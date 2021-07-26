package com.remember.onboarding.ui.register

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.extension.showError
import com.remember.common.widget.LoaderButton
import com.remember.extension.navControllerProvider
import com.remember.onboarding.R
import com.remember.repository.InvalidUsername
import org.koin.androidx.viewmodel.ext.android.viewModel

class InputUsernameFragment : Fragment(R.layout.onboarding_fragment_input_username_onboarding) {

    private val navController: NavController by navControllerProvider()
    private val args: InputUsernameFragmentArgs by navArgs()
    private val onboardingViewModel: OnboardingViewModel by viewModel()

    private val root: ViewGroup by viewProvider(R.id.root)
    private val switch: SwitchCompat by viewProvider(R.id.switch_username)
    private val username: AppCompatEditText by viewProvider(R.id.input_username)
    private val finishSignup: LoaderButton by viewProvider(R.id.input_username_continue)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val names = args.nickname.trim().split(" ")
                val nickname = if(names.size > 1) {
                    names.first()
                } else args.nickname

                username.setText(nickname)
            }
        }

        finishSignup.setOnClickListener {
            val username = username.text.toString()
            if(username.isEmpty()) {
                showError(R.string.onboarding_input_value, root)
                return@setOnClickListener
            }

            onboardingViewModel.createAccount(
                username,
                args.email,
                args.password,
                args.nickname,
                args.birthDate,
                args.cellphone,
                args.gender,
                args.name
            ).observe(viewLifecycleOwner) {
                data {
                    navController.navigate(InputUsernameFragmentDirections.actionInputUsernameFragmentToAccountCreatedFragment())
                }
                loading {
                    finishSignup.onLoading(it)
                }
                error { e ->
                    when(e) {
                        is InvalidUsername -> showError(R.string.onboarding_username_invalid, root)
                        else -> showError(R.string.onboarding_account_could_not_be_created, root)
                    }
                }
            }
        }
    }
}
