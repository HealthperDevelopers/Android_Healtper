package com.umc.healthper.ui.timer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.databinding.ActivityCommentBinding
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.util.VarUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.sql.Array

class CommentActivity : AppCompatActivity() {

    lateinit var binding : ActivityCommentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.todaycommentCalenderBt.setOnClickListener {
            VarUtil.glob.totalData.comment = binding.todaycommentCommentEt.text.toString()

            // HashSet을 통해 중복 제거
            val tmp = HashSet<String>(VarUtil.glob.totalData.sections)
            VarUtil.glob.totalData.sections.add("WHOLE")
            VarUtil.glob.totalData.sections.add("LEG")
//            VarUtil.glob.totalData.sections = ArrayList(tmp)

            Log.d("info / TotalTime", VarUtil.glob.totalData.exerciseInfo.totalExerciseTime.toString())
            Log.d("info / TotalVolume", VarUtil.glob.totalData.exerciseInfo.totalVolume.toString())
            Log.d("info / sections", VarUtil.glob.totalData.sections.toString())

            // total data 서버로 넘기기
            val authService = AuthService()
            CoroutineScope(IO).launch {

                // 무조건 이 안에서는 순차적으로 실행되는가?
                authService.todayRecord(VarUtil.glob.totalData)
//                authService.detailRecord(VarUtil.glob.work, resp)
            }

//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
            VarUtil.glob.setMain = true
            finish()
        }
    }
}