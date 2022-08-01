package com.umc.healthper.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.umc.healthper.R
import com.umc.healthper.databinding.ActivityMainBinding
import com.umc.healthper.ui.chart.view.ChartFragment
import com.umc.healthper.ui.main.view.MainFragment
import com.umc.healthper.ui.mypage.view.FavoritesMypageFragment
import com.umc.healthper.ui.mypage.view.MusicMypageFragment
import com.umc.healthper.ui.mypage.view.MypageFragment
import com.umc.healthper.util.VarUtil

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var mainFragment: MainFragment? = null
    var ChartFragment: ChartFragment? = null
    var mypageFragment: MypageFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        VarUtil.glob.mainContext = applicationContext

        if (mainFragment == null) {
            mainFragment = MainFragment()
        }

        supportFragmentManager.beginTransaction().add(R.id.main_frm_fl, mainFragment!!).commit()


        setListener(binding)
    }

    private fun setListener(binding: ActivityMainBinding) {

        binding.mainNavBnv.setOnItemSelectedListener {
            when(it.title) {
                "메인" -> {
                    if (mainFragment == null) {
                        mainFragment = MainFragment()
                        supportFragmentManager.beginTransaction().add(R.id.main_frm_fl, mainFragment!!).commit()
                    }
                    supportFragmentManager.beginTransaction().show(mainFragment!!).commit()
                    // supportFragmentManager.beginTransaction().show(mainFragment!!).commit()
                    if (mypageFragment != null) supportFragmentManager.beginTransaction().hide(mypageFragment!!).commit()
                    if (ChartFragment != null)supportFragmentManager.beginTransaction().hide(ChartFragment!!).commit()

                    true
                }
                "차트" -> {
                    if (ChartFragment == null) {
                        ChartFragment = ChartFragment()
                        supportFragmentManager.beginTransaction().add(R.id.main_frm_fl, ChartFragment!!).commit()
                    }
                    supportFragmentManager.beginTransaction().hide(mainFragment!!).commit()
                    // supportFragmentManager.beginTransaction().show(mainFragment!!).commit()
                    if (mypageFragment != null)supportFragmentManager.beginTransaction().hide(mypageFragment!!).commit()
                    supportFragmentManager.beginTransaction().show(ChartFragment!!).commit()

                    true

                }

//                "게시판" -> {
//                    if (ChartFragment == null) {
//                        ChartFragment = ChartFragment()
//                        supportFragmentManager.beginTransaction().replace(R.id.main_frm_fl, ChartFragment!!).commit()
//                    }
//                    supportFragmentManager.beginTransaction().hide(mainFragment!!).commit()
//                    // supportFragmentManager.beginTransaction().show(mainFragment!!).commit()
//                    supportFragmentManager.beginTransaction().hide(mypageFragment!!).commit()
//                    supportFragmentManager.beginTransaction().hide(ChartFragment!!).commit()
//                    true
//                }
                else -> {
                    if (mypageFragment == null) {
                        mypageFragment = MypageFragment()
                        supportFragmentManager.beginTransaction().add(R.id.main_frm_fl, mypageFragment!!).commit()
                    }
                    if (mainFragment != null) supportFragmentManager.beginTransaction().hide(mainFragment!!).commit()
                // supportFragmentManager.beginTransaction().hide(mainFragment!!).commit()
                supportFragmentManager.beginTransaction().show(mypageFragment!!).commit()
                if (ChartFragment != null) supportFragmentManager.beginTransaction().hide(ChartFragment!!).commit()
                true

            }
            }
        }
    }

    fun changeMypageFragment(int : Int){
        val transition = supportFragmentManager.beginTransaction()
        when (int){
            0 -> transition.replace(R.id.main_frm_fl, FavoritesMypageFragment())
            1 -> transition.replace(R.id.main_frm_fl, MusicMypageFragment())
//            2 -> transition.replace(R.id.main_frm_fl, MainFragment())
            // 백스택에 저장하는 방법
        }
        transition.commit()
    }
}