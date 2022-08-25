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

//        binding.chartBackTv.setOnClickListener{
//            mainActivity!!.changeChartFragment("등")
//        }
//        binding.chartLegTv.setOnClickListener{
//            mainActivity!!.changeChartFragment("하체")
//        }
//        binding.chartHipTv.setOnClickListener{
//            mainActivity!!.changeChartFragment("엉덩이")
//        }
//        binding.chartChestTv.setOnClickListener{
//            mainActivity!!.changeChartFragment("가슴")
//        }
//        binding.chartShoulderTv.setOnClickListener{
//            mainActivity!!.changeChartFragment("어깨")
//        }
//        binding.chartAbsTv.setOnClickListener{
//            mainActivity!!.changeChartFragment("복근")
//        }
//        binding.chartBicepsTv.setOnClickListener{
//            mainActivity!!.changeChartFragment("이두")
//        }
//        binding.chartTricepsTv.setOnClickListener{
//            mainActivity!!.changeChartFragment("삼두")
//        }
//        binding.chartAerobicTv.setOnClickListener{
//            mainActivity!!.changeChartFragment("유산소")
//        }
//        binding.chartFullTv.setOnClickListener{
//            mainActivity!!.changeChartFragment("전신")
//        }
        return binding.root
    }
}