package com.umc.healthper.ui.timer

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.umc.healthper.R
import com.umc.healthper.databinding.FragmentTimerBinding
import com.umc.healthper.util.VarUtil

class TimerFragment : Fragment() {
    lateinit var binding : FragmentTimerBinding
    lateinit var partTimer: PartTimer
    lateinit var totalTimer: TotalTimer
    var minutesEdit : String? = null
    var millsEdit : String? = null
    var restTimer = RestTimer()
    var runningTimer = RunningTimer()

    var isRest: Boolean = false
    var isWorkTime: Boolean = true // false -> partTime

    var timerActivity: TimerActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        timerActivity = context as TimerActivity
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
        partTimer = PartTimer()
        totalTimer = TotalTimer()
        totalTimer.start()
        runningTimer.start()
        restTimer.start()
        partTimer.start()

        VarUtil.glob.restMinutes = 60

        // partTimer.second = timerActivity!!.partTime(VarUtil.glob.currentPart)

        binding.timerTableSetEt.text = "${timerActivity!!.setCount}세트"
        binding.timerWorkTv.text = VarUtil.glob.currentWork
        binding.timerPickBt.text = VarUtil.glob.currentPart
        binding.timerTableWeightEt.setText(String.format("%02d", timerActivity!!.weight))
        binding.timerTableCountEt.setText(String.format("%02d", timerActivity!!.count))

        minutesEdit = LayoutInflater.from(timerActivity)
            .inflate(R.layout.dialog_rest, null).findViewById<EditText>(R.id.rest_minutes_et).getText().toString()
        millsEdit = LayoutInflater.from(timerActivity)
            .inflate(R.layout.dialog_rest, null).findViewById<EditText>(R.id.rest_mills_et).getText().toString()

        binding.timerClickListener.setOnClickListener {
            Log.d("isWorkTime", isWorkTime.toString())
            getWorkTime()
        }

        binding.timerWorkrestBt.setOnClickListener{
            getRest()
        }

        binding.timerDoneBt.setOnClickListener{
            timerActivity!!.addWork(totalTimer.second, runningTimer.second)
            timerActivity!!.popTimerFragment()
//            timerActivity!!.onStop()
        }

