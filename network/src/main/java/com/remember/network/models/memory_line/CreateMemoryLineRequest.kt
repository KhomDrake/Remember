package com.remember.network.models.memory_line

import com.google.gson.annotations.SerializedName

class CreateMemoryLineRequest(
    @SerializedName("title")
    val name: String,
    val type: String,
    val description: String = ""
)
