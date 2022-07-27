package com.umc.healthper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.umc.healthper.R
import com.umc.healthper.databinding.ActivityMainBinding
import com.umc.healthper.ui.mypage.view.FavoritesMypageFragment
import com.umc.healthper.ui.mypage.view.MusicMypageFragment
import com.umc.healthper.ui.mypage.view.MypageFragment

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
            0 -> {
                transition.replace(binding.mainFrm.id, FavoritesMypageFragment())
                transition.addToBackStack("favorites")
            }
            1 -> {
                transition.replace(binding.mainFrm.id, MusicMypageFragment())
                transition.addToBackStack("music")
            }
        }
        transition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transition.isAddToBackStackAllowed
        transition.commit()
    }
}