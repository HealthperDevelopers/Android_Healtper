package com.umc.healthper.data.remote

import android.content.Intent
import android.util.Log
import com.umc.healthper.ui.main.view.CalendarDataView
import com.umc.healthper.ui.main.view.DetailFirstView
import com.umc.healthper.ui.main.view.DetailSecondView

import android.widget.Toast
import com.kakao.sdk.user.UserApiClient
import com.umc.healthper.data.entity.Post
import com.umc.healthper.data.entity.TotalData
import com.umc.healthper.data.entity.WorkRecord
import com.umc.healthper.ui.SplashActivity
import com.umc.healthper.ui.login.LoginActivity
import com.umc.healthper.ui.login.LoginView
import com.umc.healthper.util.VarUtil
import com.umc.healthper.util.getRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path
import kotlin.collections.ArrayList

class AuthService {
    lateinit var dayInfoData: DetailFirstView
    lateinit var calendarData: CalendarDataView
    lateinit var dayDetailData: DetailSecondView
    lateinit var loginData: LoginView

    fun dayInfo(theDay : String)
    {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.dayInfo(theDay).enqueue(object :Callback<List<GetDayDetailFirst>> {
            override fun onResponse(call: Call<List<GetDayDetailFirst>>, response: Response<List<GetDayDetailFirst>>
            ) {
                if (response.code() == 200) {
                        Log.d("dayInfo/success", response.toString())
                        val resp: List<GetDayDetailFirst>? = response.body()
                    val arrResp = if (resp.isNullOrEmpty()) {
                        ArrayList()
                    }
                    else {
                        ArrayList<GetDayDetailFirst>(resp)
                    }
                        dayInfoData.onDetailFirstGetSuccess(arrResp)
                        Log.d("dayInfo/resp body", resp.toString())
                }
                else {
                    Log.d("dayInfo/failure", "fail")
                }
            }

            override fun onFailure(call: Call<List<GetDayDetailFirst>>, t: Throwable) {
                Log.d("dayInfo/FAILURE", t.message.toString())
            }
        })
    }

    fun login(user : String)
    {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.login(user).enqueue(object :Callback<List<CalendarResponse>> {
            override fun onResponse(call: Call<List<CalendarResponse>>, response: Response<List<CalendarResponse>>
            ) {
                when (response.code()){
                    200 -> {
                        Log.d("login/success", response.toString())
                        loginData.onLoginSuccess(response.body())
                    }
                    else -> {
                        Log.d("login/failure", response.toString())
                        loginData.onLoginFailure()
                    }
                }
            }

            override fun onFailure(call: Call<List<CalendarResponse>>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }
        })
    }

    fun calenderInfo(year : Int, month: Int)
    {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.calenderInfo(year, month).enqueue(object :Callback<List<CalendarResponse>> {
            override fun onResponse(call: Call<List<CalendarResponse>>, response: Response<List<CalendarResponse>>
            ) {
                when (response.code()){
                    200 -> {
                        val resp: List<CalendarResponse>? = response.body()
                        if (resp.isNullOrEmpty()) {

                        }
                        else {
                            calendarData.calendarDataGetSuccess(ArrayList(resp))
                            Log.d("calender/resp body", resp.toString())
                        }
                    }
                    else -> {
                        Log.d("calender/FAILURE", "Fail")
                    }
                }
            }

            override fun onFailure(call: Call<List<CalendarResponse>>, t: Throwable) {
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

    fun detailRecord(@Body work : ArrayList<SetDayDetailSecond>, @Path("recordId") recordId : Int){
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

    fun dayDetail(id : Int)
    {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.getDetail(id).enqueue(object: Callback<List<GetDayDetailSecond>> {
            override fun onResponse(
                call: Call<List<GetDayDetailSecond>>,
                response: Response<List<GetDayDetailSecond>>
            ) {
                val resp = response.body()
                Log.d("workData", resp.toString())
                val arrResp = ArrayList(resp)
                dayDetailData.daySecondDetailonSuccess(arrResp)
            }

            override fun onFailure(call: Call<List<GetDayDetailSecond>>, t: Throwable) {
            }

        })
    }

    fun postPost(post: Post){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.postPost(post).enqueue(object: Callback<PostId> {
            override fun onResponse(
                call: Call<PostId>,
                response: Response<PostId>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d("postPost/success", response.body().toString())
                    }
                    else -> {
                        Log.d("postPost/fail", response.body().toString())
                    }
                }
            }

            override fun onFailure(call: Call<PostId>, t: Throwable) {
                Log.d("postPost/fail onfailure", t.toString())
            }

        })
    }


    fun coCalInfo(year : Int, month: Int) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val resp = authService.coCalInfo(year, month)
            withContext(Dispatchers.Main) {
                if (resp.code() == 200) {
                    Log.d("coCalInfo/success", "coCalInfo/success")
                    if (!resp.body().isNullOrEmpty()) {
                        VarUtil.glob.calData  = ArrayList(resp.body())
                    }
                }
            }
        }
    }

    fun deleteComment(commentId : Int, postId : Int) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.deleteComment(commentId).enqueue(object: Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d("deleteComment/success", response.body().toString())
                        VarUtil.glob.mainActivity.boardFreepostContentFragment!!.viewPost(postId)
                    }
                    else -> {
                        Log.d("deleteComment/fail", response.body().toString())
                        Toast.makeText(VarUtil.glob.mainContext, "댓글을 수정/삭제할 수 있는 권한이 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("deleteComment/onfailure", t.toString())
            }

        })
    }

    fun logout(){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.logout().enqueue(object: Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d("logout/success", response.body().toString())
                        UserApiClient.instance.logout { error ->
                            if (error != null) {
                                Log.e("try logOut", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                            }
                            else {
                                Log.i("try logOut", "로그아웃 성공. SDK에서 토큰 삭제됨")
                            }
                        }
                    }
                    else -> {
                        Log.d("logout/fail", response.body().toString())
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("logout/onfailure", t.toString())
            }

        })
    }
}