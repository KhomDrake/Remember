package com.remember.moment.ui.create

import androidx.lifecycle.ViewModel
import com.remember.repository.CreateMomentRepository
import com.remember.repository.ProfileRepository
import com.remember.repository.TypesRepository
import com.remember.repository.model.moment.MomentBody

class MomentViewModel(
    private val profileRepository: ProfileRepository,
    private val typesRepository: TypesRepository,
    private val createMomentRepository: CreateMomentRepository
) : ViewModel() {

    var description: String = ""

    fun types() = typesRepository.memoryLineTypes()

    fun insertMemoryLine(
        moment: String,
        description: String,
        idMemoryLine: String
    ) = createMomentRepository.insertMoment(
        MomentBody(
            idMemoryLine,
            moment,
            description
        )
    )

    fun profileInformation() = profileRepository.informationProfile()

}
