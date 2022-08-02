package com.umc.healthper.util

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class VarUtil: Application() {
    companion object {
        lateinit var glob: GlobVar
        // lateinit var globalApplication: GlobalApplication
    }

    override fun onCreate() {
        super.onCreate()
        glob = GlobVar()
        KakaoSdk.init(this, "e19014eb3f73def98b97a0bd66106387")
    }
}