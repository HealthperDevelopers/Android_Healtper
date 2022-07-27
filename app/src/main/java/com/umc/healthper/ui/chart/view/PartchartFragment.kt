package com.umc.Healthper.ui.chart.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.Healthper.databinding.FragmentPartchartBinding
import com.umc.Healthper.ui.chart.adapter.PartchartVPAdapter

class PartchartFragment : Fragment() {
    lateinit var binding : FragmentPartchartBinding

    private val information = arrayListOf("최근 일주일", "최근 한달", "최근 1년")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPartchartBinding.inflate(inflater, container, false)

        val partchartAdapter = PartchartVPAdapter(this)
        binding.partchartContentVp.adapter = partchartAdapter
        TabLayoutMediator(binding.partchartContentTb, binding.partchartContentVp) {
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }
}