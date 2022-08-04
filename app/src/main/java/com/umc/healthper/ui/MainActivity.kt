package com.umc.healthper.ui

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.umc.healthper.R
import com.umc.healthper.databinding.ActivityMainBinding
import com.umc.healthper.ui.chart.view.ChartFragment
import com.umc.healthper.ui.chart.view.PartchartFragment
import com.umc.healthper.ui.main.view.MainFragment
import com.umc.healthper.ui.main.view.WorkReadyFragment
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
    var PartchartFragment: PartchartFragment? = null
    var workReadyFragment: WorkReadyFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        VarUtil.glob.mainContext = applicationContext
        VarUtil.glob.mainActivity = this

        if (mainFragment == null) {
            mainFragment = MainFragment()
        }

        supportFragmentManager.beginTransaction().add(R.id.main_frm_fl, mainFragment!!).commit()


        setListener(binding)
        initNav()
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
                    checkStack()

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
                    checkStack()

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
                    checkStack()

                    true
                }
            }
        }
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, binding.mainDl, R.string.drawer_open, R.string.drawer_close)
         {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                binding.mainDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }

            override fun onDrawerStateChanged(newState: Int) {
                super.onDrawerStateChanged(newState)
            }
        }

        binding.mainDl.addDrawerListener(toggle)
    }

    private fun checkStack() {
        if (FavoritesMypageFragment != null) supportFragmentManager.popBackStack(
            "favorites",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        if (MusicMypageFragment != null) supportFragmentManager.popBackStack(
            "music",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        if (PartchartFragment != null) supportFragmentManager.popBackStack(
            "part_chart",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        if (workReadyFragment != null) supportFragmentManager.popBackStack(
            "workReady",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
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
        PartchartFragment = PartchartFragment()

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
        if (workReadyFragment == null) {
            workReadyFragment = WorkReadyFragment()
        }

        trans.replace(binding.mainFrmFl.id, workReadyFragment!!).addToBackStack("workReady")
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        trans.isAddToBackStackAllowed
        trans.commit()

    }

    fun initNav() {
        binding.mainDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun openNav() {
        binding.mainDl.openDrawer(Gravity.RIGHT)
        binding.mainDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }
}