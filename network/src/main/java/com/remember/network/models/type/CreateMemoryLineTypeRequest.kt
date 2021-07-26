package com.remember.network.models.type

class CreateMemoryLineTypeRequest(
    val name: String,
    val detail: CreateMemoryLineTypeDetailRequest
)

class CreateMemoryLineTypeDetailRequest(
    val priority: Int
)