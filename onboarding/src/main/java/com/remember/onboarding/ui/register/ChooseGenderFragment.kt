package com.remember.onboarding.ui.register

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.extension.showError
import com.remember.common.widget.CheckBoxTextView
import com.remember.common.widget.LoaderButton
import com.remember.extension.navControllerProvider
import com.remember.onboarding.R

class ChooseGenderFragment : Fragment(R.layout.onboarding_fragment_choose_gender) {

    private val navController: NavController by navControllerProvider()
    private val args: ChooseGenderFragmentArgs by navArgs()
    private val root: ViewGroup by viewProvider(R.id.root)
    private val firstOption: CheckBoxTextView by viewProvider(R.id.checkbox_first_option)
    private val secondOption: CheckBoxTextView by viewProvider(R.id.checkbox_second_option)
    private val thirdOption: CheckBoxTextView by viewProvider(R.id.checkbox_third_option)
    private val fourthOption: CheckBoxTextView by viewProvider(R.id.checkbox_fourth_option)
    private val firthOption: CheckBoxTextView by viewProvider(R.id.checkbox_firth_option)
    private val sixOption: CheckBoxTextView by viewProvider(R.id.checkbox_six_option)
    private val continueButton: LoaderButton by viewProvider(R.id.continue_button)
    private lateinit var options: List<CheckBoxTextView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupValuesOptions()
        options = listOf(firstOption, secondOption, thirdOption, fourthOption, firthOption, sixOption)

        continueButton.setOnClickListener {
            if (options.find { it.isChecked } == null) {
                showError(R.string.onboarding_select_gender, root)
                return@setOnClickListener
            }

            if (options.filter { it.isChecked }.size > 1) {
                showError(R.string.onboarding_select_one_gender, root)
                return@setOnClickListener
            }

            val option = options.find { it.isChecked }
            option?.let {
                navController.navigate(ChooseGenderFragmentDirections.actionChooseGenderFragmentToInputEmailFragment(
                    args.name,
                    args.nickname,
                    args.birthDate,
                    args.cellphone,
                    it.checkBoxValue
                ))
            }
        }
    }

    private fun setupValuesOptions() {
        firstOption.setValue("Men")
        secondOption.setValue("Women")
        thirdOption.setValue("Transsexual")
        fourthOption.setValue("Non-Binary")
        firthOption.setValue("No Gender")
        sixOption.setValue("Other")
    }
}
