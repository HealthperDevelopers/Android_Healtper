package com.umc.healthper.ui.chart.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentChartBinding
import com.umc.healthper.ui.MainActivity

class ChartFragment : Fragment() {
    lateinit var binding : FragmentChartBinding
    var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartBinding.inflate(inflater, container, false)

        binding.chartBackTv.setOnClickListener{
            mainActivity!!.changeChartFragment("back")
        }
        binding.chartLegTv.setOnClickListener{
            mainActivity!!.changeChartFragment("leg")
        }
        binding.chartHipTv.setOnClickListener{
            mainActivity!!.changeChartFragment("hip")
        }
        binding.chartChestTv.setOnClickListener{
            mainActivity!!.changeChartFragment("chest")
        }
        binding.chartShoulderTv.setOnClickListener{
            mainActivity!!.changeChartFragment("shoulder")
        }
        binding.chartAbsTv.setOnClickListener{
            mainActivity!!.changeChartFragment("abs")
        }
        binding.chartAerobicTv.setOnClickListener{
            mainActivity!!.changeChartFragment("aerobic")
        }
        binding.chartFullTv.setOnClickListener{
            mainActivity!!.changeChartFragment("full")
        }
        return binding.root
    }
}