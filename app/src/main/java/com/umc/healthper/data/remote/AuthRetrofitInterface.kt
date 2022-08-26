package com.umc.healthper.data.remote

import com.umc.healthper.data.entity.Post
import com.umc.healthper.data.entity.TotalData
import com.umc.healthper.data.entity.WorkRecord
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AuthRetrofitInterface {

    @GET("/post/{postId}")
    fun viewPost(
        @Path("postId") postId : Int
    ) : Call<APostResponse>

    @POST("/post")
    fun postPost(@Body post : Post) : Call<PostId>

    @GET("/posts")
    fun getPosts(
        @Query ("sort") sortType : String,
        @Query ("page") page : Int
    ) : Call<PostsResponse>

    @GET("/login")
    fun login(
        @Query ("kakaoId") kakaoId : String
    ) : Call<List<CalendarResponse>>
//    로그인 시 캘린더 정보를 동시에 받아옴 = calenderInfo

    @GET("/record/calender")
    fun calenderInfo(
        @Query ("year") year : Int,
        @Query ("month") month: Int
    ) : Call<List<CalendarResponse>>
//    캘린더 내의 정보 받아오는 함수. 점 3개

    @GET("/record/info")
    fun dayInfo(
        @Query ("theDay") theDay : String
    ) : Call<List<GetDayDetailFirst>>
//    캘린더 밑에 운동 정보 받아오는 함수. theDay 예시 = "2022-08-23"

    @POST("/record")
    fun todayRecord(@Body totalData : TotalData) : Call<Int>
//    오늘 운동 데이터 저장

    @POST("/finish/{recordId}")
    fun detailRecord(
        @Body work : ArrayList<SetDayDetailSecond>,
        @Path("recordId") recordId : Int
    ) : Call<String>
//    상세 정보 등록

    @GET("/finish/{recordId}")
    fun getDetail(
        @Path("recordId") recordId : Int
    ): Call<List<GetDayDetailSecond>>
    //상세정보 가져오기

    @GET("/record/calender")
    suspend fun coCalInfo(
        @Query ("year") year : Int,
        @Query ("month") month: Int
    ) : Response<List<CalendarResponse>>
}