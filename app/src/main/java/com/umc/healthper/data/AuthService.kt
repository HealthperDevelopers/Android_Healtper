package com.umc.healthper.data

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    fun login(user : User){

        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.login(user).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("LOGIN/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!!
//                when(val code = resp.code){
//                    1000-> {
//                        loginView.onLoginSuccess(code, resp.result!!)
//                    }
//                    else->loginView.onLoginFailure(resp.message, resp.code)
//                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }
        })
        Log.d("LOGIN", "HELLO")
    }
}