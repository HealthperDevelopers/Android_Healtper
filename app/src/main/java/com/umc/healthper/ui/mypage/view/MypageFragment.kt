package com.umc.healthper.ui.mypage.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kakao.sdk.user.UserApiClient
import com.umc.healthper.databinding.FragmentMypageBinding
import com.umc.healthper.ui.MainActivity

class MypageFragment : Fragment() {

    lateinit var binding : FragmentMypageBinding
    var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        binding.mypageFavoritesIv.setOnClickListener{
            mainActivity!!.changeMypageFragment(0)
        }
//        binding.mypageMusicIv.setOnClickListener{
//            mainActivity!!.changeMypageFragment(1)
//        }

        binding.mypageLogoutBt.setOnClickListener {
//            kakaoLogout()
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e("try logOut", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                }
                else {
                    Log.i("try logOut", "로그아웃 성공. SDK에서 토큰 삭제됨")
                }
            }
        }
        return binding.root
    }

    private fun kakaoLogout(){
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("try logOut", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i("try logOut", "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }
}