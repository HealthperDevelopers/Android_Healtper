package com.umc.healthper.ui.timer

import android.icu.text.Edits
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.umc.healthper.R
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.ActivityTimerBinding
import com.umc.healthper.ui.timer.data.Pack
import com.umc.healthper.ui.timer.data.Work
import com.umc.healthper.util.VarUtil

class TimerActivity : AppCompatActivity() {
    lateinit var binding: ActivityTimerBinding
    lateinit var work : Work
    var pack: ArrayList<Pack> = arrayListOf()
    var setCount : Int = 1
    var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    var weight: Int = 0
    var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // work = Work(0, 0, pack, db.WorkDao().findWorkPartbyWorkName(VarUtil.glob.currentWork))
        weight = db.WorkDao().findWorkWeightbyWorkName(VarUtil.glob.currentWork)
        count = db.WorkDao().findWorkSetbyWorkName(VarUtil.glob.currentWork)

        supportFragmentManager.beginTransaction().add(R.id.main_cl, SettimerFragment()).commit()
    }

    fun changeTimerFragment() {
        val trans = supportFragmentManager.beginTransaction()

        trans.replace(binding.mainCl.id, TimerFragment()).addToBackStack("timerFragment")
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        trans.isAddToBackStackAllowed
        trans.commit()
    }

    fun addPack(){
        pack.add(Pack(setCount, weight, count))
    }

    fun addPack(weight: Int, count: Int){
        pack.add(Pack(setCount, weight, count))
    }

    fun iterate(totalTime: Int, runningTime: Int) {
        work = Work (totalTime, runningTime, pack, VarUtil.glob.currentPart)
        Log.d("work totalTime", work.totalTime.toString())
        Log.d("work runningTime", work.runningTime.toString())
        Log.d("work currentPart", work.part)
        for (tmp in pack) {
            Log.d("pack set", tmp.set.toString())
            Log.d("pack weight", tmp.weight.toString())
            Log.d("pack count", tmp.count.toString())
            Log.d("------------", "done")
        }
    }
}