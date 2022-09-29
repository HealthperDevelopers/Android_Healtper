package com.umc.healthper.ui

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient
import com.umc.healthper.R
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.entity.WorkPart
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.data.remote.CalendarResponse
import com.umc.healthper.data.remote.GetDayDetailFirst
import com.umc.healthper.ui.Signup.SignupActivity
import com.umc.healthper.ui.login.LoginActivity
import com.umc.healthper.ui.login.LoginView
import com.umc.healthper.ui.main.view.DetailFirstView
import java.io.InputStream
import com.umc.healthper.util.VarUtil
import com.umc.healthper.util.getAutoLogin
import java.util.*
import kotlin.collections.ArrayList

class SplashActivity : AppCompatActivity(), DetailFirstView, LoginView  {
    var isToken = false
    var dataGet = false
    override fun onDestroy() {
        super.onDestroy()
        Log.d("splashActivity", "finish")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        initDb(applicationContext)

//        val conn = AuthService()
//        conn.dayInfoData = this
//        val now = Calendar.getInstance()
//        val y = now.get(Calendar.YEAR)
//        val m = now.get(Calendar.MONTH) + 1
//        val d = now.get(Calendar.DATE)
//        conn.dayInfo("$y-$m-$d")

        Handler().postDelayed({
            splashlogin()
        },DURATION)

    }
    companion object {
        private const val DURATION : Long = 3000
    }

    fun initDb(context: Context) {
        val db = LocalDB.getInstance(context)!!
        val manager: AssetManager = context.assets
        if (db.WorkPartDao().getFirst() == null) {
            val partData = manager.open("workPartList.txt")

            partData.bufferedReader().readLines().forEach {
                val dataList = it.split(" ")
                val id = dataList[0].toInt()
                val partEng = dataList[1]
                val partKor = dataList[2]
                val partColor = dataList[3]

                val partData = WorkPart(id, partKor, partColor)
                db.WorkPartDao().insert(partData)
            }
        }
        if (db.WorkDao().getFirst() == null) {
            val input: InputStream = manager.open("workList.txt")
            var part = ""
            var partId = 0
            var isPart = false
            input.bufferedReader().readLines().forEach {
                val work = it
                if (isPart) {
                    part = work
                    partId = db.WorkPartDao().getWorkPartIdbyPartName(part)
                    isPart = false
                }
                else if (work == "-") isPart = true
                else if (!isPart) {
                    var inp = Work(
                        0,work, partId, 0, 0, 0
                    )
                    db.WorkDao().insert(inp)
//                        CoroutineScope(Dispatchers.IO).launch {
//
//                        }
                }
            }
        }

        if (VarUtil.glob.unselectedPart.size == 0 &&
            VarUtil.glob.selectedPart.size == 0) {
            for (i in db.WorkPartDao().getAllWork()) {
                VarUtil.glob.unselectedPart.add(i)
            }
        }

    }

    override fun onDetailFirstGetSuccess(data: ArrayList<GetDayDetailFirst>) {
        VarUtil.glob.detailFirstList = data
    }

    override fun onDetailFirstGetFailure() {
    }



    fun splashlogin(){
        Log.d("autoLogin", getAutoLogin().toString())
        if (getAutoLogin()) {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }
                else if (tokenInfo != null) {
                    Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    Log.d("ID tokeninfo", tokenInfo.id.toString())
                    isToken = true
                    // api 들어갈 자리
                    val authService = AuthService()
                    authService.loginData = this
                    authService.login(tokenInfo!!.id.toString())
//                    val now = Calendar.getInstance()
//                    authService.coCalInfo(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1)
                }
            }
        }
        else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }

    override fun onLoginSuccess(data: List<CalendarResponse>?) {
        if (!data.isNullOrEmpty()) {
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
            Log.d("onLoginSuccess", "onLoginSuccess")
            VarUtil.glob.calData = ArrayList(data)
            val authService = AuthService()
            val now = Calendar.getInstance()

            val conn = AuthService()
            conn.dayInfoData = this
            val y = now.get(Calendar.YEAR)
            val m = now.get(Calendar.MONTH) + 1
            val d = now.get(Calendar.DATE)
            conn.dayInfo(String.format("%04d-%02d-%02d", y, m, d))

            authService.coCalInfo(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1)
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLoginFailure() {
        Log.d("onLoginFailure", "onLoginFailure")
        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onSignUp(user: String) {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)

        finish()
    }
}