package com.remember.repository.cache

import com.remember.network.auth.Authentication
import com.remember.repository.model.Album
import com.remember.repository.model.MemoryLineTypePagination
import com.remember.repository.model.memoryline.MemoryLineDetail
import com.remember.repository.model.memoryline.MemoryLinePagination
import com.remember.repository.model.moment.Moment
import com.remember.repository.model.moment.MomentsPagination
import com.remember.repository.model.participant.ParticipantPagination
import com.remember.repository.model.profile.Profile

object CacheBox {
    fun resetAllCache() {
        albumsHashMap.clear()
        memoryLineTypesHashMap.clear()
        memoryLineParticipantHashMap.clear()
        memoryLineDetailHashMap.clear()
        memoryLineHashMap.clear()
        momentsHashMap.clear()
        profileHashMap.clear()
    }

    val albumsHashMap: HashMap<String, List<Album>> = hashMapOf()
    val memoryLineHistory: HashMap<String, List<Moment>> = hashMapOf()
    val memoryLineTypesHashMap: HashMap<String, MemoryLineTypePagination> = hashMapOf()
    val memoryLineParticipantHashMap: HashMap<String, ParticipantPagination> = hashMapOf()
    val memoryLineDetailHashMap: HashMap<String, MemoryLineDetail> = hashMapOf()
    val momentDetailHashMap: HashMap<String, Moment> = hashMapOf()
    val memoryLineHashMap: HashMap<String, MemoryLinePagination> = hashMapOf()
    val momentsHashMap: HashMap<String, MomentsPagination> = hashMapOf()
    val profileHashMap: HashMap<String, Profile> = hashMapOf()

    val key: String
        get() = Authentication.getToken()
}