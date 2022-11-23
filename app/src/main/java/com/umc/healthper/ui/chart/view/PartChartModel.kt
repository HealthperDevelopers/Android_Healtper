package com.umc.healthper.ui.chart.view

import android.content.res.ColorStateList
import android.graphics.Color
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.util.VarUtil

class PartChartModel {
    fun setPartColor(partName : String): ColorStateList {
        var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
        return ColorStateList.valueOf(
            Color.parseColor(db.WorkPartDao().getColorbyPartName(partName)))
    }
}