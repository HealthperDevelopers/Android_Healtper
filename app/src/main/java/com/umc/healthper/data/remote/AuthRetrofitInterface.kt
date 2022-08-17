package com.umc.healthper.data.remote

import com.umc.healthper.data.entity.User
import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {
//    @POST("/app/users/user")
//    fun login(@Body user: User): Call<AuthResponse>

    @GET("/login")
    fun test(
        @Query ("kakaoId") kakaoId : String
    ) : Call<List<AuthResponse>>

    @GET("/home")
    fun isLogin() : Call<Test>
}