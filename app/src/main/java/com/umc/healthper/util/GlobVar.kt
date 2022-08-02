package com.umc.healthper.util

import android.app.Application
import android.content.Context
import android.widget.TextView

class GlobVar(): Application() {
    lateinit var mainContext: Context
    lateinit var today: TextView
}