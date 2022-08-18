package com.umc.healthper.ui.timer

import android.content.Intent
import android.icu.text.Edits
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.umc.healthper.R
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.ActivityTimerBinding
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.ui.timer.data.Pack
import com.umc.healthper.ui.timer.data.Work
import com.umc.healthper.util.VarUtil

class TimerActivity : AppCompatActivity() {
    lateinit var binding: ActivityTimerBinding
    var pack: ArrayList<Pack> = arrayListOf()
    var setCount : Int = 1
    var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    var weight: Int = 0
    var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    fun popTimerFragment() {
        supportFragmentManager.popBackStack(
            "timerFragment",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        finish()
    }

    fun addPack(){
        pack.add(Pack(setCount, weight, count))
        VarUtil.glob.totalData.exerciseInfo.totalVolume += weight * count
//        Log.d("pack weight", weight.toString())
//        Log.d("pack count", count.toString())
//        Log.d("------------", "done")
    }

    fun addPack(weight: Int, count: Int){
        pack.add(Pack(setCount, weight, count))
        VarUtil.glob.totalData.exerciseInfo.totalVolume += weight * count
//        Log.d("pack weight", weight.toString())
//        Log.d("pack count", count.toString())
//        Log.d("------------", "done")
    }

    fun addWork(runningTime: Int) {
        VarUtil.glob.work.add(Work (runningTime, pack, db.WorkPartDao().getWorkPartId(VarUtil.glob.currentPart), VarUtil.glob.currentWork))
        VarUtil.glob.totalData.sections.add(VarUtil.glob.currentPart) // -> comment에서 중복 제거
        for (tmp in pack) {
            Log.d("pack set", tmp.set.toString())
            Log.d("pack weight", tmp.weight.toString())
            Log.d("pack count", tmp.count.toString())
            Log.d("------------", "done")
        }
    }

    fun partTime(part:String): Int {
        var partTime = 0
        Log.d("partTime func", "func")

        for (tmp in VarUtil.glob.work){
            Log.d("running time", tmp.runningTime.toString())
            Log.d("part", tmp.partId.toString())

            if (db.WorkPartDao().getWorkPartId(part) == tmp.partId)
                partTime += tmp.runningTime
        }
        return partTime
    }

    fun partTime(all:Boolean): Int {
        var partTime = 0
        Log.d("partTime func", "func")

        for (tmp in VarUtil.glob.work){
            Log.d("running time", tmp.runningTime.toString())
            Log.d("part", tmp.partId.toString())

            if (all)
                partTime += tmp.runningTime
        }
        return partTime
    }
}