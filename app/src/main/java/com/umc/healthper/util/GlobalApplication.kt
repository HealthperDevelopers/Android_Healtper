package com.umc.healthper.util

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "e19014eb3f73def98b97a0bd66106387")
    }
}