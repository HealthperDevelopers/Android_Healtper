package com.umc.healthper.data.remote

import com.umc.healthper.data.entity.TotalData
import com.umc.healthper.data.entity.User
import com.umc.healthper.ui.timer.data.Work
import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {
//    @POST("/app/users/user")
//    fun login(@Body user: User): Call<AuthResponse>

    @GET("/login")
    fun login(
        @Query ("kakaoId") kakaoId : String
    ) : Call<List<AuthResponse>>

    @GET("/home")
    fun isLogin() : Call<Test>

    @POST("/record")
    fun todayRecord(@Body totalData : TotalData) : Call<AuthResponse>

    @POST("/finish/{recordId}")
    fun detailRecord(@Body work : ArrayList<Work>) : Call<AuthResponse>
}