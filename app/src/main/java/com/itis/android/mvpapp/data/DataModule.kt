package com.itis.android.mvpapp.data

import android.content.Context
import android.content.SharedPreferences
import com.itis.android.mvpapp.data.network.NetworkModule
import com.itis.android.mvpapp.data.util.CredentialStorage
import com.itis.android.mvpapp.data.util.PreferencesCredentialStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, RepositoryBuilder::class])
class DataModule {

    companion object {
        private const val CREDENTIALS_PREFERENCES = "CREDENTIALS_PREFERENCES"
    }

    @Provides
    @Singleton
    fun provideCredentialStorage(context: Context): CredentialStorage {
        val sharedPreferences = context.getSharedPreferences(CREDENTIALS_PREFERENCES, Context.MODE_PRIVATE)
        return PreferencesCredentialStorage(context, sharedPreferences)
    }
}