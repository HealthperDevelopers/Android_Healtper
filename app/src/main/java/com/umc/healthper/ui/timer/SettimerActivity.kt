package com.umc.healthper.ui.timer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.healthper.R
import com.umc.healthper.databinding.ActivitySetTimerBinding

class SettimerActivity : AppCompatActivity() {
    lateinit var binding: ActivitySetTimerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setTimerWorkstartBt.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_layout, TimerFragment())
                .commit()
        }
    }
}