package com.remember.extension

import android.util.Patterns
import java.util.regex.Pattern

fun String.isValidEmail() : Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidDate() : Boolean {
    val date = this.split('/')

    if(date.count() != 3)
        return false

    if((date[0].toInt() in 1..31).not())
        return false

    if((date[1].toInt() in 1..12).not())
        return false

    if((date[2].toInt() in 1960..2019).not())
        return false

    return true
}

fun String.validDDD(): Boolean {
    val regex = "[1-9]{2}"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.validPhoneNumber() : Boolean {
    val regex = "^[\\s9]?\\d{4}-?\\d{4}\$"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.isValidPassword() : Boolean {
    val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_?:-])(?=\\S+$).{4,}$"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)

    return matcher.matches()
}

fun String.toDateUS() : String {
    val date = this.split('/')
    return "${date[2]}-${date[1]}-${date[0]}"
}
