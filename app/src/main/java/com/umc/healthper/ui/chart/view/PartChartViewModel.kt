package com.umc.healthper.ui.chart.view

import androidx.lifecycle.ViewModel
import com.umc.healthper.util.VarUtil

class PartChartViewModel : ViewModel() {
    fun gotoMypage() {
        VarUtil.glob.mainActivity.Mypage()
    }
}