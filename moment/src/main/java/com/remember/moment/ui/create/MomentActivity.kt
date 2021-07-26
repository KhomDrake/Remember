package com.remember.moment.ui.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.remember.extension.setStatusBar
import com.remember.extension.setStatusBarIconsLight
import com.remember.moment.R

class MomentActivity : AppCompatActivity(R.layout.moment_activity_camera) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar(R.color.statusBarColor)
        setStatusBarIconsLight()
    }
}
