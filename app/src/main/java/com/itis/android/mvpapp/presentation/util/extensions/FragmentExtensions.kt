package com.itis.android.mvpapp.presentation.util.extensions

import android.os.Bundle
import android.support.v4.app.Fragment
import com.itis.android.mvpapp.router.initparams.InitParams
import java.lang.IllegalStateException

private const val BF_INIT_PARAMS = ".init.params"

fun <T : InitParams> Fragment.extractInitParams(): T {
    return arguments?.getParcelable(BF_INIT_PARAMS) ?: throw IllegalStateException("initParams should not be null")
}

fun <T : Fragment> T.putInitParams(initParams: InitParams) {
    arguments = Bundle().apply {
        putParcelable(BF_INIT_PARAMS, initParams)
    }
}