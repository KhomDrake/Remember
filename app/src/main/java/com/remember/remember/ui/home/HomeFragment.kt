package com.remember.remember.ui.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.facebook.shimmer.ShimmerFrameLayout
import com.nambimobile.widgets.efab.ExpandableFabLayout
import com.nambimobile.widgets.efab.FabOption
import com.remember.common.actions.toCreateMemoryLine
import com.remember.common.actions.toCreateMoment
import com.remember.common.actions.toMemoryLineType
import com.remember.common.actions.toProfile
import com.remember.common.extension.showError
import com.remember.remember.R
import com.remember.remember.ui.home.adapter.HomePagerAdapter
import com.remember.common.model.MomentType
import com.remember.common.widget.RememberToolbar
import com.remember.extension.setStatusBarIconsLight
import com.remember.extension.requestPermission
import com.remember.extension.checkPermissions
import com.remember.extension.setStatusBar
import com.remember.remember.ui.home.memory_line.MemoryLineFragment
import com.remember.repository.model.MemoryLineTypePagination
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.app_fragment_home) {

    private val viewPager: ViewPager2 by viewProvider(R.id.view_pager)
    private val root: ViewGroup by viewProvider(R.id.root)
    private val indicator: WormDotsIndicator by viewProvider(R.id.indicator)
    private val shimmerHome: ShimmerFrameLayout by viewProvider(R.id.shimmer_home)
    private val rememberToolbar: RememberToolbar by viewProvider(R.id.toolbar)
    private val emptyStateText: AppCompatTextView by viewProvider(R.id.empty_state_home)
    private val homeAddButton: ExpandableFabLayout by viewProvider(R.id.home_add_button)
    private val memoryLineOption: FabOption by viewProvider(R.id.select_memory_line)
    private val memoryLineTypeOption: FabOption by viewProvider(R.id.select_memory_line_type)
    private val homeViewModel: HomeViewModel by viewModel()
    private val viewStateMachine = ViewStateMachine()
    private val stateLoadingTypes = 0
    private val stateData = 1
    private val stateEmpty = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rememberToolbar.setAvatarVisibility(true)
        setStatusBar(R.color.statusBarColor)
        setStatusBarIconsLight()
        setupViewStateMachine()
        setupToolbar()
        setupExpandableFab()
    }

    override fun onStart() {
        super.onStart()
        requestData()
    }

    private fun setupToolbar() {
        rememberToolbar.setOnClickListenerBackIcon {
            if (viewStateMachine.currentStateKey != stateData) {
                showError(R.string.app_cannot_open_camera_empty_state, root)
                return@setOnClickListenerBackIcon
            }

            if(checkPermissions(MemoryLineFragment.REQUIRED_PERMISSIONS_CAMERA))
                startActivity(requireContext().toCreateMoment(MomentType.CAMERA.name))
            else
                requestPermission(
                    MemoryLineFragment.REQUIRED_PERMISSIONS_CAMERA,
                    MemoryLineFragment.REQUEST_CODE_PERMISSION_CAMERA
                )
        }

        rememberToolbar.setOnClickListenerAvatarIcon {
            startActivity(requireContext().toProfile())
        }
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = stateLoadingTypes
            }

            state(stateLoadingTypes) {
                visibles(shimmerHome)
                gones(viewPager, indicator, emptyStateText, homeAddButton)
            }

            state(stateData) {
                gones(shimmerHome, emptyStateText)
                visibles(viewPager, indicator, homeAddButton)
                onEnter {
                    memoryLineOption.fabOptionEnabled = true
                }
            }

            state(stateEmpty) {
                gones(shimmerHome, viewPager, indicator)
                visibles(emptyStateText, homeAddButton)
                onEnter {
                    memoryLineOption.fabOptionEnabled = false
                }
            }
        }
    }

    private fun setupExpandableFab() {
        memoryLineOption.setOnClickListener {
            startActivity(requireContext().toCreateMemoryLine())
        }

        memoryLineTypeOption.setOnClickListener {
            startActivity(requireContext().toMemoryLineType())
        }
    }

    private fun requestData() {
        homeViewModel.profile().observe(viewLifecycleOwner) {
            data {
                rememberToolbar.loadAvatar(it.photo)
            }
            error { _ ->
                rememberToolbar.loadAvatar(null)
            }
        }

        homeViewModel.types().observe(viewLifecycleOwner) {
            data {
                if (it.results.isEmpty()) {
                    viewStateMachine.changeState(stateEmpty)
                    return@data
                }

                setupData(it)
            }
            showLoading {
                viewStateMachine.changeState(stateLoadingTypes)
            }
        }
    }

    private fun setupData(memoryLineTypePagination: MemoryLineTypePagination) {
        viewPager.adapter = HomePagerAdapter(
            requireActivity(),
            memoryLineTypePagination.results
        )
        indicator.setViewPager2(viewPager)
        viewStateMachine.changeState(stateData)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            MemoryLineFragment.REQUEST_CODE_PERMISSION_CAMERA -> {
                if(checkPermissions(MemoryLineFragment.REQUIRED_PERMISSIONS_CAMERA))
                    startActivity(requireContext().toCreateMoment(MomentType.CAMERA.name))
            }
            else -> Unit
        }
    }

    override fun onDestroyView() {
        viewStateMachine.shutdown()
        super.onDestroyView()
    }
}
