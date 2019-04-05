package com.itis.android.mvpapp.presentation.utils.validation

import java.util.regex.Pattern

class EmailValidator {

    private val validEmailAddressRegex = Pattern.compile("[A-Z0-9a-z._%+-]+@stud.kpfu.ru", Pattern.CASE_INSENSITIVE)

    fun isValid(emailStr: String): Boolean {
        val matcher = validEmailAddressRegex.matcher(emailStr)
        return matcher.find()
    }
}
