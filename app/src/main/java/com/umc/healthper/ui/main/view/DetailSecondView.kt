package com.umc.healthper.ui.main.view

import com.umc.healthper.data.remote.GetDayDetailSecond

interface DetailSecondView {
    fun daySecondDetailonSuccess(data: ArrayList<GetDayDetailSecond>)
}