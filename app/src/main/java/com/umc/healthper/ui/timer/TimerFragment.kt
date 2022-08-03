package com.umc.healthper.ui.timer

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {
    lateinit var binding : FragmentTimerBinding
    var isRest: Boolean = false

    var settimerActivity: SettimerActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        settimerActivity = context as SettimerActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
        TotalTimer().start()
        RunningTimer().start()

        binding.timerWorkrestBt.setOnClickListener{
            getRest()
        }

        return binding.root
    }

    private fun getRest() {
        if (isRest) {
            isRest = false
            binding.timerWorkTimeCl.visibility = View.VISIBLE
            binding.timerRestTimeCl.visibility = View.GONE
        }
        else {
            isRest = true
            binding.timerWorkTimeCl.visibility = View.GONE
            binding.timerRestTimeCl.visibility = View.VISIBLE
        }
    }

    inner class TotalTimer : Thread(){
        private var hour: Int = 0
        private var minute: Int = 0
        private var second: Int = 0
        private var mills: Float = 0f

        override fun run() {
            while (true){
                sleep(50)
                mills += 50

                if (mills % 1000 == 0f){
                    second++
                    minute = second / 60
                    hour = minute / 60
                    settimerActivity!!.runOnUiThread {
                        binding.timerTotalWorkTimeTv.text = String.format("%02d:%02d:%02d", hour, minute, second % 60)
                        binding.timerTotalRestTimeTv.text = String.format("%02d:%02d:%02d", hour, minute, second % 60)
                        Log.d("start timer", binding.timerTotalWorkTimeTv.text.toString())
                    }
                }
            }
        }
    }

    inner class RunningTimer : Thread(){
        private var hour: Int = 0
        private var minute: Int = 0
        private var second: Int = 0
        private var mills: Float = 0f

        override fun run() {
            while (true){
                if (isRest){
                    continue
                }
                sleep(50)
                mills += 50

                if (mills % 1000 == 0f){
                    second++
                    minute = second / 60
                    hour = minute / 60
                    settimerActivity!!.runOnUiThread {
                        binding.timerRunningTimeTv.text = String.format("%02d:%02d:%02d", hour, minute, second % 60)
                        binding.timerRunningRestTimeTv.text = String.format("%02d:%02d:%02d", hour, minute, second % 60)
                        Log.d("running timer", binding.timerRunningTimeTv.text.toString())
                    }
                }
            }
        }
    }

    class RestTimer : Thread(){

    }
}