package com.remember.moment.ui.create.camera

import androidx.navigation.NavController
import com.remember.camera.ui.BaseCameraFragment
import com.remember.common.actions.MEMORY_LINE_ID
import com.remember.common.actions.MEMORY_LINE_NAME
import com.remember.common.actions.OWNER_ID
import com.remember.common.actions.TYPE_MOMENT
import com.remember.common.model.MemoryLineBaseInformation
import com.remember.common.model.MomentType
import com.remember.extension.navControllerProvider
import java.io.File

class MomentCameraFragment : BaseCameraFragment() {

    private var memoryLineBaseInformation: MemoryLineBaseInformation? = null
    private val navController: NavController by navControllerProvider()

    override fun setupCameraFragment() {
        activity?.run {
            val type = intent?.getStringExtra(TYPE_MOMENT) ?: MomentType.GALLERY.name
            val id = intent?.getStringExtra(MEMORY_LINE_ID)
            val ownerId = intent?.getStringExtra(OWNER_ID)
            val name = intent?.getStringExtra(MEMORY_LINE_NAME)

            if(id != null && ownerId != null && name != null)
                memoryLineBaseInformation = MemoryLineBaseInformation(id, ownerId, name)

            if (type == MomentType.GALLERY.name) {
                navController.navigate(
                    MomentCameraFragmentDirections.directToGallery(memoryLineBaseInformation)
                )
            } else {
                setupCamera()
            }
        } ?: run {
            setupCamera()
        }
    }

    private fun setupCamera() {
        setupStartCamera()
        startCamera()
    }

    override fun onTakePicture(compressedFile: File) {
        navController.navigate(
            MomentCameraFragmentDirections.goToPreviewPhotoFromCamera(
                memoryLineBaseInformation,
                compressedFile
            )
        )
    }
}
