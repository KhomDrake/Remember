package com.remember.onboarding.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.actions.toHome
import com.remember.common.actions.toLogo
import com.remember.common.actions.toTerms
import com.remember.common.extension.showError
import com.remember.common.widget.LoaderButton
import com.remember.common.widget.RememberToolbar
import com.remember.extension.onTextChanged
import com.remember.onboarding.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity(R.layout.onboarding_activity_login) {

    private val loginViewModel: LoginViewModel by viewModel()
    private val root: ViewGroup by viewProvider(R.id.root)
    private val usernameOrEmail: AppCompatEditText by viewProvider(R.id.input_username_or_email)
    private val password: AppCompatEditText by viewProvider(R.id.input_password)
    private val login: LoaderButton by viewProvider(R.id.login_button)
    private val toolbar: RememberToolbar by viewProvider(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
    }

    override fun onStart() {
        super.onStart()
        loginViewModel.setup().observe(this) {
            data {
                startActivity(if(it) toLogo() else toTerms())
                finish()
            }
        }
    }

    private fun setupListeners() {
        usernameOrEmail.onTextChanged(loginViewModel::setUsernameOrEmail)
        password.onTextChanged(loginViewModel::setPassword)

        login.setOnClickListener {
            if(loginViewModel.canProceed().not()) {
                showError(R.string.onboarding_input_values, root)
                return@setOnClickListener
            }
            loginViewModel.login().observe(this) {
                data {
                    startActivity(if(it) toHome() else toTerms())
                    finishAffinity()
                }
                showLoading {
                    login.onLoading(true)
                }
                error { _ ->
                    login.onLoading(false)
                    showError(R.string.onboarding_login_failed, root)
                }
            }
        }

        toolbar.setOnClickListenerBackIcon {
            finish()
        }
    }
}
