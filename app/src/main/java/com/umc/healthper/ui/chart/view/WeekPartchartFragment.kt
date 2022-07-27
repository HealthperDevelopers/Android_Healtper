package com.umc.Healthper.ui.chart.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentPartchartWeekBinding

class WeekPartchartFragment : Fragment(){

    lateinit var binding : FragmentPartchartWeekBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPartchartWeekBinding.inflate(inflater, container, false)

        return binding.root
    }
}