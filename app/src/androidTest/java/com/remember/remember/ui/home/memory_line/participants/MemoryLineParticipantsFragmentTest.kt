package com.remember.remember.ui.home.memory_line.participants

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class MemoryLineParticipantsFragmentTest {

    @get:Rule
    val koinRule = KoinRule(
        KoinModules.modules + mockedRepositories,
        start = false,
        configAndroidThreeTen = false
    )

    @Test
    fun openWithFirstIsOwner_shouldShowOwnerMemoryLine() {
        memoryParticipantsFragment {
            firstIsOwner()
        } launch {

        } check {
            firstIsOwner()
        }
    }

    @Test
    fun openWithFirstIsNotOwner_shouldNotShowOwnerMemoryLine() {
        memoryParticipantsFragment {
            firstIsNotOwner()
        } launch {

        } check {
            firstIsNotOwner()
        }
    }

    @Test
    fun openWithNoParticipants_shouldEmptyState() {
        memoryParticipantsFragment {
            noParticipants()
        } launch {

        } check {
            emptyState()
        }
    }

    @Test
    fun openWithError_shouldShowErrorState() {
        memoryParticipantsFragment {
            withError()
        } launch {

        } check {
            errorState()
        }
    }

}