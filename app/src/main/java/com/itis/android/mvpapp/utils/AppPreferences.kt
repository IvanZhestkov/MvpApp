package com.itis.android.mvpapp.utils

import android.content.Context
import com.itis.android.mvpapp.utils.Constants.PREF_KEY_ACCESS_TOKEN
import com.itis.android.mvpapp.utils.Constants.SHARED_PREF_NAME
import javax.inject.Inject

class AppPreferences
@Inject constructor(private val context: Context) {

    private val preferences by lazy {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getAccessToken(): String? {
        return preferences.getString(PREF_KEY_ACCESS_TOKEN, null)
    }

    fun setAccessToken(token: String?) {
        preferences.edit().putString(PREF_KEY_ACCESS_TOKEN, token).apply()
    }
}