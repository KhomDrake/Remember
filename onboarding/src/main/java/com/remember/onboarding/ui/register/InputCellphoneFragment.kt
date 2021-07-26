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
import com.remember.extension.validDDD
import com.remember.extension.validPhoneNumber
import com.remember.onboarding.R

const val PHONE_NUMBER_MASK = "#####-####"

class InputCellphoneFragment : Fragment(R.layout.onboarding_fragment_input_cellphone_onboarding) {

    private val args: InputCellphoneFragmentArgs by navArgs()
    private val root: ViewGroup by viewProvider(R.id.root)
    private val ddd: AppCompatEditText by viewProvider(R.id.ddd_input)
    private val number: AppCompatEditText by viewProvider(R.id.number_input)
    private val buttonContinue: LoaderButton by viewProvider(R.id.continue_button)
    private val navController: NavController by navControllerProvider()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        number.addTextChangedListener(MascaraNumericaTextWatcher(PHONE_NUMBER_MASK))
        buttonContinue.setOnClickListener {
            val ddd = ddd.text.toString()
            if(!ddd.validDDD()) {
                showError(R.string.onboarding_ddd_invalid, root)
                return@setOnClickListener
            }

            val cellphone = number.text.toString()

            if(!cellphone.validPhoneNumber()) {
                showError(R.string.onboarding_phone_number_invalid, root)
                return@setOnClickListener
            }

            navController.navigate(InputCellphoneFragmentDirections.actionInputCellphoneFragmentToChooseGenderFragment(
                args.name,
                args.nickname,
                args.birthDate,
                "+55${ddd}${cellphone.removePrefix("-")}"
            ))
        }
    }
}
