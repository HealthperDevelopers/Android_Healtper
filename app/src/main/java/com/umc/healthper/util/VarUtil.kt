package com.umc.healthper.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.kakao.sdk.common.KakaoSdk
import com.umc.healthper.BuildConfig

class VarUtil: Application() {
    companion object {
        lateinit var glob: GlobVar
        lateinit var mSharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        glob = GlobVar()
        KakaoSdk.init(this, "${BuildConfig.KAKAO_API_KEY}")
        mSharedPreferences = applicationContext.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE)
    }
}