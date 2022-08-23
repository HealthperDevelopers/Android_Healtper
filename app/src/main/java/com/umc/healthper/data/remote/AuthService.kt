package com.umc.healthper.data.remote

import android.util.Log
import com.umc.healthper.data.entity.ExerciseInfo
import com.umc.healthper.data.entity.TotalData
import com.umc.healthper.data.entity.WorkRecord
import com.umc.healthper.util.VarUtil
import com.umc.healthper.util.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*
import kotlin.collections.ArrayList

class AuthService {

    fun dayInfo(theDay : String)
    {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.dayInfo(theDay).enqueue(object :Callback<List<DayResponse>> {
            override fun onResponse(call: Call<List<DayResponse>>, response: Response<List<DayResponse>>
            ) {
                Log.d("dayInfo/success", response.toString())
                val resp : List<DayResponse> = response.body()!!
                Log.d("dayInfo/resp body", resp.toString())
            }

            override fun onFailure(call: Call<List<DayResponse>>, t: Throwable) {
                Log.d("dayInfo/FAILURE", t.message.toString())
            }
        })
    }

    fun login(user : String)
    {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.login(user).enqueue(object :Callback<List<CalenderResponse>> {
            override fun onResponse(call: Call<List<CalenderResponse>>, response: Response<List<CalenderResponse>>
            ) {
                Log.d("login/success", response.toString())
                val resp : List<CalenderResponse> = response.body()!!
//                Log.d("login/resp body", resp.first().day.toString())
//                Log.d("login/resp body", resp.first().sections.toString())
            }

            override fun onFailure(call: Call<List<CalenderResponse>>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }
        })
    }

    fun calenderInfo(year : Int, month: Int)
    {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.calenderInfo(year, month).enqueue(object :Callback<List<CalenderResponse>> {
            override fun onResponse(call: Call<List<CalenderResponse>>, response: Response<List<CalenderResponse>>
            ) {
                when (response.code()){
                    200 -> {
                        val resp: List<CalenderResponse> = response.body()!!
                        Log.d("calender/resp body", resp.toString())
                    }
                    else -> {
                        Log.d("calender/FAILURE", "Fail")
                    }
                }
            }

            override fun onFailure(call: Call<List<CalenderResponse>>, t: Throwable) {
                Log.d("calender/FAILURE", t.message.toString())
            }
        })
    }

    fun todayRecord(totalData: TotalData) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.todayRecord(totalData).enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Log.d("record/totalData", totalData.toString())
                when (response.code()){
                    200 -> {
                        var resp : Int = response.body()!!
                        Log.d("record/resp", resp.toString())
                        Log.d("record/work", VarUtil.glob.work.toString())
                        detailRecord(VarUtil.glob.work, resp)
                    }
                    else -> {Log.d("record/FAILURE", "FAil")}
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.d("record/FAILURE", t.message.toString())
            }
        })
    }

    fun detailRecord(@Body work : ArrayList<WorkRecord>, @Path("recordId") recordId : Int){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.detailRecord(work, recordId).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("detail/response", response.toString())
                when (response.code()){
                    200 -> {
                        Log.d("detail/SUCCESS", response.toString())
                        VarUtil.glob.mainActivity.resetWorkData() // detail 저장 후 루틴 초기화
                    }
                    else -> {Log.d("detail/FAILURE", response.toString())}
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("detail/fail", t.message.toString())
            }
        })
    }
}