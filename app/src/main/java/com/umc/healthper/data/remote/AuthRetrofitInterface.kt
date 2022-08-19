package com.umc.healthper.data.remote

import com.umc.healthper.data.entity.TotalData
import com.umc.healthper.ui.timer.data.Work
import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {

    @GET("/login")
    fun login(
        @Query ("kakaoId") kakaoId : String
    ) : Call<List<CalenderResponse>>

    @GET("/record/calender")
    fun calenderInfo(
        @Query ("year") year : Int,
        @Query ("month") month: Int
    ) : Call<List<CalenderResponse>>

    @GET("/record/info")
    fun dayInfo(
        @Query ("theDay") theDay : String
    ) : Call<List<DayResponse>>

    @POST("/record")
    fun todayRecord(@Body totalData : TotalData) : Call<Int>

    @POST("/finish/{recordId}")
    fun detailRecord(
        @Body work : ArrayList<Work>,
        @Path("recordId") recordId : Int
    ) : Call<CalenderResponse>
}