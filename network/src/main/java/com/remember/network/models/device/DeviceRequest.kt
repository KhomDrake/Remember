package com.remember.network.models.device

import com.google.gson.annotations.SerializedName

class DeviceRequest(
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("os_version")
    val osVersions: String,
    @SerializedName("firebase_token")
    val firebaseToken: String
)