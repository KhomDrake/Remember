package com.remember.remember.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.remember.common.actions.toHome
import com.remember.common.actions.toIntro
import com.remember.common.actions.toTerms
import com.remember.remember.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogoActivity : AppCompatActivity(R.layout.app_activity_logo) {

    private val userViewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.setup().observe(this) {
            data {
                startActivity(if(it) toHome() else toTerms())
                finish()
            }
            error { _ ->
                userViewModel.removeAuthData().observeSuccess(this@LogoActivity) {
                    startActivity(toIntro())
                    finish()
                }
            }
        }
    }
}
