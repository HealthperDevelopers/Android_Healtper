package com.umc.healthper.ui

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.umc.healthper.R
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.entity.WorkPart
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.data.remote.GetDayDetailFirst
import com.umc.healthper.ui.login.LoginActivity
import com.umc.healthper.ui.main.view.DetailFirstView
import java.io.InputStream
import com.umc.healthper.util.VarUtil
import com.umc.healthper.util.getAutoLogin
import java.util.*
import kotlin.collections.ArrayList

class SplashActivity : AppCompatActivity(), DetailFirstView  {
    var isToken = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initDb(applicationContext)

        val conn = AuthService()
        conn.dayInfoData = this
        val now = Calendar.getInstance()
        val y = now.get(Calendar.YEAR)
        val m = now.get(Calendar.MONTH) + 1
        val d = now.get(Calendar.DATE)
        conn.dayInfo("$y-$m-$d")

        Handler().postDelayed({
//            val intent = Intent(this, LoginActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//            startActivity(intent)
//            finish()
              splashlogin()
            finish()
        },DURATION)

    }
    companion object {
        private const val DURATION : Long = 1500
    }

    override fun onBackPressed() {
        super.onBackPressed()
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
                }
                else if (tokenInfo != null) {
                    Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    Log.d("ID tokeninfo", tokenInfo.id.toString())
                    isToken = true
                    // api 들어갈 자리
                    val authService = AuthService()
                    authService.login(tokenInfo.id.toString())
                }
            }
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                        Log.d("error", error.toString())
                    }
                }
            }
            else if (token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                UserApiClient.instance.accessTokenInfo{ tokenInfo, error ->
                    if (error != null) {
                        Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
                    }
                    else if (tokenInfo != null) {
                        Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        Log.d("ID tokeninfo no auto", tokenInfo.id.toString())
                        isToken = true
                        // api 들어갈 자리
                        val authService = AuthService()
                        authService.login(tokenInfo.id.toString())
//                        finish()
                    }
                }
            }
        }


        if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
            LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
        }else if (getAutoLogin() && !isToken) {
            LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
        else if (!getAutoLogin() || !isToken){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }
}