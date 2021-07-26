package com.remember.onboarding.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.navigation.NavController
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.extension.navControllerProvider
import com.remember.onboarding.R

class OnboardingActivity : AppCompatActivity(R.layout.onboarding_activity_onboarding) {

    private val toolbar: ImageView by viewProvider(R.id.toolbar)
    private val navController: NavController by navControllerProvider(R.id.nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setOnClickListener {
            if(navController.currentDestination?.id == R.id.inputNameFragment) finish()
            else navController.navigateUp()
        }
    }
}
