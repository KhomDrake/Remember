package com.remember.onboarding.ui.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.actions.toLogin
import com.remember.common.actions.toOnboarding
import com.remember.common.widget.LoaderButton
import com.remember.onboarding.R

class IntroActivity : AppCompatActivity(R.layout.onboarding_activity_intro) {
    private val createAccount: LoaderButton by viewProvider(R.id.create_account)
    private val alreadyHaveAccount: AppCompatTextView by viewProvider(R.id.already_have_account)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        createAccount.setOnClickListener {
            startActivity(toOnboarding())
        }

        alreadyHaveAccount.setOnClickListener {
            startActivity(toLogin())
        }
    }
}
