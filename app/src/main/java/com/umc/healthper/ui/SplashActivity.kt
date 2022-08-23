package com.umc.healthper.ui

import android.annotation.SuppressLint
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
import com.umc.healthper.ui.login.LoginActivity
import java.io.InputStream
import com.umc.healthper.util.VarUtil
import com.umc.healthper.util.getAutoLogin
import com.umc.healthper.util.saveAutoLogin

class SplashActivity : AppCompatActivity() {
//    var autoLogin = getAutoLogin()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
//            val intent = Intent(this, LoginActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//            startActivity(intent)
//            finish()
              splashlogin()
//            finish()
        },DURATION)
        initDb(applicationContext)

    }
    companion object {
        private const val DURATION : Long = 1500
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    fun initDb(context: Context) {
        val db = LocalDB.getInstance(context)!!
        if (db.WorkDao().getFirst() == null) {
            val manager: AssetManager = context.assets
            val input: InputStream = manager.open("workList.txt")

            var part = ""
            var partId = 0
            var next = false
            input.bufferedReader().readLines().forEach {
                val work = it
                if (next) {
                    part = work
                    var data = WorkPart (
                        0, part
                    )
                    db.WorkPartDao().insert(data)
                    partId = db.WorkPartDao().getWorkPartIdbyPartName(part)
                    next = false
                }
                else if (work == "-") next = true
                else if (!next) {
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

        for (i in db.WorkPartDao().getAllWork()) {
            VarUtil.glob.unselectedPart.add(i)
        }
    }

    fun splashlogin(){
        Log.d("autoLogin", getAutoLogin().toString())
        if (getAutoLogin()) {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                    finish()
                }
                else if (tokenInfo != null) {
                    Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    Log.d("ID tokeninfo", tokenInfo.id.toString())
                    // api 들어갈 자리
                    val authService = AuthService()
                    authService.login(tokenInfo.id.toString())
//                    finish()
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
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                        finish()
                    }
                }
            }
            else if (token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                UserApiClient.instance.accessTokenInfo{ tokenInfo, error ->
                    if (error != null) {
                        Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                        finish()
                    }
                    else if (tokenInfo != null) {
                        Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        Log.d("ID tokeninfo no auto", tokenInfo.id.toString())
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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//            finish()
        }else if (getAutoLogin()) {
            LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
        else if (!getAutoLogin()){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//            finish()
        }
    }
}