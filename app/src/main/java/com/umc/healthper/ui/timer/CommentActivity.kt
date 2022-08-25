package com.umc.healthper.ui.timer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

    override fun onStart() {
        super.onStart()
        var second = VarUtil.glob.totalData.exerciseInfo.totalExerciseTime
        var minute = second / 60
        var hour = minute / 60
        binding.todaycommentRealTimeTv.text = String.format("%02d : %02d : %02d", hour, minute, second % 60)

        var volume = VarUtil.glob.totalData.exerciseInfo.totalVolume
        binding.todaycommentRealVolumeTv.text = String.format("%d kg", volume)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.todaycommentCalenderBt.setOnClickListener {
            Log.d("todaycommentCommentEt", binding.todaycommentCommentEt.text.toString())

            if (binding.todaycommentCommentEt.text.toString() == "") {
                Toast.makeText(this, "작성해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                VarUtil.glob.totalData.comment = binding.todaycommentCommentEt.text.toString()

                // HashSet을 통해 중복 제거
                val tmp = HashSet<String>(VarUtil.glob.totalData.sections)
                VarUtil.glob.totalData.sections = ArrayList(tmp)

                Log.d(
                    "info / TotalTime",
                    VarUtil.glob.totalData.exerciseInfo.totalExerciseTime.toString()
                )
                Log.d(
                    "info / TotalVolume",
                    VarUtil.glob.totalData.exerciseInfo.totalVolume.toString()
                )
                Log.d("info / sections", VarUtil.glob.totalData.sections.toString())

                // total data 서버로 넘기기
                val authService = AuthService()
                authService.todayRecord(VarUtil.glob.totalData)

                VarUtil.glob.setMain = true
                finish()
            }
        }
    }
}