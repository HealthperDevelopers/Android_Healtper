package com.umc.healthper.ui.chart.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.healthper.ui.chart.view.MonthPartchartFragment
import com.umc.healthper.ui.chart.view.WeekPartchartFragment
import com.umc.healthper.ui.chart.view.YearPartchartFragment

class PartchartVPAdapter(fragment: Fragment, var partName : String) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> WeekPartchartFragment(partName)
            1 -> MonthPartchartFragment()
            else -> YearPartchartFragment()
        }
    }
}