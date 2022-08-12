package com.umc.healthper.ui.timer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.healthper.databinding.ActivityCommentBinding
import com.umc.healthper.ui.MainActivity

class CommentActivity : AppCompatActivity() {

    lateinit var binding : ActivityCommentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.todaycommentCalenderBt.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}