package com.remember.remember.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.remember.common.actions.MEMORY_LINE_ID
import com.remember.common.actions.MEMORY_LINE_NAME
import com.remember.common.actions.OWNER_ID
import com.remember.extension.navControllerProvider
import com.remember.remember.R
import com.remember.common.model.MemoryLineBaseInformation

class HomeActivity : AppCompatActivity(R.layout.app_activity_home) {
    private val navController: NavController by navControllerProvider(R.id.nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setupWithNavController(navController)

        intent?.extras?.let { extras ->
            val name = extras.getString(MEMORY_LINE_NAME) ?: return@let
            val id = extras.getString(MEMORY_LINE_ID) ?: return@let
            val ownerId = extras.getString(OWNER_ID) ?: return@let
            navController.navigate(
                HomeFragmentDirections.actionNavigationHomeToMemoryLineFragment2(
                    MemoryLineBaseInformation(
                        id,
                        ownerId,
                        name
                    )
                )
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
