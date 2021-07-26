package com.remember.onboarding.ui.register

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.extension.showError
import com.remember.common.widget.LoaderButton
import com.remember.onboarding.R
import com.remember.extension.navControllerProvider

class InputNameFragment : Fragment(R.layout.onboarding_fragment_input_name_onboarding) {

    private val navController: NavController by navControllerProvider()
    private val root: ViewGroup by viewProvider(R.id.root)
    private val name: AppCompatEditText by viewProvider(R.id.input_name_input)
    private val nickname: AppCompatEditText by viewProvider(R.id.input_nickname_input)
    private val continueButton: LoaderButton by viewProvider(R.id.continue_button)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        continueButton.setOnClickListener {
            val name = name.text.toString()
            val nickname = nickname.text.toString()
            if(name.isEmpty() || nickname.isEmpty()) {
                showError(R.string.onboarding_input_values, root)
                return@setOnClickListener
            }

            navController.navigate(
                InputNameFragmentDirections.actionInputNameFragmentToBirthDateFragment(
                    name,
                    nickname
                )
            )
        }
    }
}
