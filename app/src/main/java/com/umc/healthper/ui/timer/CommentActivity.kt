package com.umc.healthper.ui.timer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.databinding.ActivityCommentBinding
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.util.VarUtil

class CommentActivity : AppCompatActivity() {

    lateinit var binding : ActivityCommentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.todaycommentCalenderBt.setOnClickListener {
            VarUtil.glob.totalData.comment = binding.todaycommentCommentEt.text.toString()
            Log.d("info / TotalTime", VarUtil.glob.totalData.exerciseInfo.totalExerciseTime.toString())
            Log.d("info / TotalVolume", VarUtil.glob.totalData.exerciseInfo.totalVolume.toString())

            // VarUtil.glob.totalData.sections & exerciseInfo -> 운동을 저장하면서 처리가능할 것 같음.

            // total data 서버로 넘기기
            val authService = AuthService()
            authService.todayRecord(VarUtil.glob.totalData)
// -> totalData 내용확인용 로그

//            Log.d("\n\ntotalData comment", VarUtil.glob.totalData.comment)
//            for (tmp in VarUtil.glob.totalData.workList){
//                Log.d("work", tmp.work)
//                Log.d("total time", tmp.totalTime.toString())
//                Log.d("running time", tmp.runningTime.toString())
//                Log.d("partId", tmp.partId.toString())
//                for (temp in tmp.pack){
//                    Log.d("pack set", temp.set.toString())
//                    Log.d("pack weight", temp.weight.toString())
//                    Log.d("pack count", temp.count.toString())
//                    Log.d("------------", "done")
//                }
//                Log.d("work done", "_________________")
//            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}