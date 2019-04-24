package com.itis.android.mvpapp.presentation.utils.validation

import java.util.regex.Pattern

object Validator {
    private val validEmailAddressRegex = Pattern.compile(
        "[A-Z0-9a-z._%+-]+@stud.kpfu.ru", Pattern.CASE_INSENSITIVE
    )

    private val atLeastOneNumberPattern = Pattern.compile(
        "^(?=.*[a-z])(?=.*\\d)[a-zA-Z\\d]{8,25}\$"
    )

    fun isEmailValid(emailStr: String): Boolean {
        /*val matcher = validEmailAddressRegex.matcher(emailStr)
        return matcher.find()*/
        return true
    }

    fun isPasswordValid(password: String): Boolean {
        /*if (password.length > 20) {
            return false
        }
        if (!atLeastOneNumberPattern.matcher(password).find()) {
            return false
        }*/
        return true
    }
}