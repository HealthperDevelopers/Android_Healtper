package com.umc.healthper.util

import android.app.Application
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
        KakaoSdk.init(this, "${BuildConfig.kakao_api_key}")
    }
}