package com.remember.network.models.type

import com.google.gson.annotations.SerializedName

class MemoryLineTypeResponse(
    @SerializedName("id")
    val idTypeOwner: String,
    val type: TypeResponse,
    val priority: Int
)

class TypeResponse(
    val id: String,
    val name: String
)

class MemoryLineTypePaginationResponse(
    val next: String?,
    val results: List<MemoryLineTypeResponse>
)

class UpdateMemoryLineTypeRequest(
    val id: String,
    val type: String,
    val priority: Int
)