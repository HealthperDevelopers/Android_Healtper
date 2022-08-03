package com.umc.healthper.ui.timer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.umc.healthper.R
import com.umc.healthper.databinding.ActivitySetTimerBinding
import com.umc.healthper.ui.main.view.WorkReadyFragment

class SettimerActivity : AppCompatActivity() {
    lateinit var binding: ActivitySetTimerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.main_cl, SettimerFragment()).commit()
    }

    fun changeTimerFragment() {
        val trans = supportFragmentManager.beginTransaction()

        trans.replace(binding.mainCl.id, TimerFragment()).addToBackStack("timerFragment")
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        trans.isAddToBackStackAllowed
        trans.commit()
    }
}