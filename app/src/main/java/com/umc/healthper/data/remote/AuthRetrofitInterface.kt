package com.umc.healthper.data.remote

import com.umc.healthper.data.entity.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AuthRetrofitInterface {

    @POST ("/member")
    fun signup(
        @Body userInfo : User
    ) : Call<Void>

    @GET ("/member")
    fun getNickname(
        @Query ("kakaoKey") kakaoKey : Long
    ) : Call<MemberResponse>

    @GET("/statistic")
    fun statistic(
        @Query ("exerciseName") exerciseName : String
    ) : Call<ChartData>

    /** 댓글 생성 */
    @POST("/comment")
    fun comment(
        @Body comment : Comment
    ) : Call<Void>

    /** 대댓글 생성 */
    @POST("/comment-nested")
    fun childcomment(
        @Body childcomment : ChildComment
    ) : Call<Void>

    /** 댓글 & 대댓글 삭제 */
    @DELETE ("/comment/{commentId}")
    fun deleteComment(
        @Path("commentId") commentId : Int
    ) : Call<Void>

    @GET("/logout")
    fun logout() : Call<Void>

    /** 게시글 생성 */
    @POST("/post")
    fun postPost(@Body post : Post) : Call<PostId>

    /** 게시글 조회 */
    @GET("/post/{postId}")
    fun viewPost(
        @Path("postId") postId : Int
    ) : Call<APostResponse>

    /** 게시글 삭제 */
    @DELETE("/post/{postId}")
    fun deletePost(
        @Path("postId") postId : Int
    ) : Call<Void>

    /** 게시글 좋아요 추가 */
    @POST("/post/{postId}/like")
    fun RecommendPost(
        @Path("postId") postId : Int
    ) : Call<Void>

    /** 게시글 좋아요 취소 */
    @DELETE("/post/{postId}/like")
    fun UnrecommendPost(
        @Path("postId") postId : Int
    ) : Call<Void>

    /** 게시글 목록 조회 */
    @GET("/posts")
    fun getPosts(
        @Query ("type") type : String,
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