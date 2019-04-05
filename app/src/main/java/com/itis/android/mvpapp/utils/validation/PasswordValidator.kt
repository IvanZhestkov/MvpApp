package com.itis.android.mvpapp.utils.validation

import java.util.regex.Pattern

class PasswordValidator {

    private val atLeastOneNumberPattern = Pattern.compile(
            "^(?=.*[a-z])(?=.*\\d)[a-zA-Z\\d]{8,25}\$")

    fun isValid(password: String): Boolean {
        if (password.length > 20) {
            return false
        }
        if (!atLeastOneNumberPattern.matcher(password).find()) {
            return false
        }
        return true
    }
}
