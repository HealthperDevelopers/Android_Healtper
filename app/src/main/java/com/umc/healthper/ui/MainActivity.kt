package com.umc.healthper.ui

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.umc.healthper.R
import com.umc.healthper.data.entity.ExerciseInfo
import com.umc.healthper.data.entity.TotalData
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.databinding.ActivityMainBinding
import com.umc.healthper.ui.board.view.BoardFragment
import com.umc.healthper.ui.board.view.MyboardBoardFragment
import com.umc.healthper.ui.board.view.WriteBoardFragment
import com.umc.healthper.ui.chart.view.ChartFragment
import com.umc.healthper.ui.chart.view.PartchartFragment
import com.umc.healthper.ui.main.view.*
import com.umc.healthper.ui.mypage.view.FavoritesMypageFragment
// import com.umc.healthper.ui.mypage.view.MusicMypageFragment
import com.umc.healthper.ui.mypage.view.MypageFragment
import com.umc.healthper.ui.timer.CommentActivity
import com.umc.healthper.ui.timer.TimerActivity
import com.umc.healthper.util.VarUtil
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
//    var work : ArrayList<Work> = arrayListOf()
    var mainFragment: MainFragment? = null
    var ChartFragment: ChartFragment? = null
    var mypageFragment: MypageFragment? = null
    var BoardFragment: BoardFragment? = null
    var FavoritesMypageFragment: FavoritesMypageFragment? = null
    // var MusicMypageFragment: MusicMypageFragment? = null
    var PartchartFragment: PartchartFragment? = null
    var workReadyFragment: WorkReadyFragment? = null
    var workdetailFragment: WorkdetailFragment? = null

    // board page
    var MyboardBoardFragment: MyboardBoardFragment? = null
    var WriteBoardFragment: WriteBoardFragment? = null

    var detailWorkRecordSecondFragment: DetailWorkRecordSecondFragment? = null
    var detailWorkRecordFirstFragment: DetailWorkRecordFirstFragment? = null
    var authService : AuthService = AuthService()
    private var now: Calendar = Calendar.getInstance()

    override fun onDestroy() {
        super.onDestroy()
        Log.d("mainActivity", "destroy")
        ActivityCompat.finishAffinity(this)
    }

    override fun onResume() {
        super.onResume()
        Log.d("mainActivity", "reStart")
        if (VarUtil.glob.setMain) {
            // authService.calenderInfo(2022, 8)
            checkStack()
            VarUtil.glob.setMain = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("mainActivity", "Create")
        VarUtil.glob.mainContext = applicationContext
        VarUtil.glob.mainActivity = this
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        VarUtil.glob.size = size

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
                    if(BoardFragment != null)supportFragmentManager.beginTransaction().hide(BoardFragment!!).commit()
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
                    if(BoardFragment != null)supportFragmentManager.beginTransaction().hide(BoardFragment!!).commit()
                    if (mypageFragment != null)supportFragmentManager.beginTransaction().hide(mypageFragment!!).commit()
                    checkStack()

                    supportFragmentManager.beginTransaction().show(ChartFragment!!).commit()

                    true

                }

                "게시판" -> {
                    if (BoardFragment == null) {
                        BoardFragment = BoardFragment()
                        supportFragmentManager.beginTransaction().add(R.id.main_frm_fl, BoardFragment!!).commit()
                    }

                    if (mainFragment != null) supportFragmentManager.beginTransaction().hide(mainFragment!!).commit()
                    if (mypageFragment != null)supportFragmentManager.beginTransaction().hide(mypageFragment!!).commit()
                    if (ChartFragment != null)supportFragmentManager.beginTransaction().hide(ChartFragment!!).commit()
                    checkStack()

                    supportFragmentManager.beginTransaction().show(BoardFragment!!).commit()

                    true
                }
                else -> {
                    if (mypageFragment == null) {
                        mypageFragment = MypageFragment()
                        supportFragmentManager.beginTransaction().add(R.id.main_frm_fl, mypageFragment!!).commit()
                    }
                    if (mainFragment != null) supportFragmentManager.beginTransaction().hide(mainFragment!!).commit()
                    if (BoardFragment != null)supportFragmentManager.beginTransaction().hide(BoardFragment!!).commit()
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

    fun checkStack() {
        if (FavoritesMypageFragment != null) supportFragmentManager.popBackStack(
            "favorites",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
//        if (MusicMypageFragment != null) supportFragmentManager.popBackStack(
//            "music",
//            FragmentManager.POP_BACK_STACK_INCLUSIVE
//        )
        if (PartchartFragment != null) supportFragmentManager.popBackStack(
            "part_chart",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        if (workReadyFragment != null) supportFragmentManager.popBackStack(
            "workReady",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        if (workdetailFragment != null) supportFragmentManager.popBackStack(
            "workDetail",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        if (detailWorkRecordFirstFragment != null) supportFragmentManager.popBackStack(
            "detailWorkRecordFirst",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        if (detailWorkRecordSecondFragment != null) supportFragmentManager.popBackStack(
            "detailWorkRecordSecond",
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
//            1 -> {
//                MusicMypageFragment = MusicMypageFragment()
//                transition.replace(binding.mainFrmFl.id, MusicMypageFragment())
//                transition.addToBackStack("music")
//            }
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

    fun changeMainFragment(page: Int) {
        val trans = supportFragmentManager.beginTransaction()
        when (page) {
            1-> {
                if (workReadyFragment == null) {
                    workReadyFragment = WorkReadyFragment()
                }

                trans.replace(binding.mainFrmFl.id, workReadyFragment!!).addToBackStack("workReady")
                Log.d("add2BackStack", "workReady")
            }
            4 -> {
                if (detailWorkRecordSecondFragment == null) {
                    detailWorkRecordSecondFragment = DetailWorkRecordSecondFragment()
                }

                trans.replace(binding.mainFrmFl.id, detailWorkRecordSecondFragment!!).addToBackStack("detailWorkRecordSecond")
            }
            2 -> {
                if (workdetailFragment == null) {
                    workdetailFragment = WorkdetailFragment()
                }

                trans.replace(binding.mainFrmFl.id, workdetailFragment!!).addToBackStack("workDetail")
                Log.d("add2BackStack", "workDetail")
            }
            else -> {
                if (detailWorkRecordFirstFragment == null) {
                    detailWorkRecordFirstFragment = DetailWorkRecordFirstFragment()
                }

                trans.replace(binding.mainFrmFl.id, detailWorkRecordFirstFragment!!).addToBackStack("detailWorkRecordFirst")

            }
        }
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        trans.isAddToBackStackAllowed
        trans.commit()

    }


    fun changeBoardFragment(int : Int){
        val transition = supportFragmentManager.beginTransaction()
        when (int){
            0 -> {
                MyboardBoardFragment = MyboardBoardFragment()
                transition.replace(binding.mainFrmFl.id, MyboardBoardFragment!!)
                transition.addToBackStack("boardMypage")
            }
            1->{
                WriteBoardFragment = WriteBoardFragment()
                transition.replace(binding.mainFrmFl.id, WriteBoardFragment!!)
                transition.addToBackStack("boardWrite")

            }
        }
        transition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transition.isAddToBackStackAllowed
        transition.commit()
    }

    fun initNav() {
        binding.mainDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun openNav() {
        binding.mainDl.openDrawer(Gravity.RIGHT)
        binding.mainDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    fun goTimer() {
        val intent = Intent(this, TimerActivity::class.java)
        startActivity(intent)
    }

    fun change2Comment() {
        for (tmp in VarUtil.glob.work){
//            Log.d("total time", tmp.totalTime.toString())
            Log.d("running time", tmp.runningTime.toString())
            Log.d("partId", tmp.partId.toString())
            Log.d("work", tmp.work)
            for (temp in tmp.pack){
                Log.d("pack set", temp.set.toString())
                Log.d("pack weight", temp.weight.toString())
                Log.d("pack count", temp.count.toString())
                Log.d("------------", "done")
            }
            Log.d("work done", "_________________")
        }

        val intent = Intent(this, CommentActivity::class.java)
        startActivity(intent)
    }

    fun resetWorkData(){
        Log.d("reset", "Work")
        VarUtil.glob.work = arrayListOf()
        VarUtil.glob.totalData = TotalData("", ArrayList(), ExerciseInfo(0, 0))

    }
}