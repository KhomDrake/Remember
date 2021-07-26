package com.remember.profile.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import com.remember.common.extension.showError
import com.remember.common.extension.showSuccess
import com.remember.profile.R
import com.remember.repository.ProfileRepository
import com.remember.repository.model.profile.Profile
import com.remember.repository.model.profile.UpdateProfileImage
import com.remember.test.extensions.click
import com.remember.test.extensions.hasText
import com.remember.test.extensions.isDisplayed
import com.remember.test.extensions.isTextDisplayed
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.koin.core.KoinComponent
import org.koin.core.inject

fun ProfileFragmentTest.profileFragment(func: ProfileFragmentRobot.() -> Unit) =
    ProfileFragmentRobot(mockk(relaxed = true)).apply(func)

class ProfileFragmentRobot(private val navController: NavController) : KoinComponent {

    private val profileRepository: ProfileRepository by inject()

    fun profileSuccess() {
        every { profileRepository.informationProfile() } returns MutableResponseLiveData<Profile>().apply {
            postData(
                Profile(
                    "kladjslkdjlskdjsalkdjasldka",
                    "Hoid",
                    "Adonalsium",
                    "1997-09-05",
                    "adonalsium@hotmail.com",
                    "vasher",
                    "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Poppy_15.jpg",
                    "dlksjadlkasjdla",
                    "M"
                )
            )
        }
    }

    fun profileError() {
        every { profileRepository.informationProfile() } returns MutableResponseLiveData<Profile>().apply {
            postError(Throwable())
        }
    }

    fun removeProfileSuccess() {
        every {
            profileRepository.updateProfile(any(), any())
        } returns MutableResponseLiveData<Profile>().apply {
            postSuccess()
        }
    }

    fun removeProfileFailure() {
        every {
            profileRepository.updateProfile(any(), any())
        } returns MutableResponseLiveData<Profile>().apply {
            postError(Throwable())
        }
    }

    infix fun launch(func: ProfileFragmentRobot.() -> Unit) : ProfileFragmentRobot {
        mockkStatic("com.remember.common.extension.ToastKt")
        launchFragmentInContainer<ProfileFragment>(
            themeResId = R.style.RememberAppTheme_Primary
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }
        return apply(func)
    }

    fun clickTryAgain() {
        R.id.error_button.click()
    }

    fun clickUpdateProfilePicture() {
        R.id.change_profile.click()
    }

    fun clickRemove() {
        R.id.delete_icon.click()
    }

    infix fun check(func: ProfileFragmentResult.() -> Unit) = ProfileFragmentResult().apply(func)

}

class ProfileFragmentResult : KoinComponent {

    private val profileRepository: ProfileRepository by inject()

    fun successValues() {
        "Como quer ser chamado(a)".isTextDisplayed()
        "Perfil".isTextDisplayed()
        R.id.name_title.hasText("Nome")
        R.id.name_input.hasText("Hoid")
        R.id.username_title.hasText("Usuário")
        R.id.username_input.hasText("vasher")
    }

    fun errorScreen() {
        R.id.error_title.hasText("Ocorreu um erro")
        R.id.error_text.hasText("Não foi possível carregar o seu perfil. Por favor, tente novamente.")
        R.id.error_button.hasText("Tentar Novamente")
    }

    fun requestedProfileAgain() {
        verify(exactly = 2) {
            profileRepository.informationProfile()
        }
    }

    fun bottomSheetVisible() {
        "Foto de perfil".isTextDisplayed()
        R.id.delete_icon.isDisplayed()
        R.id.gallery_icon.isDisplayed()
        R.id.camera_icon.isDisplayed()
        "Remover".isTextDisplayed()
        "Galeria".isTextDisplayed()
        "Câmera".isTextDisplayed()
    }

    fun removeProfileCalled() {
        verify {
            profileRepository.updateProfile(any(), any())
        }
    }

    fun successMessage() {
        verify { any<Fragment>().showSuccess(
            R.string.profile_profile_picture_removed_success, any()
        ) }
    }

    fun failureMessage() {
        verify { any<Fragment>().showError(
            R.string.profile_profile_picture_removed_failure, any()
        ) }
    }

}