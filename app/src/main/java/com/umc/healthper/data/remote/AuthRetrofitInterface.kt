package com.umc.healthper.data.remote

import com.umc.healthper.data.entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/app/users/user")
    fun login(@Body user: User): Call<AuthResponse>
}