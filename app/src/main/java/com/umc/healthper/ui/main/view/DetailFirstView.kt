package com.umc.healthper.ui.main.view

import com.umc.healthper.data.remote.GetDayDetailFirst

interface DetailFirstView {
    fun onDetailFirstGetSuccess(data: ArrayList<GetDayDetailFirst>)
    fun onDetailFirstGetFailure()
}