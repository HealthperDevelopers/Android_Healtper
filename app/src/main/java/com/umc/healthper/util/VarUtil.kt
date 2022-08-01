package com.umc.healthper.util

import android.app.Application

class VarUtil: Application() {
    companion object {
        lateinit var glob: GlobVar
    }

    override fun onCreate() {
        super.onCreate()
        glob = GlobVar()
    }
}