        binding.timerRestSettingTimeTv.setOnClickListener {

            // Dialog만들기
            val mDialogView = LayoutInflater.from(timerActivity).inflate(R.layout.dialog_rest, null)
            val mBuilder = AlertDialog.Builder(timerActivity!!)
                .setView(mDialogView)

            mDialogView.findViewById<EditText>(R.id.rest_minutes_et).setText(String.format("%02d", VarUtil.glob.restMinutes / 60))
            mDialogView.findViewById<EditText>(R.id.rest_mills_et).setText(String.format("%02d", VarUtil.glob.restMinutes % 60))

            val  mAlertDialog = mBuilder.show()

            val doneButton = mDialogView.findViewById<Button>(R.id.rest_done_bt)
            doneButton.setOnClickListener {
                while (true){
                    minutesEdit = mDialogView.findViewById<EditText>(R.id.rest_minutes_et).getText().toString()
                    millsEdit = mDialogView.findViewById<EditText>(R.id.rest_mills_et).getText().toString()

                    if (minutesEdit!!.toInt() >= 0 && minutesEdit!!.toInt() <= 60 && millsEdit!!.toInt() < 60 && millsEdit!!.toInt() >= 0) {
                        if (millsEdit!!.toInt() == 0 && minutesEdit!!.toInt() == 0) {
                            Toast.makeText(timerActivity!!, "None", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        else {
                            Toast.makeText(timerActivity!!, "Okay", Toast.LENGTH_SHORT).show()
                            break
                        }
                    }
                    else {
                        Toast.makeText(timerActivity!!, "Out of range", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }

                binding.timerRestSettingTimeTv.text = String.format("%02d:%02d", minutesEdit!!.toInt(), millsEdit!!.toInt())
                VarUtil.glob.restMinutes = minutesEdit!!.toInt() * 60 + millsEdit!!.toInt()
                binding.timerRestSettingTimeTv.text = String.format("%02d:%02d", minutesEdit!!.toInt(), millsEdit!!.toInt())
                mAlertDialog.dismiss()
            }
        }

        return binding.root
    }

    private fun getWorkTime() {
        if (isWorkTime) // 현재 진행시간이라면 = 운동별 시간
        {
            isWorkTime = false // change to partTime
            binding.timerRunningRestTv.visibility = View.GONE
            binding.timerRunningRestTimeTv.visibility = View.GONE
            binding.timerPartTv.visibility = View.VISIBLE
            binding.timerPartTimeTv.visibility = View.VISIBLE
        }
        else {
            isWorkTime = true // change to WorkTime = 운동별 시간
            binding.timerRunningRestTv.visibility = View.VISIBLE
            binding.timerRunningRestTimeTv.visibility = View.VISIBLE
            binding.timerPartTv.visibility = View.GONE
            binding.timerPartTimeTv.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timerActivity!!.totalTime = totalTimer.second
        totalTimer.interrupt()
        runningTimer.interrupt()
        restTimer.interrupt()
        partTimer.interrupt()
    }

    private fun getRest() {
        if (isRest) {
            isRest = false
            binding.timerWorkrestBt.text = "쉬는 시간"
//            binding.timerTableTv.setBackgroundResource(R.drawable.table_tint)
//            binding.timerWorkTimeCl.visibility = View.VISIBLE
//            binding.timerRestTimeCl.visibility = View.GONE
            binding.timerRestSettingTimeTv.visibility = View.INVISIBLE
            binding.timerRestTimeTv.visibility = View.INVISIBLE
//            binding.timerRunningTv.visibility = View.VISIBLE
//            binding.timerRunningTimeTv.visibility = View.VISIBLE
            binding.timerRestImg.visibility = View.INVISIBLE

            //rest timer initialize
            restTimer.second = 0
            restTimer.mills = 0f
            binding.timerRestTimeTv.setTextColor(Color.parseColor("#FF494949"))
            binding.timerRestTimeTv.text = String.format("%02d:%02d", 0, 0)

            // editable = false
            binding.timerTableWeightEt.setText(String.format("%02d", binding.timerTableWeightEt.text.toString().toInt()))
            binding.timerTableCountEt.setText(String.format("%02d", binding.timerTableCountEt.text.toString().toInt()))
            binding.timerTableWeightEt.isEnabled = false
            binding.timerTableCountEt.isEnabled = false
        }
        else {
            isRest = true
            timerActivity!!.setCount++
            binding.timerTableSetEt.text = "${timerActivity!!.setCount}세트"
            binding.timerWorkrestBt.text = "다음 세트"
//            binding.timerTableTv.setBackgroundResource(R.drawable.table)
//            binding.timerWorkTimeCl.visibility = View.GONE
//            binding.timerRestTimeCl.visibility = View.VISIBLE
            binding.timerRestSettingTimeTv.visibility = View.VISIBLE
            binding.timerRestTimeTv.visibility = View.VISIBLE
//            binding.timerRunningTv.visibility = View.INVISIBLE
//            binding.timerRunningTimeTv.visibility = View.INVISIBLE
            binding.timerRestImg.visibility = View.VISIBLE

            // editable = false
            binding.timerTableWeightEt.isEnabled = true
            binding.timerTableCountEt.isEnabled = true

            timerActivity!!.addPack(binding.timerTableWeightEt.text.toString().toInt(), binding.timerTableCountEt.text.toString().toInt())
        }
    }

    inner class TotalTimer : Thread(){
        private var hour: Int = 0
        private var minute: Int = 0
        var second: Int = timerActivity!!.partTime(true) - 1
        private var mills: Float = 0f

        override fun run() {
            try {
                while (true){
                    if (mills % 1000 == 0f){
                        second++
                        minute = second / 60
                        hour = minute / 60
                        timerActivity!!.runOnUiThread {
//                            binding.timerTotalWorkTimeTv.text = String.format("%02d:%02d:%02d", hour, minute, second % 60)
                            binding.timerTotalRestTimeTv.text = String.format("%02d:%02d:%02d", hour, minute, second % 60)
//                            Log.d("start timer", binding.timerTotalWorkTimeTv.text.toString())
                        }
                    }

                    sleep(50)
                    mills += 50
                }
            }catch (e: InterruptedException){
                Log.d("TotalTimer Thread", "쓰레드가 죽었습니다. ${e.message}")
            }
        }
    }

    inner class RunningTimer : Thread(){
        private var hour: Int = 0
        private var minute: Int = 0
        var second: Int  = 0
        private var mills: Float = 0f

        override fun run() {
            try {
                while (true){
//                    if (isRest){
//                        continue
//                    }
                    sleep(50)
                    mills += 50

                    if (mills % 1000 == 0f){
                        second++
                        minute = second / 60
                        hour = minute / 60
                        timerActivity!!.runOnUiThread {
//                            binding.timerRunningTimeTv.text = String.format("%02d:%02d:%02d", hour, minute, second % 60)
                            binding.timerRunningRestTimeTv.text = String.format("%02d:%02d:%02d", hour, minute, second % 60)
//                            Log.d("running timer", binding.timerRunningTimeTv.text.toString())
                        }
                    }
                }
            }catch (e: InterruptedException){
                Log.d("RunningTimer Thread", "쓰레드가 죽었습니다. ${e.message}")
            }
        }
    }

    inner class PartTimer : Thread(){
        private var hour: Int = 0
        private var minute: Int = 0
        private var second: Int = timerActivity!!.partTime(VarUtil.glob.currentPart) - 1
//        private var second: Int = 0

        private var mills: Float = 0f

        override fun run() {
            try {
                while (true){
                    if (mills % 1000 == 0f){
                        second++
                        minute = second / 60
                        hour = minute / 60
                        timerActivity!!.runOnUiThread {
                            binding.timerPartTimeTv.text = String.format("%02d:%02d:%02d", hour, minute, second % 60)
                            Log.d("part timer", binding.timerRunningRestTimeTv.text.toString())
                        }
                    }

                    sleep(50)
                    mills += 50

                }
            }catch (e: InterruptedException){
                Log.d("PartTimer Thread", "쓰레드가 죽었습니다. ${e.message}")
            }
        }
    }

    inner class RestTimer : Thread(){
        var second: Int = 0
        var mills: Float = 0f

        override fun run() {
            try {
                while (true){
                    if (!isRest){
                        continue
                    }
                    sleep(50)
                    mills += 50

                    if (mills % 1000 == 0f){
                        // second++
                        timerActivity!!.runOnUiThread {
                            if ((second / 60 >= minutesEdit!!.toInt()) && (second % 60 >= millsEdit!!.toInt()))
                                binding.timerRestTimeTv.setTextColor(Color.parseColor("#FF0000"))
                            else
                                binding.timerRestTimeTv.setTextColor(Color.parseColor("#FF494949"))

                            binding.timerRestTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            Log.d("rest timer", binding.timerRestTimeTv.text.toString())
                        }
                        second++
                    }
                }
            }catch (e: InterruptedException){
                Log.d("RestTimer Thread", "쓰레드가 죽었습니다. ${e.message}")
            }
        }
    }
}