package com.umc.healthper.ui.login

import com.umc.healthper.data.remote.CalendarResponse

interface LoginView {
    fun onLoginSuccess(data: List<CalendarResponse>?)
}