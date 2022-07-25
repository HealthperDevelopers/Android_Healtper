package com.umc.Healthper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.umc.Healthper.databinding.ActivityMainBinding

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
            .replace(R.id.main_frm, MypageFragment())
            .commit()
    }

    fun changeMypageFragment(int : Int){
        val transition = supportFragmentManager.beginTransaction()
        when (int){
            0 -> transition.replace(R.id.main_frm, FavoritesMypageFragment())
            1 -> transition.replace(R.id.main_frm, MusicMypageFragment())
        }
        transition.commit()
    }
}