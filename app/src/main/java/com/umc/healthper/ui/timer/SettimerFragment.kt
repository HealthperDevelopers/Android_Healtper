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
        binding.setTimerTotalTimeTv.text = String.format("%02d:%02d:%02d", hour, minute, second % 60)
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

        var second = timerActivity!!.partTime(true)
        var minute = second / 60
        var hour = minute / 60
        binding.setTimerTableSetEt.text = "${timerActivity!!.setCount}세트"
        binding.setTimerWorkTv.text = VarUtil.glob.currentWork
        binding.setTimerPickBt.text = VarUtil.glob.currentPart
        binding.setTimerTotalTimeTv.text = String.format("%02d:%02d:%02d", hour, minute, second % 60)
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
        return binding.root
    }
}