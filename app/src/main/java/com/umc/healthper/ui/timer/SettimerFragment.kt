package com.umc.healthper.ui.timer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentSetTimerBinding

class SettimerFragment : Fragment() {
    lateinit var binding : FragmentSetTimerBinding
    var timerActivity: TimerActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        timerActivity = context as TimerActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetTimerBinding.inflate(inflater, container, false)

        binding.setTimerWorkstartBt.setOnClickListener{
            timerActivity!!.changeTimerFragment()
        }
        return binding.root
    }
}