package com.remember.test.rule

import androidx.test.core.app.ApplicationProvider
import com.jakewharton.threetenabp.AndroidThreeTen
import io.mockk.unmockkAll
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

class KoinRule(
    private val modules: List<Module> = emptyList(),
    private val start: Boolean = true,
    private val configAndroidThreeTen: Boolean = true
) : TestRule {
    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    if(configAndroidThreeTen)
                        AndroidThreeTen.init(ApplicationProvider.getApplicationContext())
                    if(!start)
                        stopKoin()

                    startKoin {
                        androidContext(ApplicationProvider.getApplicationContext())
                        modules(modules)
                    }
                    base.evaluate()
                } finally {
                    stopKoin()
                    unmockkAll()
                }
            }
        }
    }
}