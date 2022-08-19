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
//    로그인 시 캘린더 정보를 동시에 받아옴 = calenderInfo

    @GET("/record/calender")
    fun calenderInfo(
        @Query ("year") year : Int,
        @Query ("month") month: Int
    ) : Call<List<CalenderResponse>>
//    캘린더 내의 정보 받아오는 함수. 점 3개

    @GET("/record/info")
    fun dayInfo(
        @Query ("theDay") theDay : String
    ) : Call<List<DayResponse>>
//    캘린더 밑에 운동 정보 받아오는 함수. theDay 예시 = "2022-08-23"

    @POST("/record")
    fun todayRecord(@Body totalData : TotalData) : Call<Int>
//    오늘 운동 데이터 저장

    @POST("/finish/{recordId}")
    fun detailRecord(
        @Body work : ArrayList<Work>,
        @Path("recordId") recordId : Int
    ) : Call<CalenderResponse>
//    상세 정보 등록

}