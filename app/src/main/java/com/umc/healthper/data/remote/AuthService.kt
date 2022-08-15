package com.umc.healthper.data.remote

import android.util.Log
import com.umc.healthper.data.entity.User
import com.umc.healthper.util.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {

    fun test(user : String)
    {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        val tmplist = authService.test(user)
        Log.d("tmplist", tmplist.toString())
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