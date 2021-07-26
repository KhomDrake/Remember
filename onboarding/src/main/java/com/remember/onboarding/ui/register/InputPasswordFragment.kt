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
import com.remember.onboarding.R
import com.remember.extension.isValidPassword
import com.remember.extension.navControllerProvider

class InputPasswordFragment : Fragment(R.layout.onboarding_fragment_input_password_onboarding) {
    private val navController: NavController by navControllerProvider()
    private val args: InputPasswordFragmentArgs by navArgs()
    private val root: ViewGroup by viewProvider(R.id.root)
    private val password: AppCompatEditText by viewProvider(R.id.input_password)
    private val confirmPassword: AppCompatEditText by viewProvider(R.id.input_confirm_password)
    private val continueButton: LoaderButton by viewProvider(R.id.input_password_continue)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        continueButton.setOnClickListener {
            val password = password.text.toString()
            val confirmPassword = confirmPassword.text.toString()
            if(confirmPassword.isEmpty() || password.isEmpty()) {
                showError(R.string.onboarding_input_values, root)
                return@setOnClickListener
            }
            if(password != confirmPassword) {
                showError(R.string.onboarding_password_not_equal, root)
                return@setOnClickListener
            }

            if(password.isValidPassword().not()) {
                showError(R.string.onboarding_password_pattern, root)
                return@setOnClickListener
            }

            navController.navigate(
                InputPasswordFragmentDirections.actionInputPasswordFragmentToInputUsernameFragment(
                    args.name,
                    args.nickname,
                    args.birthDate,
                    args.cellphone,
                    args.gender,
                    args.email,
                    password
                )
            )
        }
    }
}
