package com.itis.android.mvpapp.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Single

class PreferencesCredentialStorage(
        val context: Context,
        val preferences: SharedPreferences
) : CredentialStorage {

    companion object {
        private const val KEY_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        private const val KEY_USER_ROLE = "PREF_KEY_USER_ROLE"
    }

    @SuppressLint("ApplySharedPref")
    override fun getSaveTokenCompletable(token: String): Completable {
        return Completable.fromAction {
            preferences.edit()
                    .putString(KEY_TOKEN, token)
                    .commit()
        }
    }

    @SuppressLint("ApplySharedPref")
    override fun getSaveTokenSingle(): Single<String> {
        return Single.fromCallable {
            preferences.getString(KEY_TOKEN, null)
                    ?: throw IllegalArgumentException("token is null")
        }
    }

    override fun saveUserRole(role: String?) {
        preferences.edit()
                .putString(KEY_USER_ROLE, role)
                .apply()
    }

    override fun getUserRole(): String? {
        return preferences.getString(KEY_USER_ROLE, null)
    }

    override fun clear() {
        preferences.edit().clear().apply()
    }
}