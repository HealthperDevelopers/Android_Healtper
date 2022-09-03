package com.umc.healthper.ui.timer

import android.content.Intent
import android.icu.text.Edits
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.umc.healthper.R
import com.umc.healthper.data.entity.Pack
import com.umc.healthper.data.entity.WorkRecord
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.data.remote.Detail
import com.umc.healthper.data.remote.GetDayDetailSecond
import com.umc.healthper.data.remote.SetDayDetailSecond
import com.umc.healthper.databinding.ActivityTimerBinding
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.util.VarUtil

class TimerActivity : AppCompatActivity() {
    lateinit var binding: ActivityTimerBinding
    var pack: ArrayList<Detail> = arrayListOf()
    var setCount : Int = 1
    var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    var weight: Int = 0
    var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weight = db.WorkDao().findWorkWeightbyWorkName(VarUtil.glob.currentWork)
        count = db.WorkDao().findWorkCountbyWorkName(VarUtil.glob.currentWork)

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
        // 중복 데이터 조회해서 pack에 덧붙이기
        var flag = false
        for (workRecord in VarUtil.glob.work){
            if (workRecord.exerciseName == VarUtil.glob.currentWork)
            {
                workRecord.details?.add(Detail(setCount, weight, count))
                VarUtil.glob.totalData.exerciseInfo.totalVolume += weight * count
                flag = true
                break
            }
        }
        if (!flag) {
            pack.add(Detail(setCount, count, weight))
            VarUtil.glob.totalData.exerciseInfo.totalVolume += weight * count
        }
        // 현재 진행한 세트의 무게와 횟수 저장 + 세트수도 저장해야함.
        db.WorkDao().updateWorkWeight(VarUtil.glob.currentWork, weight)
        db.WorkDao().updateWorkCount(VarUtil.glob.currentWork, count)
    }

    fun addPack(weight: Int, count: Int){
        // 중복 데이터 조회해서 pack에 덧붙이기
        var flag = false
        for (workRecord in VarUtil.glob.work){
            if (workRecord.exerciseName == VarUtil.glob.currentWork)
            {
                workRecord.details?.add(Detail(setCount, weight, count))
                VarUtil.glob.totalData.exerciseInfo.totalVolume += weight * count
                flag = true
                break
            }
        }
        if (!flag) {
            pack.add(Detail(setCount, count, weight))
            VarUtil.glob.totalData.exerciseInfo.totalVolume += weight * count
        }
        // 현재 진행한 세트의 무게와 횟수 저장
        db.WorkDao().updateWorkWeight(VarUtil.glob.currentWork, weight)
        db.WorkDao().updateWorkCount(VarUtil.glob.currentWork, count)
    }

    fun addWork(runningTime: Int) {
        var flag = false
        for (workRecord in VarUtil.glob.work){
            if (workRecord.exerciseName == VarUtil.glob.currentWork)
            {
                workRecord.exerciseTime += runningTime
                flag = true
                break
            }
        }
        if (!flag) {
            VarUtil.glob.work.add(SetDayDetailSecond (VarUtil.glob.currentWork, db.WorkPartDao().getWorkPartIdbyPartName(VarUtil.glob.currentPart), runningTime,pack))
            VarUtil.glob.totalData.sections.add(VarUtil.glob.currentPart) // -> comment에서 중복 제거
        }
    }

    /** partName을 넣으면 파트별 운동 시간을 알려주는 함수 */
    fun partTime(part:String): Int {
        var partTime = 0
        for (tmp in VarUtil.glob.work){
            if (db.WorkPartDao().getWorkPartIdbyPartName(part) == tmp.sectionId)
                partTime += tmp.exerciseTime
        }
        return partTime
    }

    /** 총 운동시간을 알려주는 함수 */
    fun partTime(all:Boolean): Int {
        var partTime = 0

        for (tmp in VarUtil.glob.work){
            if (all)
                partTime += tmp.exerciseTime
        }
        return partTime
    }

    /** partID을 넣으면 파트별 볼륨을 알려주는 함수 */
    fun partVolume(partId : Int): Int {
        var partVolume = 0
        for (tmp in VarUtil.glob.work){
            if (tmp.sectionId == partId){
                for (pack in tmp.details!!)
                {
                    partVolume += pack.repeatTime * pack.weight
                }
            }
        }
        return partVolume
    }

    /** 운동 이름을 넣으면 운동별 시간을 알려주는 함수*/
    fun workTime(workName : String): Int {
        var time = 0
        for (tmp in VarUtil.glob.work){
            if (workName == tmp.exerciseName)
                time += tmp.exerciseTime
        }
        return time
    }
}