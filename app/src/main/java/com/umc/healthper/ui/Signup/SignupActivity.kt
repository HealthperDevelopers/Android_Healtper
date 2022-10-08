package com.umc.healthper.ui.Signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient
import com.umc.healthper.R
import com.umc.healthper.data.entity.User
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.data.remote.CalendarResponse
import com.umc.healthper.data.remote.GetDayDetailFirst
import com.umc.healthper.databinding.ActivitySignupBinding
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.ui.login.LoginActivity
import com.umc.healthper.ui.login.LoginView
import com.umc.healthper.ui.main.view.DetailFirstView
import com.umc.healthper.util.VarUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class SignupActivity : AppCompatActivity(), SignupView, DetailFirstView, LoginView {

    lateinit var binding : ActivitySignupBinding
    var userId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("카카오로그인", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.i("kakaoLogin", "사용자 정보 요청 성공" + "\n닉네임: ${user.kakaoAccount?.profile?.nickname}")
                binding.signupKakaoNicknameTv.text = user.kakaoAccount?.profile?.nickname
            }
        }

        UserApiClient.instance.accessTokenInfo{ tokenInfo, error ->
            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            }
            else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                userId = tokenInfo.id
            }
        }

        binding.signupDoneTv.setOnClickListener{
            if (binding.signupNicknameEt.text.toString() != null) {
                var authService = AuthService()
                authService.signupData = this
                authService.signup(User(userId, binding.signupNicknameEt.text.toString()))
            }
        }
    }

    override fun onSignupSuccess() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
            else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                Log.d("ID tokeninfo", tokenInfo.id.toString())
                val authService = AuthService()
                authService.loginData = this
                authService.login(tokenInfo!!.id.toString())
                VarUtil.glob.tutorial = true
            }
        }
    }

    override fun onSignupFailure() {
        Toast.makeText(this, "다른 닉네임으로 시도해주세요.", Toast.LENGTH_SHORT).show()
    }

    override fun onDetailFirstGetSuccess(data: ArrayList<GetDayDetailFirst>) {
        VarUtil.glob.detailFirstList = data
    }

    override fun onDetailFirstGetFailure() {
    }

    override fun onLoginSuccess(data: List<CalendarResponse>?) {
        if (!data.isNullOrEmpty()) {
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
            Log.d("onLoginSuccess", "signup")
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

    }
}