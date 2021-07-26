package com.remember.remember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.repository.EnvConfig

class EnvActivity : AppCompatActivity(R.layout.activity_env) {

    private val current: AppCompatTextView by viewProvider(R.id.current)
    private val dev: AppCompatButton by viewProvider(R.id.dev)
    private val mock: AppCompatButton by viewProvider(R.id.mock)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dev.setOnClickListener {
            EnvConfig.rememberUrl = EnvConfig.DEV
            updateCurrent()
        }

        mock.setOnClickListener {
            EnvConfig.rememberUrl = EnvConfig.MOCK
            updateCurrent()
        }

        updateCurrent()
    }

    private fun updateCurrent() {
        current.text = when (EnvConfig.rememberUrl) {
            EnvConfig.DEV -> "Dev"
            else -> "Mock"
        }
    }
}
