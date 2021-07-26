package com.remember.remember.ui.home.adapter

import android.os.Bundle
import androidx.navigation.NavController
import com.remember.common.model.toMemoryLineBaseInformation
import com.remember.common.ui.memory_line.MemoryLinesFragment
import com.remember.extension.navControllerProvider
import com.remember.remember.R
import com.remember.repository.model.memoryline.MemoryLine

const val MEMORY_LINE_ID_BUNDLE = "idMemoryLine"
const val MEMORY_LINE_INFORMATION_BUNDLE = "memoryLineData"

class MemoryLinesHomeFragment(
    typeMemoryLine: String = "",
    titleText: String = "",
    withImages: Boolean = true
) : MemoryLinesFragment(typeMemoryLine, titleText, withImages) {
    private val navController: NavController by navControllerProvider()

    override fun onClickMemoryLine(memoryLine: MemoryLine) {
        navController.navigate(
            R.id.action_navigation_home_to_memoryLineFragment2,
            Bundle().apply {
                putParcelable(MEMORY_LINE_INFORMATION_BUNDLE, memoryLine.toMemoryLineBaseInformation())
            }
        )
    }

    override fun onClickMoreParticipants(id: String) {
        navController.navigate(
            R.id.action_navigation_home_to_memoryLineParticipantsFragment,
            Bundle().apply {
                putString(MEMORY_LINE_ID_BUNDLE, id)
            }
        )
    }
}
