package com.remember.moment.ui.create.select_memory_line.adapter

import androidx.navigation.NavController
import com.remember.common.model.toMemoryLineBaseInformation
import com.remember.common.ui.memory_line.MemoryLinesFragment
import com.remember.extension.navControllerProvider
import com.remember.moment.ui.create.select_memory_line.ChooseMemoryLineFragmentDirections
import com.remember.repository.model.memoryline.MemoryLine
import java.io.File

class ChooseMemoryLinesFragment(
    private val file: File? = null,
    typeMemoryLine: String = "",
    titleText: String = "",
    withImages: Boolean = true
) : MemoryLinesFragment(typeMemoryLine, titleText, withImages) {

    private val navController: NavController by navControllerProvider()

    override fun onClickMemoryLine(memoryLine: MemoryLine) {
        file?.let {
            navController.navigate(
                ChooseMemoryLineFragmentDirections.sendToBucket(
                    it,
                    memoryLine.toMemoryLineBaseInformation()
                )
            )
        }

    }

    override fun onClickMoreParticipants(id: String) {
        return
    }
}
