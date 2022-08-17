package com.umc.healthper.data.remote

import android.util.Log
import com.umc.healthper.data.entity.User
import com.umc.healthper.util.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {

    fun isLogin() {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.isLogin().enqueue(object : Callback<Test>{
            override fun onResponse(call: Call<Test>, response: Response<Test>) {
//                val resp: Test = response.body()!!
                Log.d("resp str/ success", response.toString())
            }

            override fun onFailure(call: Call<Test>, t: Throwable) {
                Log.d("resp str/ fail", t.message.toString())
            }
        })
    }
    fun test(user : String)
    {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        // val tmplist = authService.test(user)
        // Log.d("tmplist", tmplist.toString())

        authService.test(user).enqueue(object :Callback<List<AuthResponse>> {
            override fun onResponse(call: Call<List<AuthResponse>>, response: Response<List<AuthResponse>>
            ) {
                Log.d("test login/success", response.toString())
//                val resp : List<AuthResponse> = response.body()!!
//                Log.d("test login/resp body", resp.first().day.toString())
//                Log.d("test login/resp body", resp.first().sections.toString())
            }

            override fun onFailure(call: Call<List<AuthResponse>>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }
        })
    }
//    fun login(user : User){
//
//        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
//
//        authService.login(user).enqueue(object: Callback<AuthResponse> {
//            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
//                Log.d("LOGIN/SUCCESS", response.toString())
//                val resp: AuthResponse = response.body()!!
////                when(val code = resp.code){
////                    1000-> {
////                        loginView.onLoginSuccess(code, resp.result!!)
////                    }
////                    else->loginView.onLoginFailure(resp.message, resp.code)
////                }
//            }
//
//            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//                Log.d("LOGIN/FAILURE", t.message.toString())
//            }
//        })
//        Log.d("LOGIN", "HELLO")
//    }
}