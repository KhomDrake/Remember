package com.remember.onboarding.ui.register

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import br.com.arch.toolkit.delegate.viewProvider
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher
import com.remember.common.extension.showError
import com.remember.common.widget.LoaderButton
import com.remember.extension.navControllerProvider
import com.remember.extension.isValidDate
import com.remember.onboarding.R

class BirthDateFragment : Fragment(R.layout.onboarding_fragment_birth_date_onboarding) {

    private val navController: NavController by navControllerProvider()
    private val args: BirthDateFragmentArgs by navArgs()

    private val root: ViewGroup by viewProvider(R.id.root)
    private val date: AppCompatEditText by viewProvider(R.id.birth_date_input)
    private val continueButton: LoaderButton by viewProvider(R.id.birth_date_continue)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        date.addTextChangedListener(MascaraNumericaTextWatcher("##/##/####"))
        continueButton.setOnClickListener {
            val date = date.text.toString()
            if(date.isValidDate().not()) {
                showError(R.string.onboarding_invalid_birth_date, root)
                return@setOnClickListener
            }

            navController.navigate(
                BirthDateFragmentDirections.actionBirthDateFragmentToInputCellphoneFragment(
                    args.name,
                    args.nickname,
                    date
                )
            )
        }
    }
}