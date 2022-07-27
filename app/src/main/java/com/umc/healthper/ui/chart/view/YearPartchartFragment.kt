package com.umc.Healthper.ui.chart.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.Healthper.databinding.FragmentPartchartYearBinding

class YearPartchartFragment : Fragment(){

    lateinit var binding : FragmentPartchartYearBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPartchartYearBinding.inflate(inflater, container, false)

        return binding.root
    }
}