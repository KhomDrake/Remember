package com.remember.onboarding.ui.login

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.IntentRule
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class LoginActivityTest {

    @get:Rule
    val intentRule = IntentRule()

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun hasUserWithAuthorization_shouldSendToHome() {
        loginActivity {
            userWithAuthorizationData()
        } launch {

        } check {
            goToHome()
        }
    }

    @Test
    fun hasUserWithoutAuthorization_shouldNotSendToHome() {
        loginActivity {
            userWithoutAuthorizationData()
        } launch {

        } check {
            notGoToHome()
        }
    }

    @Test
    fun openActivity_andCheckValues() {
        loginActivity {
            userWithoutAuthorizationData()
        } launch {

        } check {
            inputUsernameTitle()
            inputPasswordTitle()
            loginButton()
        }
    }

    @Test
    fun inputRightValuesInUsernameAndPassword_shouldGoToHome() {
        loginActivity {
            userWithoutAuthorizationData()
            loginSuccess()
        } launch {
            inputRightUsername()
            inputRightPassword()
            clickLoginButton()
        } check {
            loginSuccess()
        }
    }

    @Test
    fun inputWrongValuesInUsernameAndPassword_shouldShowRightErrorMessage() {
        loginActivity {
            userWithoutAuthorizationData()
            loginFailed()
        } launch {
            inputWrongUsername()
            inputWrongPassword()
            clickLoginButton()
        } check {
            errorMessage()
            notGoToHome()
        }
    }

    @Test
    fun whenUserHasNotAcceptedTerms_shouldGoToTerms() {
        loginActivity {
            notAcceptedTerms()
        } launch {
            inputRightPassword()
            inputRightUsername()
            clickLoginButton()
        } check {
            toTerms()
        }
    }

    @Test
    fun whenUserHasAcceptedTerms_shouldNoGoToTerms() {
        loginActivity {
            acceptedTerms()
        } launch {
            inputRightPassword()
            inputRightUsername()
            clickLoginButton()
        } check {
            goToHome()
        }
    }
}