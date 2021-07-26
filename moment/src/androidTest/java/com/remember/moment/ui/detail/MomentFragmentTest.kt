package com.remember.moment.ui.detail

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class MomentFragmentTest {

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun momentDataSuccess_shouldRightInformation() {
        momentFragment {
            momentDataSuccess()
        } launch {

        } check {
            momentInformation()
        }
    }

    @Test
    fun momentDataSuccess_andClickComments_shouldOpenCommentsFragment() {
        momentFragment {
            momentDataSuccess()
        } launch {
            clickComments()
        } check {
            openCommentsFragment()
        }
    }

    @Test
    fun whenIsNotOwner_shouldRemoveBeDisable() {
        momentFragment {
            momentDataSuccess()
            isNotOwner()
        } launch {

        } check {
            removeDisable()
        }
    }

    @Test
    fun whenIsOwner_shouldRemoveBeEnable() {
        momentFragment {
            momentDataSuccess()
            isOwner()
        } launch {

        } check {
            removeEnable()
        }
    }

    @Test
    fun whenIsOwner_andClickRemove_shouldShowBottomSheet() {
        momentFragment {
            momentDataSuccess()
            isOwner()
        } launch {
            clickRemove()
        } check {
            bottomSheet()
        }
    }

    @Test
    fun whenConfirmRemoveMoment_shouldCallRemoveMoment() {
        momentFragment {
            momentDataSuccess()
            isOwner()
            removeMoment()
        } launch {
            clickRemove()
            clickConfirm()
        } check {
            removeMomentCalled()
        }
    }

    @Test
    fun whenNotConfirmRemoveMoment_shouldNotCallRemoveMoment() {
        momentFragment {
            momentDataSuccess()
            isOwner()
            removeMoment()
        } launch {
            clickRemove()
            clickCancel()
        } check {
            removeMomentNotCalled()
        }
    }

    @Test
    fun whenMomentDataError_shouldShowErrorScreen() {
        momentFragment {
            momentDataError()
        } launch {

        } check {
            errorScreen()
        }
    }
}