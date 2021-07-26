package com.remember.moment.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import com.remember.common.actions.MOMENT_ID
import com.remember.common.actions.WITH_COMMENT
import com.remember.extension.navControllerProvider
import com.remember.moment.R

class MomentDetailActivity : AppCompatActivity() {

    private val navController: NavController by navControllerProvider(R.id.nav_host_fragment_moment_detail)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.moment_activity_moment_detail)

        val goToComment = intent.getBooleanExtra(WITH_COMMENT, false)
        val idMoment = intent?.getStringExtra(MOMENT_ID).toString()
        if(goToComment) {
            navController.navigate(
                R.id.momentCommentsFragment, Bundle().apply {
                    putString("idMoment", idMoment)
                }
            )
        } else {
            navController.navigate(
                R.id.momentFragment, Bundle().apply {
                    putString("idMoment", idMoment)
                }
            )
        }
    }
}
