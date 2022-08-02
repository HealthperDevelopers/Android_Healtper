package com.umc.healthper.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.umc.healthper.R
import com.umc.healthper.databinding.ActivityMainBinding
import com.umc.healthper.ui.chart.view.ChartFragment
import com.umc.healthper.ui.chart.view.PartchartFragment
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
    var FavoritesMypageFragment: FavoritesMypageFragment? = null
    var MusicMypageFragment: MusicMypageFragment? = null

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
                    if (FavoritesMypageFragment != null) supportFragmentManager.popBackStack("favorites", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    if (MusicMypageFragment != null) supportFragmentManager.popBackStack("music", FragmentManager.POP_BACK_STACK_INCLUSIVE)

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
                    if (FavoritesMypageFragment != null) supportFragmentManager.popBackStack("favorites", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    if (MusicMypageFragment != null) supportFragmentManager.popBackStack("music", FragmentManager.POP_BACK_STACK_INCLUSIVE)

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
                    if (FavoritesMypageFragment != null) supportFragmentManager.popBackStack("favorites", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    if (MusicMypageFragment != null) supportFragmentManager.popBackStack("music", FragmentManager.POP_BACK_STACK_INCLUSIVE)

                    true

            }
            }
        }
    }

    fun changeMypageFragment(int : Int){
        val transition = supportFragmentManager.beginTransaction()
        when (int){
            0 -> {
                FavoritesMypageFragment = FavoritesMypageFragment()
                transition.replace(binding.mainFrmFl.id, FavoritesMypageFragment!!)
                transition.addToBackStack("favorites")
            }
            1 -> {
                MusicMypageFragment = MusicMypageFragment()
                transition.replace(binding.mainFrmFl.id, MusicMypageFragment())
                transition.addToBackStack("music")
            }
        }
        transition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transition.isAddToBackStackAllowed
        transition.commit()
    }

    fun changeChartFragment(part : String){
        val transition = supportFragmentManager.beginTransaction()

        // activity2fragment using intent -> impossible, use bundle

        transition.replace(binding.mainFrmFl.id,
            PartchartFragment().apply {
                arguments = Bundle().apply {
                    putString("part", part)
                }
            }
        )
        transition.addToBackStack("part_chart")

        transition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transition.isAddToBackStackAllowed
        transition.commit()
    }

    fun changeMainFragment() {
        val trans = supportFragmentManager.beginTransaction()
    }
}