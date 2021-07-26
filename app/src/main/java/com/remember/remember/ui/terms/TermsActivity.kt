package com.remember.remember.ui.terms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.facebook.shimmer.ShimmerFrameLayout
import com.remember.common.actions.toHome
import com.remember.common.extension.showError
import com.remember.common.widget.ErrorView
import com.remember.common.widget.LoaderButton
import com.remember.remember.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class TermsActivity : AppCompatActivity(R.layout.app_activity_terms) {

    private val root: ViewGroup by viewProvider(R.id.root)
    private val title: AppCompatTextView by viewProvider(R.id.term_of_use_title)
    private val termsItems: RecyclerView by viewProvider(R.id.terms_items)
    private val positiveButton: LoaderButton by viewProvider(R.id.positive_button)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_term)
    private val errorView: ErrorView by viewProvider(R.id.error_view)
    private val viewStateMachine = ViewStateMachine()
    private val stateLoading = 0
    private val stateData = 1
    private val stateError = 2
    private val termViewModel: TermViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewStateMachine()
        configAcceptTerms()
        loadTerms()
        errorView.setOnClickListener {
            loadTerms()
        }
    }

    private fun loadTerms() {
        termViewModel.terms().observe(this) {
            data {
                title.text = it.title
                termsItems.adapter = TermsAdapterRecyclerView(it.items)
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

    private fun configAcceptTerms() {
        positiveButton.setOnClickListener {
            termViewModel.acceptTerms().observe(this) {
                data {
                    startActivity(toHome())
                }

                showLoading {
                    positiveButton.onLoading(true)
                }

                error { _ ->
                    showError(R.string.app_error_terms, root)
                    positiveButton.onLoading(false)
                }
            }
        }
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = stateLoading
            }

            state(stateLoading) {
                visibles(shimmer)
                gones(termsItems, positiveButton, title, errorView)
            }

            state(stateData) {
                visibles(termsItems, positiveButton, title)
                gones(shimmer, errorView)
            }

            state(stateError) {
                visibles(errorView)
                gones(termsItems, positiveButton, title, shimmer)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewStateMachine.shutdown()
    }
}
