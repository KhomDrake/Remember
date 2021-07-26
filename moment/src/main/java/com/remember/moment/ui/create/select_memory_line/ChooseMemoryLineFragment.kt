package com.remember.moment.ui.create.select_memory_line

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.facebook.shimmer.ShimmerFrameLayout
import com.remember.common.widget.ErrorView
import com.remember.moment.R
import com.remember.moment.ui.create.MomentViewModel
import com.remember.moment.ui.create.select_memory_line.adapter.ChooseMemoryLinePagerAdapter
import com.remember.extension.navControllerProvider
import com.remember.common.widget.RememberToolbar
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseMemoryLineFragment : Fragment(R.layout.moment_fragment_choose_memory_line) {

    private val momentViewModel: MomentViewModel by viewModel()
    private val navController: NavController by navControllerProvider()
    private val args: ChooseMemoryLineFragmentArgs by navArgs()
    private val viewPager: ViewPager2 by viewProvider(R.id.view_pager)
    private val toolbar: RememberToolbar by viewProvider(R.id.toolbar)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_home)
    private val errorView: ErrorView by viewProvider(R.id.error_view)
    private val pageIndicatorView: WormDotsIndicator by viewProvider(R.id.page_indicator_view)
    private val viewStateMachine = ViewStateMachine()
    private val stateLoading = 0
    private val stateData = 1
    private val stateError = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        setupViewStateMachine()
        loadType()
        errorView.setOnClickListener {
            loadType()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewStateMachine.shutdown()
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = stateLoading
            }

            state(stateLoading) {
                visibles(shimmer)
                gones(viewPager, pageIndicatorView, errorView)
            }

            state(stateData) {
                gones(shimmer, errorView)
                visibles(viewPager, pageIndicatorView)
            }

            state(stateError) {
                visibles(errorView)
                gones(viewPager, pageIndicatorView, shimmer)
            }
        }
    }

    private fun setup() {
        toolbar.setOnClickListenerBackIcon {
            navController.navigateUp()
        }
    }

    private fun loadType() {
        momentViewModel.types().observe(viewLifecycleOwner) {
            data {
                viewPager.adapter = ChooseMemoryLinePagerAdapter(
                    requireActivity(),
                    args.file,
                    it.results
                )
                pageIndicatorView.setViewPager2(viewPager)
                viewStateMachine.changeState(stateData)
            }
            showLoading {
                viewStateMachine.changeState(stateLoading)
            }
            error { _ ->
                viewStateMachine.changeState(stateError)
            }
        }
    }
}
