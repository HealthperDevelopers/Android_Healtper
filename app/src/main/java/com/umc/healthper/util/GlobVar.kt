package com.umc.healthper.util

import android.app.Application
import android.content.Context
import android.graphics.Point
import android.widget.TextView
import com.umc.healthper.data.entity.ExerciseInfo
import com.umc.healthper.data.entity.TotalData
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.ui.main.adapter.WorkReadyListAdapter
import com.umc.healthper.ui.timer.data.Work

class GlobVar: Application() {
    lateinit var mainContext: Context
    lateinit var mainActivity: MainActivity
    lateinit var today: TextView
    lateinit var size: Point
    var restMinutes : Int = 60


    var selectedPart = ArrayList<String>()
    var unselectedPart = ArrayList<String>()
    var work : ArrayList<Work> = arrayListOf()
    var totalData : TotalData = TotalData("", ArrayList(), ExerciseInfo(0, 0))

    lateinit var workReadyAdapter: WorkReadyListAdapter
    var currentPart: String = ""
    var currentWork: String = ""
}