package com.umc.healthper.ui.timer

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.umc.healthper.R
import com.umc.healthper.databinding.FragmentTimerBinding
import com.umc.healthper.util.VarUtil

class TimerFragment : Fragment() {
    lateinit var binding : FragmentTimerBinding
    lateinit var partTimer: PartTimer
    lateinit var totalTimer: TotalTimer
    var minutesEdit : String? = null
    var secondEdit : String? = null
    var restTimer = RestTimer()
    var runningTimer = RunningTimer()

    var isRest: Boolean = false

    var timerActivity: TimerActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        timerActivity = context as TimerActivity
    }

    @SuppressLint("SetTextI18n", "CutPasteId")
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

        setWorkTime()

        binding.timerTableWeightEt.setTextColor(Color.parseColor("#acacac")) // 운동 시간
        binding.timerTableCountEt.setTextColor(Color.parseColor("#acacac"))
        binding.timerTableSetEt.setTextColor(Color.parseColor("#acacac"))
        binding.timerTableWeight.setTextColor(Color.parseColor("#acacac"))
        binding.timerTableCount.setTextColor(Color.parseColor("#acacac"))

        binding.timerTableSetEt.text = "${timerActivity!!.setCount}세트"
        binding.timerWorkTv.text = VarUtil.glob.currentWork
        binding.timerPickBt.text = VarUtil.glob.currentPart
        binding.timerTableWeightEt.setText(String.format("%02d", timerActivity!!.weight))
        binding.timerTableCountEt.setText(String.format("%02d", timerActivity!!.count))

        minutesEdit = LayoutInflater.from(timerActivity)
            .inflate(R.layout.dialog_rest, null).findViewById<EditText>(R.id.rest_minutes_et).getHint().toString()
        secondEdit = LayoutInflater.from(timerActivity)
            .inflate(R.layout.dialog_rest, null).findViewById<EditText>(R.id.rest_minutes_et).getHint().toString()

        binding.timerClickListener.setOnClickListener {
            Log.d("isWorkTime", VarUtil.glob.isWorkTime.toString())
            getWorkTime()
        }

        binding.timerPartPackSet.setOnClickListener {
            if (!isRest) {
                Toast.makeText(timerActivity!!, "운동 중에는 수정 불가합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.timerWorkrestBt.setOnClickListener{
            getRest()
        }
        binding.timerWorknextBt.setOnClickListener{
            getRest()
        }

        binding.timerDoneBt.setOnClickListener{
            VarUtil.glob.totalData.exerciseInfo.totalExerciseTime = totalTimer.second
            timerActivity!!.popTimerFragment()
        }

        binding.timerRestSettingTimeTv.setOnClickListener {

            // Dialog만들기
            val mDialogView = LayoutInflater.from(timerActivity).inflate(R.layout.dialog_rest, null)
            val mBuilder = AlertDialog.Builder(timerActivity!!)
                .setView(mDialogView)

            mDialogView.findViewById<EditText>(R.id.rest_minutes_et).setHint(String.format("%02d", VarUtil.glob.restMinutes / 60))
            mDialogView.findViewById<EditText>(R.id.rest_second_et).setHint(String.format("%02d", VarUtil.glob.restMinutes % 60))

            val  mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.window?.setLayout(800, 600)

            val doneButton = mDialogView.findViewById<TextView>(R.id.rest_done_bt)

            doneButton.setOnClickListener {
                minutesEdit = mDialogView.findViewById<EditText>(R.id.rest_minutes_et).getText().toString()
                secondEdit = mDialogView.findViewById<EditText>(R.id.rest_second_et).getText().toString()

                if (minutesEdit == "")
                    minutesEdit = mDialogView.findViewById<EditText>(R.id.rest_minutes_et).getHint().toString()
                if (secondEdit == "")
                    secondEdit = mDialogView.findViewById<EditText>(R.id.rest_second_et).getHint().toString()

                Log.d("minutes", minutesEdit!!)
                Log.d("second", secondEdit!!)

                if (minutesEdit!!.toInt() >= 0 && minutesEdit!!.toInt() <= 60 && secondEdit!!.toInt() < 60 && secondEdit!!.toInt() >= 0) {
                    if (secondEdit!!.toInt() == 0 && minutesEdit!!.toInt() == 0) {
                        Toast.makeText(timerActivity!!, "None", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(timerActivity!!, "Okay", Toast.LENGTH_SHORT).show()
                        VarUtil.glob.restMinutes = minutesEdit!!.toInt() * 60 + secondEdit!!.toInt()
                        binding.timerRestSettingTimeTv.text = String.format("%02d : %02d", minutesEdit!!.toInt(), secondEdit!!.toInt())
                        mAlertDialog.dismiss()
                    }
                }
                else {
                    Toast.makeText(timerActivity!!, "Out of range", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    private fun setWorkTime() {
        if (!VarUtil.glob.isWorkTime) // 현재 진행시간이 아니라면 = 파트별 시간
        {
            binding.timerRunningRestTv.visibility = View.INVISIBLE
            binding.timerRunningRestTimeTv.visibility = View.INVISIBLE
            binding.timerPartTv.visibility = View.VISIBLE
            binding.timerPartTimeTv.visibility = View.VISIBLE
        }
        else {
            binding.timerRunningRestTv.visibility = View.VISIBLE
            binding.timerRunningRestTimeTv.visibility = View.VISIBLE
            binding.timerPartTv.visibility = View.INVISIBLE
            binding.timerPartTimeTv.visibility = View.INVISIBLE
        }
    }

    private fun getWorkTime() {
        if (VarUtil.glob.isWorkTime) // 현재 진행시간이라면 = 운동별 시간
        {
            VarUtil.glob.isWorkTime = false // change to partTime
            binding.timerRunningRestTv.visibility = View.INVISIBLE
            binding.timerRunningRestTimeTv.visibility = View.INVISIBLE
            binding.timerPartTv.visibility = View.VISIBLE
            binding.timerPartTimeTv.visibility = View.VISIBLE
        }
        else {
            VarUtil.glob.isWorkTime = true // change to WorkTime = 운동별 시간
            binding.timerRunningRestTv.visibility = View.VISIBLE
            binding.timerRunningRestTimeTv.visibility = View.VISIBLE
            binding.timerPartTv.visibility = View.INVISIBLE
            binding.timerPartTimeTv.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timerActivity!!.addWork(runningTimer.second)
        VarUtil.glob.totalData.exerciseInfo.totalExerciseTime = totalTimer.second
        totalTimer.interrupt()
        runningTimer.interrupt()
        restTimer.interrupt()
        partTimer.interrupt()
    }

    private fun getRest() {
        if (isRest) {
            isRest = false
            binding.timerWorkrestBt.visibility = View.INVISIBLE
            binding.timerWorknextBt.visibility = View.VISIBLE
            binding.timerRestSettingTimeTv.visibility = View.INVISIBLE
            binding.timerRestTimeTv.visibility = View.INVISIBLE
            binding.timerRestImg.visibility = View.INVISIBLE
            binding.timerRestTimeRedTv.visibility = View.INVISIBLE
            binding.timerRestTimeYellowTv.visibility = View.INVISIBLE

            //rest timer initialize
            restTimer.second = 0
            restTimer.mills = 0f
            binding.timerRestTimeTv.setTextColor(Color.parseColor("#FF494949"))
            binding.timerRestTimeTv.text = String.format("%02d : %02d", 0, 0)

            // editable = false
            binding.timerTableWeightEt.setText(String.format("%02d", binding.timerTableWeightEt.text.toString().toInt()))
            binding.timerTableCountEt.setText(String.format("%02d", binding.timerTableCountEt.text.toString().toInt()))
            binding.timerTableWeightEt.isEnabled = false
            binding.timerTableCountEt.isEnabled = false
            binding.timerTableWeightEt.setTextColor(Color.parseColor("#acacac")) // 운동 시간
            binding.timerTableCountEt.setTextColor(Color.parseColor("#acacac"))
            binding.timerTableSetEt.setTextColor(Color.parseColor("#acacac"))
            binding.timerTableWeight.setTextColor(Color.parseColor("#acacac"))
            binding.timerTableCount.setTextColor(Color.parseColor("#acacac"))

            timerActivity!!.addPack(binding.timerTableWeightEt.text.toString().toInt(), binding.timerTableCountEt.text.toString().toInt())
        }
        else {
            isRest = true
            timerActivity!!.setCount++
            binding.timerWorkrestBt.visibility = View.VISIBLE
            binding.timerWorknextBt.visibility = View.INVISIBLE
            binding.timerTableSetEt.text = "${timerActivity!!.setCount}세트"
            binding.timerRestSettingTimeTv.visibility = View.VISIBLE
            binding.timerRestTimeTv.visibility = View.VISIBLE
            binding.timerRestImg.visibility = View.VISIBLE

            // editable = false
            binding.timerTableWeightEt.isEnabled = true
            binding.timerTableCountEt.isEnabled = true
            binding.timerRestTimeRedTv.visibility = View.INVISIBLE
            binding.timerRestTimeYellowTv.visibility = View.INVISIBLE
            binding.timerTableWeightEt.setTextColor(Color.parseColor("#616161")) // 쉬는 시간
            binding.timerTableCountEt.setTextColor(Color.parseColor("#616161"))
            binding.timerTableSetEt.setTextColor(Color.parseColor("#616161"))
            binding.timerTableWeight.setTextColor(Color.parseColor("#616161"))
            binding.timerTableCount.setTextColor(Color.parseColor("#616161"))

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
                            binding.timerTotalRestTimeTv.text = String.format("%02d : %02d : %02d", hour, minute, second % 60)
                            Log.d("start timer", binding.timerTotalRestTimeTv.text.toString())
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
            second = timerActivity!!.workTime(VarUtil.glob.currentWork)
            binding.timerRunningRestTimeTv.text = String.format("%02d : %02d : %02d", (second / 60) / 60, second / 60, second % 60)
            try {
                while (true){
                    sleep(50)
                    mills += 50

                    if (mills % 1000 == 0f){
                        second++
                        minute = second / 60
                        hour = minute / 60
                        timerActivity!!.runOnUiThread {
                            binding.timerRunningRestTimeTv.text = String.format("%02d : %02d : %02d", hour, minute, second % 60)
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
        private var mills: Float = 0f

        override fun run() {
            try {
                while (true){
                    if (mills % 1000 == 0f){
                        second++
                        minute = second / 60
                        hour = minute / 60
                        timerActivity!!.runOnUiThread {
                            binding.timerPartTimeTv.text = String.format("%02d : %02d : %02d", hour, minute, second % 60)
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
        var limitTime : Int = 0

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

                            limitTime = minutesEdit!!.toInt() * 60 + secondEdit!!.toInt()

                            if (second <= limitTime) {
                                binding.timerRestTimeTv.setTextColor(Color.parseColor("#494949"))
                                binding.timerRestTimeRedTv.visibility = View.INVISIBLE
                                binding.timerRestTimeYellowTv.visibility = View.INVISIBLE
                            }
                            else if (second <= limitTime + 5){
                                binding.timerRestTimeTv.setTextColor(Color.parseColor("#FEA621"))
                                binding.timerRestTimeYellowTv.visibility = View.VISIBLE
                                binding.timerRestTimeRedTv.visibility = View.INVISIBLE
                            }
                            else{
                                binding.timerRestTimeTv.setTextColor(Color.parseColor("#FF0000"))
                                binding.timerRestTimeRedTv.visibility = View.VISIBLE
                                binding.timerRestTimeYellowTv.visibility = View.INVISIBLE
                            }

                            binding.timerRestTimeTv.text = String.format("%02d : %02d", second / 60, second % 60)
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