package com.remember.common.actions

import android.content.Context
import android.content.Intent

const val MEMORY_LINE_ID = "MEMORY_LINE_ID"
const val TYPE_MOMENT = "TYPE_MOMENT"
const val MEMORY_LINE_NAME = "MEMORY_LINE_NAME"
const val OWNER_ID = "OWNER_ID"

fun Context.toCreateMoment(
    type: String,
    idMemoryLine: String? = null,
    memoryLineName: String? = null,
    ownerId: String = ""
) = Intent().setAction("$packageName.CREATE_MOMENT").apply {
    putExtra(TYPE_MOMENT, type)
    putExtra(MEMORY_LINE_ID, idMemoryLine)
    putExtra(MEMORY_LINE_NAME, memoryLineName)
    putExtra(OWNER_ID, ownerId)
}
const val MOMENT_ID = "MOMENT_ID"
const val WITH_COMMENT = "WITH_COMMENT"

fun Context.toMomentDetail(
    idMoment: String? = null,
    withComment: Boolean = false
) = Intent().setAction("$packageName.MOMENT_DETAIL").apply {
    putExtra(MOMENT_ID, idMoment)
    putExtra(WITH_COMMENT, withComment)
}

const val IS_FOR_RESULT = "IS_FOR_RESULT"
const val ACTION = "ACTION"

fun Context.toGallery(isForResult: Boolean = true, action: String? = null) = Intent().setAction("$packageName.GALLERY").apply {
    putExtra(IS_FOR_RESULT, isForResult)
    putExtra(ACTION, action)
}