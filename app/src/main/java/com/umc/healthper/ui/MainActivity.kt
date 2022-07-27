package com.umc.Healthper.ui

import android.os.Bundle
<<<<<<< HEAD
import androidx.fragment.app.FragmentTransaction
import com.umc.healthper.R
import com.umc.healthper.databinding.ActivityMainBinding
import com.umc.healthper.ui.mypage.view.FavoritesMypageFragment
import com.umc.healthper.ui.mypage.view.MusicMypageFragment
import com.umc.healthper.ui.mypage.view.MypageFragment
=======
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.umc.Healthper.ui.mypage.view.FavoritesMypageFragment
import com.umc.Healthper.ui.mypage.view.MusicMypageFragment
import com.umc.Healthper.ui.mypage.view.MypageFragment
import com.umc.healthper.databinding.ActivityMainBinding
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a

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