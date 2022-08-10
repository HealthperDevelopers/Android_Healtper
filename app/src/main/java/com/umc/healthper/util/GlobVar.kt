package com.umc.healthper.util

import android.app.Application
import android.content.Context
import android.graphics.Point
import android.widget.TextView
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.ui.main.adapter.WorkReadyListAdapter

class GlobVar: Application() {
    lateinit var mainContext: Context
    lateinit var mainActivity: MainActivity
    lateinit var today: TextView
    lateinit var size: Point
    var restMinutes : Int = 60


    var selectedPart = ArrayList<String>()
    var unselectedPart = ArrayList<String>()

    lateinit var workReadyAdapter: WorkReadyListAdapter
    var currentPart: String = ""
    var currentWork: String = ""
}