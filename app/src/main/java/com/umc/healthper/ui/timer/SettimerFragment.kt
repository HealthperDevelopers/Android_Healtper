package com.umc.healthper.ui.timer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentSetTimerBinding
import com.umc.healthper.util.VarUtil

class SettimerFragment : Fragment() {
    lateinit var binding : FragmentSetTimerBinding
    var timerActivity: TimerActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        timerActivity = context as TimerActivity
    }

    override fun onPause() {
        super.onPause()
        Log.d("pause", "fragment")
        timerActivity!!.weight = binding.setTimerTableWeightEt.text.toString().toInt()
        timerActivity!!.count = binding.setTimerTableCountEt.text.toString().toInt()
        timerActivity!!.addPack()
    }

    override fun onResume() {
        super.onResume()
        var second = timerActivity!!.partTime(true)
        var minute = second / 60
        var hour = minute / 60
        binding.setTimerTotalTimeTv.text = String.format("%02d : %02d : %02d", hour, minute, second % 60)
        binding.setTimerTableWeightEt.setText(String.format("%02d", timerActivity!!.weight))
        binding.setTimerTableCountEt.setText(String.format("%02d", timerActivity!!.count))
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetTimerBinding.inflate(inflater, container, false)

        setWorkTime()

        var second = timerActivity!!.partTime(true)
        var minute = second / 60
        var hour = minute / 60
        binding.setTimerTableSetEt.text = "${timerActivity!!.setCount}세트"
        binding.setTimerWorkTv.text = VarUtil.glob.currentWork
        binding.setTimerPickBt.text = VarUtil.glob.currentPart
        binding.setTimerTotalTimeTv.text = String.format("%02d : %02d : %02d", hour, minute, second % 60)
        binding.setTimerTableWeightEt.setText(String.format("%02d", timerActivity!!.weight))
        binding.setTimerTableCountEt.setText(String.format("%02d", timerActivity!!.count))
        timerActivity!!.weight = binding.setTimerTableWeightEt.text.toString().toInt()
        timerActivity!!.count = binding.setTimerTableCountEt.text.toString().toInt()

        binding.setTimerStartBt.setOnClickListener{
            timerActivity!!.changeTimerFragment()
        }

        binding.setTimerBackBt.setOnClickListener{
            timerActivity!!.finish()
        }

        binding.setTimerClickListener.setOnClickListener {
            Log.d("isWorkTime", VarUtil.glob.isWorkTime.toString())
            getWorkTime()
        }
        return binding.root
    }

    private fun setWorkTime() {
        if (!VarUtil.glob.isWorkTime) // 현재 진행시간이 아니라면 = 파트별 시간
        {
            binding.setTimerRunningRestTv.visibility = View.INVISIBLE
            binding.setTimerRunningRestTimeTv.visibility = View.INVISIBLE
            binding.setTimerPartTv.visibility = View.VISIBLE
            binding.setTimerPartTimeTv.visibility = View.VISIBLE
        }
        else {
            binding.setTimerRunningRestTv.visibility = View.VISIBLE
            binding.setTimerRunningRestTimeTv.visibility = View.VISIBLE
            binding.setTimerPartTv.visibility = View.INVISIBLE
            binding.setTimerPartTimeTv.visibility = View.INVISIBLE
        }
    }

    private fun getWorkTime() {
        if (VarUtil.glob.isWorkTime) // 현재 진행시간이라면 = 운동별 시간
        {
            VarUtil.glob.isWorkTime = false // change to partTime
            binding.setTimerRunningRestTv.visibility = View.INVISIBLE
            binding.setTimerRunningRestTimeTv.visibility = View.INVISIBLE
            binding.setTimerPartTv.visibility = View.VISIBLE
            binding.setTimerPartTimeTv.visibility = View.VISIBLE
        }
        else {
            VarUtil.glob.isWorkTime = true // change to WorkTime = 운동별 시간
            binding.setTimerRunningRestTv.visibility = View.VISIBLE
            binding.setTimerRunningRestTimeTv.visibility = View.VISIBLE
            binding.setTimerPartTv.visibility = View.INVISIBLE
            binding.setTimerPartTimeTv.visibility = View.INVISIBLE
        }
    }
}