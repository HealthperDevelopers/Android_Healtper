package com.umc.healthper.ui.main.view

import com.umc.healthper.data.remote.CalendarResponse

interface CalendarDataView {
    fun CalendarDataGetSuccess(data: ArrayList<CalendarResponse>)
}