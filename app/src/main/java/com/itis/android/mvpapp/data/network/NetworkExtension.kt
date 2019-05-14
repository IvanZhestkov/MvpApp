package com.itis.android.mvpapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.itis.android.mvpapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}

fun OkHttpClient.Builder.addLoggingWhenDebug(): OkHttpClient.Builder {
    if (BuildConfig.DEBUG) {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return addInterceptor(interceptor)
    }
    return this
}