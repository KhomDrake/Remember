package com.remember.remember.ui.home.memory_line

import android.Manifest
import android.app.ActivityOptions
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.facebook.shimmer.ShimmerFrameLayout
import com.nambimobile.widgets.efab.ExpandableFabLayout
import com.nambimobile.widgets.efab.FabOption
import com.remember.common.actions.toCreateMoment
import com.remember.common.actions.toMomentDetail
import com.remember.common.actions.toProfile
import com.remember.common.model.MomentType
import com.remember.common.widget.ErrorView
import com.remember.common.widget.RememberToolbar
import com.remember.extension.checkPermissions
import com.remember.extension.navControllerProvider
import com.remember.extension.onScroll
import com.remember.extension.requestPermission
import com.remember.extension.setStatusBar
import com.remember.extension.setStatusBarIconsLight
import com.remember.remember.R
import com.remember.remember.ui.home.memory_line.adapter.RecyclerViewAdapterMoments
import de.hdodenhof.circleimageview.CircleImageView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoryLineFragment : Fragment(R.layout.app_fragment_memory_line) {

    private val memoryLineDetailViewModel: MemoryLineDetailViewModel by viewModel()
    private val navController: NavController by navControllerProvider()
    private val navArgs: MemoryLineFragmentArgs by navArgs()

    private val rememberToolbar: RememberToolbar by viewProvider(R.id.toolbar)
    private val name: AppCompatTextView by viewProvider(R.id.name_memory_line)
    private val configuration: CircleImageView by viewProvider(R.id.configuration)
    private val hasMoreMembers: CircleImageView by viewProvider(R.id.has_more_participants)
    private val addMoment: ExpandableFabLayout by viewProvider(R.id.add_moment)
    private val selectCamera: FabOption by viewProvider(R.id.select_camera)
    private val selectGallery: FabOption by viewProvider(R.id.select_gallery)
    private val moments: RecyclerView by viewProvider(R.id.moments)
    private val swipeRefreshLayout: SwipeRefreshLayout by viewProvider(R.id.swipe_refresh)

    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer)
    private val errorView: ErrorView by viewProvider(R.id.retrievable_error)

    private val viewStateMachine = ViewStateMachine()
    private val stateLoadingMoments = 0
    private val stateDataMoments = 1
    private val stateErrorMoments = 2

   private lateinit var linearLayoutManager: LinearLayoutManager
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setStatusBar(R.color.statusBarColor)
        setStatusBarIconsLight()
        setupViewStateMachine()
        setupMemoryLineInformation()
        setupAddMoment()
        setupListeners()
        setupRetrievableError()
        setupSwipeRefresh()
        setupToolbar()
        updateName()
        setupMoments()
    }

    override fun onStart() {
        super.onStart()
        setupDetail()
        setupProfile()
        loadMoments()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewStateMachine.shutdown()
    }

    private fun setupMoments() {
        moments.setupAdapter()
        moments.onScroll({lastVisibleItemPosition}) {
            val momentsAdapter = moments.momentsAdapter
            when {
                momentsAdapter.hasNoMore -> Unit
                memoryLineDetailViewModel.hasNextPage.not() -> {
                    momentsAdapter.addNoMore()
                }
                momentsAdapter.canLoadMore.not() -> Unit
                momentsAdapter.canLoadMore -> loadMoreMoments()
                else -> Unit
            }
        }

        moments.momentsAdapter.setOnError {
            loadMoreMoments()
        }
    }

    private fun loadMoments(resetAll: Boolean = false) {
        if(resetAll) memoryLineDetailViewModel.reset() else memoryLineDetailViewModel.resetPage()

        moments.momentsAdapter.removeAll()
        memoryLineDetailViewModel.momentsPagination()
            .observe(viewLifecycleOwner) {
                data {
                    swipeRefreshLayout.isRefreshing = false
                    moments.momentsAdapter.addMoments(it.results)
                    memoryLineDetailViewModel.setNextPage(it.next)
                    viewStateMachine.changeState(stateDataMoments)
                }
                showLoading {
                    viewStateMachine.changeState(stateLoadingMoments)
                }
                error { _ ->
                    viewStateMachine.changeState(stateErrorMoments)
                }
            }
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = stateLoadingMoments
            }

            state(stateLoadingMoments) {
                visibles(shimmer)
                gones(addMoment, moments, swipeRefreshLayout, errorView)
            }

            state(stateDataMoments) {
                visibles(addMoment, moments, swipeRefreshLayout)
                gones(shimmer, errorView)
            }

            state(stateErrorMoments) {
                visibles(errorView)
                gones(addMoment, moments, swipeRefreshLayout, shimmer)
            }
        }
    }

    private fun setupProfile() {
        memoryLineDetailViewModel.profile().observe(viewLifecycleOwner) {
            data {
                rememberToolbar.loadAvatar(it.photo)
            }
            error { _ ->
                rememberToolbar.loadAvatar(null)
            }
        }
    }

    private fun setupToolbar() {
        rememberToolbar.setAvatarVisibility(true)
        rememberToolbar.setOnClickListenerBackIcon {
            if(checkPermissions(REQUIRED_PERMISSIONS_CAMERA))
                momentCamera()
            else
                requestPermission(
                    REQUIRED_PERMISSIONS_CAMERA,
                    REQUEST_CODE_PERMISSION_CAMERA
                )
        }

        rememberToolbar.setOnClickListenerAvatarIcon {
            startActivity(requireContext().toProfile())
        }
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            loadMoments(resetAll = true)
        }
    }

    private fun setupRetrievableError() {
        errorView.setOnClickListener {
            loadMoments(resetAll = true)
        }
    }

    private fun setupDetail() {
        memoryLineDetailViewModel.memoryLineDetail()
            .observe(viewLifecycleOwner) {
                data {
                    hasMoreMembers.isVisible = it.participants.isNotEmpty()
                    memoryLineDetailViewModel.setNameMemoryLine(it.title)
                }
            }
    }

    private fun updateName() {
        memoryLineDetailViewModel.baseInformationMemoryLine.observe(viewLifecycleOwner, Observer {
            name.text = it.name
        })
    }

    private fun loadMoreMoments() {
        memoryLineDetailViewModel.momentsPagination()
            .observe(viewLifecycleOwner) {
                data {
                    swipeRefreshLayout.isRefreshing = false
                    moments.momentsAdapter.addMoments(it.results)
                    memoryLineDetailViewModel.setNextPage(it.next)
                }
                showLoading {
                    moments.momentsAdapter.addLoading()
                }
                error { _ ->
                    moments.momentsAdapter.addError()
                }
            }
    }

    private fun setupAddMoment() {
        selectCamera.setOnClickListener {
            if(checkPermissions(REQUIRED_PERMISSIONS_CAMERA))
                momentCamera()
            else
                requestPermission(
                    REQUIRED_PERMISSIONS_CAMERA,
                    REQUEST_CODE_PERMISSION_CAMERA
                )
        }

        selectGallery.setOnClickListener {
            if(checkPermissions(REQUIRED_PERMISSIONS_GALLERY))
                momentGallery()
            else
                requestPermission(
                    REQUIRED_PERMISSIONS_GALLERY,
                    REQUEST_CODE_PERMISSION_GALLERY
                )
        }
    }

    private fun setupMemoryLineInformation() {
        memoryLineDetailViewModel.setBaseInformationMemoryLine(navArgs.memoryLineData)
    }

    private fun setupListeners() {
        configuration.setOnClickListener {
            memoryLineDetailViewModel.baseInformationMemoryLine.value?.let { information ->
                navController.navigate(
                    MemoryLineFragmentDirections.actionMemoryLineFragmentToMemoryLineConfigurationFragment(
                        information
                    )
                )
            }
        }

        hasMoreMembers.setOnClickListener {
            memoryLineDetailViewModel.baseInformationMemoryLine.value?.let { information ->
                navController.navigate(
                    MemoryLineFragmentDirections.actionMemoryLineFragmentToMemoryLineParticipantsFragment(
                        information.idMemoryLine
                    )
                )
            }
        }
    }

    private fun RecyclerView.setupAdapter() {
        linearLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        layoutManager = linearLayoutManager
        adapter = RecyclerViewAdapterMoments(
            onClickComments = { idMoment, image: View ->
                toMomentDetail(idMoment, true, image)
            },
            onClickMoment = { idMoment: String, _, image: View ->
                toMomentDetail(idMoment, false, image)
            }
        )
    }

    private fun toMomentDetail(idMoment: String, withComment: Boolean, image: View) {
        val intent = requireContext().toMomentDetail(idMoment, withComment)
        val options = ActivityOptions
            .makeSceneTransitionAnimation(activity, image, "Moment")
        startActivity(intent, options.toBundle())
    }

    private fun momentGallery() {
        memoryLineDetailViewModel.baseInformationMemoryLine.value?.let { information ->
            startActivity(
                requireContext().toCreateMoment(
                    MomentType.GALLERY.name,
                    information.idMemoryLine,
                    information.name,
                    information.ownerId
                )
            )
        }
    }

    private fun momentCamera() {
        memoryLineDetailViewModel.baseInformationMemoryLine.value?.let { information ->
            startActivity(
                requireContext().toCreateMoment(
                    MomentType.CAMERA.name,
                    information.idMemoryLine,
                    information.name,
                    information.ownerId
                )
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            REQUEST_CODE_PERMISSION_CAMERA -> {
                if(checkPermissions(REQUIRED_PERMISSIONS_CAMERA))
                    momentCamera()
            }
            REQUEST_CODE_PERMISSION_GALLERY -> {
                if(checkPermissions(REQUIRED_PERMISSIONS_GALLERY))
                    momentGallery()
            }
            else -> Unit
        }
    }

    private val RecyclerView.momentsAdapter: RecyclerViewAdapterMoments
        get() = adapter as RecyclerViewAdapterMoments

    companion object {
        const val REQUEST_CODE_PERMISSION_CAMERA = 10
        const val REQUEST_CODE_PERMISSION_GALLERY = 11
        val REQUIRED_PERMISSIONS_CAMERA = arrayOf(Manifest.permission.CAMERA)
        val REQUIRED_PERMISSIONS_GALLERY = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}
