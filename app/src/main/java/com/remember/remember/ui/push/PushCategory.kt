package com.remember.remember.ui.push

import android.app.NotificationManager
import androidx.annotation.StringRes
import com.remember.remember.R

enum class PushCategory(val importance: Int, @StringRes val description: Int, @StringRes val categoryName: Int) {
    APP_INFORMATION(NotificationManager.IMPORTANCE_DEFAULT, R.string.app_app_information_description, R.string.app_app_information_name),
    INVITE(NotificationManager.IMPORTANCE_DEFAULT, R.string.app_invite_description, R.string.app_invite_name),
    COMMENT(NotificationManager.IMPORTANCE_DEFAULT, R.string.app_comment_description, R.string.app_comment_name),
    MEMORY_LINE(NotificationManager.IMPORTANCE_DEFAULT, R.string.app_memory_line_description, R.string.app_memory_line_name),
    MOMENT(NotificationManager.IMPORTANCE_DEFAULT, R.string.app_moment_description, R.string.app_moment_name),
    ADVERTISING(NotificationManager.IMPORTANCE_DEFAULT, R.string.app_advertising_description, R.string.app_advertising_name)
}
