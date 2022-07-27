<<<<<<< HEAD:app/src/main/java/com/umc/healthper/ui/chart/adapter/PartchartVPAdapter.kt
package com.umc.healthper.ui.chart.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.healthper.ui.chart.view.MonthPartchartFragment
import com.umc.healthper.ui.chart.view.WeekPartchartFragment
import com.umc.healthper.ui.chart.view.YearPartchartFragment
=======
package com.umc.Healthper.ui.chart.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.Healthper.ui.chart.view.MonthPartchartFragment
import com.umc.Healthper.ui.chart.view.WeekPartchartFragment
import com.umc.Healthper.ui.chart.view.YearPartchartFragment
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a:app/src/main/java/com/umc/healthper/ui/PartchartVPAdapter.kt

class PartchartVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> WeekPartchartFragment()
            1 -> MonthPartchartFragment()
            else -> YearPartchartFragment()
        }
    }
}