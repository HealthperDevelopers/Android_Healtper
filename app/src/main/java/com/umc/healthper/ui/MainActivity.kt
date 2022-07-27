package com.umc.healthper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< HEAD:app/src/main/java/com/umc/healthper/ui/MainActivity.kt
import com.umc.healthper.R
=======
import com.umc.Healthper.databinding.ActivityMainBinding
>>>>>>> af41f3baf952582e40ab0e51cb6fee18c350a706:app/src/main/java/com/umc/Healthper/MainActivity.kt

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

            // 백스택에 저장하는 방법
        }
        transition.commit()
    }
}