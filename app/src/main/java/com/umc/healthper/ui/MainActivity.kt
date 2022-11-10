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
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.umc.healthper.R
import com.umc.healthper.data.entity.ExerciseInfo
import com.umc.healthper.data.entity.TotalData
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.databinding.ActivityMainBinding
import com.umc.healthper.ui.board.MyboardBoardFragment
import com.umc.healthper.ui.board.view.*
import com.umc.healthper.ui.chart.view.ChartFragment
import com.umc.healthper.ui.chart.view.PartchartFragment
import com.umc.healthper.ui.main.view.*
import com.umc.healthper.ui.mypage.view.FavoritesMypageFragment
// import com.umc.healthper.ui.mypage.view.MusicMypageFragment
import com.umc.healthper.ui.mypage.view.MypageFragment
import com.umc.healthper.ui.timer.CommentActivity
import com.umc.healthper.ui.timer.TimerActivity
import com.umc.healthper.ui.tutorial.view.TutorialFragment
import com.umc.healthper.util.VarUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
//    var work : ArrayList<Work> = arrayListOf()
    var tutorialFragment : TutorialFragment? = null
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
    var boardWritingFragement: BoardWritingFragement? = null
    var boardFreepostContentFragment : BoardFreepostContentFragment? = null
    var boardQuestionpostContentFragment : BoardQuestionpostContentFragment? = null

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
        authService.coCalInfo(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1)
        Log.d("calData", (now.get(Calendar.MONTH) + 1).toString() + VarUtil.glob.calData.toString())
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

        if (VarUtil.glob.tutorial)
        {
            if (tutorialFragment == null){
                tutorialFragment = TutorialFragment()
            }
            CoroutineScope(Dispatchers.Main).launch {
                val trans = supportFragmentManager.beginTransaction()
                changeMypageFragment(0)
                trans.replace(R.id.main_dl, tutorialFragment!!).addToBackStack("tutorial")
                Log.d("add2BackStack", "tutorial")
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                trans.isAddToBackStackAllowed
                trans.commit()
            }
            binding.mainNavBnv.selectedItemId = R.id.menu_mypage
            if (mypageFragment == null) {
                mypageFragment = MypageFragment()
                supportFragmentManager.beginTransaction().add(R.id.main_frm_fl, mypageFragment!!).commit()
            }
            if (mainFragment != null) supportFragmentManager.beginTransaction().hide(mainFragment!!).commit()
            if (BoardFragment != null)supportFragmentManager.beginTransaction().hide(BoardFragment!!).commit()
            supportFragmentManager.beginTransaction().show(mypageFragment!!).commit()
            if (ChartFragment != null) supportFragmentManager.beginTransaction().hide(ChartFragment!!).commit()

            VarUtil.glob.tutorial = false
        }

        supportFragmentManager.beginTransaction().add(R.id.main_frm_fl, mainFragment!!).commit()

        setListener(binding)
        initNav()
    }

    private fun setListener(binding: ActivityMainBinding) {
        setSidebarListener()

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
    }

    private fun setSidebarListener() {
        var sideMenu = binding.mainSidMenuNv
        val drawerToggle = setDrawerToggle()

        sideMenu.setNavigationItemSelectedListener{
            val authService = AuthService()
            when (it.itemId) {
                R.id.setting_help -> {
                    viewTutorial()
                    drawerToggle.closeDrawer(sideMenu)
                }
                R.id.setting_ad -> {
                    Toast.makeText(this, "ad", Toast.LENGTH_SHORT).show()
                }
                R.id.setting_signout -> {
                    // 탈퇴하기 기능 구현
                    authService.signout()
                }
                // 세팅 구현
            }
        true
        }
    }

    private fun viewTutorial() {
        val transition = supportFragmentManager.beginTransaction()
        var tutorialFragment = TutorialFragment()
        transition.add(binding.mainDl.id, tutorialFragment)
                .addToBackStack("tutorial")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .isAddToBackStackAllowed
        transition.commit()
    }

    private fun setDrawerToggle() : DrawerLayout {
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(this, binding.mainDl, R.string.drawer_open, R.string.drawer_close)
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
        return binding.mainDl
    }

    fun checkStack() {
        if (boardFreepostContentFragment != null) supportFragmentManager.popBackStack(
            "boardFreeComment",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        if (boardQuestionpostContentFragment != null) supportFragmentManager.popBackStack(
            "boardQuestionContent",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        if (MyboardBoardFragment != null) supportFragmentManager.popBackStack(
            "boardMypage",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        if (boardWritingFragement != null) supportFragmentManager.popBackStack(
            "boardWrite",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        if (FavoritesMypageFragment != null) supportFragmentManager.popBackStack(
            "favorites",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
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
                boardWritingFragement = BoardWritingFragement()
                transition.add(binding.mainFrmFl.id, boardWritingFragement!!)
                transition.addToBackStack("boardWrite")

            }
            2->{
                if (boardFreepostContentFragment == null) boardFreepostContentFragment = BoardFreepostContentFragment()
                transition.add(binding.mainFrmFl.id, boardFreepostContentFragment!!)
                transition.addToBackStack("boardFreeComment")
            }
            3->{
                if (boardQuestionpostContentFragment == null) boardQuestionpostContentFragment = BoardQuestionpostContentFragment()
                transition.add(binding.mainFrmFl.id, boardQuestionpostContentFragment!!)
                transition.addToBackStack("boardQuestionContent")
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
        val intent = Intent(this, CommentActivity::class.java)
        startActivity(intent)
    }

    fun resetWorkData(){
        Log.d("reset", "Work")
        VarUtil.glob.work = arrayListOf()
        VarUtil.glob.totalData = TotalData("", ArrayList(), ExerciseInfo(0, 0))
    }

    fun softkeyboardHide(): InputMethodManager {
        return getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    }

    fun Mypage(){
        binding.mainNavBnv.selectedItemId = R.id.menu_mypage
        if (mypageFragment == null) {
            mypageFragment = MypageFragment()
            supportFragmentManager.beginTransaction().add(R.id.main_frm_fl, mypageFragment!!).commit()
        }
        setListener(binding)
        initNav()
    }
}