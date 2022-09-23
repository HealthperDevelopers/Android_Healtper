package com.umc.healthper.ui.chart.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.healthper.databinding.FragmentPartchartBinding
import com.umc.healthper.ui.board.adapter.BoardVPAdapter
import com.umc.healthper.ui.chart.adapter.PartchartVPAdapter
import com.umc.healthper.util.VarUtil

class PartchartFragment : Fragment() {
    lateinit var binding : FragmentPartchartBinding
    var partName : String = ""

    private val information = arrayListOf("최근 5회", "최근 10회", "전체")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("data : ", arguments?.getString("part").toString())

        binding = FragmentPartchartBinding.inflate(inflater, container, false)
        binding.partchartUserNameTv.text = VarUtil.glob.Nickname

        partName = arguments?.getString("part").toString()
        binding.partchartPartTv.text = partName

        val partchartAdapter = PartchartVPAdapter(this, arguments?.getString("part").toString())
        binding.partchartContentVp.adapter = partchartAdapter
        TabLayoutMediator(binding.partchartContentTb, binding.partchartContentVp) {
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }
}