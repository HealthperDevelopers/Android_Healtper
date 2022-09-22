package com.umc.healthper.ui.Signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient
import com.umc.healthper.R
import com.umc.healthper.data.entity.User
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.data.remote.CalendarResponse
import com.umc.healthper.databinding.ActivitySignupBinding
import com.umc.healthper.util.VarUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity(), SignupView {

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
        // 다시 로그인 시도..
        // 로그인 시 멤버 정보 조회해서 닉네임 활성화하기
    }

    override fun onSignupFailure() {
        Toast.makeText(this, "다른 닉네임으로 시도해주세요.", Toast.LENGTH_SHORT).show()
    }
}