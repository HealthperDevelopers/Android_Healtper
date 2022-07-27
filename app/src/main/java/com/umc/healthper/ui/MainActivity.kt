package com.umc.Healthper.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.Healthper.ui.mypage.view.FavoritesMypageFragment
import com.umc.Healthper.ui.mypage.view.MusicMypageFragment
import com.umc.Healthper.ui.mypage.view.MypageFragment
import com.umc.healthper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_frm, PartchartFragment())
//            .commit()

        supportFragmentManager.beginTransaction()
            .replace(binding.mainFrm.id, MypageFragment())
            .commit()
    }

    fun changeMypageFragment(int : Int){
        val transition = supportFragmentManager.beginTransaction()
        when (int){
            0 -> transition.replace(binding.mainFrm.id, FavoritesMypageFragment())
            1 -> transition.replace(binding.mainFrm.id, MusicMypageFragment())

            // 백스택에 저장하는 방법
        }
        transition.commit()
    }
}