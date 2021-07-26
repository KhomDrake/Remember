package com.remember.repository.push

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.Settings
import com.remember.network.models.device.DeviceRequest

object InformationDevice {
    private var _token: String? = null
    private var _deviceId: String? = null
    private var _osVersion: String? = null
    private var _name: String? = null

    fun informationDevice() = DeviceRequest(
        _deviceId ?: "",
        _name ?: "",
        _osVersion ?: "",
        _token ?: ""
    )

    fun setupNewToken(token: String) {
        _token = token
    }

    @SuppressLint("HardwareIds")
    fun setupInformationDevice(token: String?, contentResolver: ContentResolver, osVersion: String) {
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val name = Settings.Global.getString(contentResolver, "device_name")
        _token = token
        _deviceId = deviceId
        _osVersion = osVersion
        _name = name
    }
}