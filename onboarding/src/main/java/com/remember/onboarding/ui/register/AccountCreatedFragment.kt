package com.remember.onboarding.ui.register

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.actions.toHistory
import com.remember.common.actions.toLogin
import com.remember.onboarding.R

class AccountCreatedFragment : Fragment(R.layout.onboarding_account_created_fragment) {

    private val rememberHistory: AppCompatButton by viewProvider(R.id.remember_history)
    private val firstAccess: AppCompatTextView by viewProvider(R.id.first_access)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rememberHistory.setOnClickListener {
            startActivity(requireContext().toHistory())
            requireActivity().finish()
        }

        firstAccess.setOnClickListener {
            startActivity(requireContext().toLogin())
            requireActivity().finish()
        }
    }
}