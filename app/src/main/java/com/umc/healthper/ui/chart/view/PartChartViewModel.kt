package com.umc.healthper.ui.chart.view

import android.content.res.ColorStateList
import androidx.lifecycle.ViewModel
import com.umc.healthper.util.VarUtil

class PartChartViewModel : ViewModel() {
    var partChartModel = PartChartModel()
    var partName : String = ""

    fun gotoMypage() {
        VarUtil.glob.mainActivity.Mypage()
    }

    fun setPartColor(): ColorStateList {
        return partChartModel.setPartColor(partName)
    }
}