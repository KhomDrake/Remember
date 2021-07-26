package com.remember.onboarding.ui.register

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.extension.showError
import com.remember.common.widget.LoaderButton
import com.remember.extension.isValidEmail
import com.remember.extension.navControllerProvider
import com.remember.onboarding.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class InputEmailFragment : Fragment(R.layout.onboarding_fragment_input_email_onboarding) {

    private val navController: NavController by navControllerProvider()
    private val root: ViewGroup by viewProvider(R.id.root)
    private val onboardingViewModel: OnboardingViewModel by viewModel()
    private val args: InputEmailFragmentArgs by navArgs()
    private val email: AppCompatEditText by viewProvider(R.id.input_email_input)
    private val continueButton: LoaderButton by viewProvider(R.id.continue_button)
    private var onLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        continueButton.setOnClickListener {
            val email = email.text.toString()

            if(email.isValidEmail().not()) {
                showError(R.string.onboarding_invalid_email, root)
                return@setOnClickListener
            }

            if(onLoading) return@setOnClickListener

            onboardingViewModel.verifyEmail(email)
                .observe(viewLifecycleOwner) {
                    data {
                        navController.navigate(
                            InputEmailFragmentDirections.actionInputEmailFragmentToInputPasswordFragment(
                                args.name,
                                args.nickname,
                                args.birthDate,
                                args.cellphone,
                                args.gender,
                                email
                            )
                        )
                    }
                    loading {
                        onLoading = it
                        continueButton.onLoading(it)
                    }
                    error { _ ->
                        showError(R.string.onboarding_invalid_email, root)
                    }
                }
        }
    }
}
