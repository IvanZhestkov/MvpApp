package com.itis.android.mvpapp.presentation.util.color

import android.content.Context
import android.support.v4.content.ContextCompat
import com.itis.android.mvpapp.R

object ColorGenerator {

    fun getColor(context: Context): Int {
        val colors = listOf(
                R.color.colorAvatarBlue,
                R.color.colorAvatarDarkBlue,
                R.color.colorAvatarRed,
                R.color.colorAvatarOrange,
                R.color.colorAvatarYellow,
                R.color.colorAvatarGreen,
                R.color.colorAvatarPurple,
                R.color.colorAvatarPink
        )

        return ContextCompat.getColor(context, colors.shuffled()[0])
    }

}