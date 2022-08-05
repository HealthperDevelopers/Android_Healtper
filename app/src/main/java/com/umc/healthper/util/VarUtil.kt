package com.umc.healthper.util

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.umc.healthper.BuildConfig

class VarUtil: Application() {
    companion object {
        lateinit var glob: GlobVar
        // lateinit var globalApplication: GlobalApplication
    }

    override fun onCreate() {
        super.onCreate()
        glob = GlobVar()
        KakaoSdk.init(this, "${BuildConfig.KAKAO_API_KEY}")
    }
}