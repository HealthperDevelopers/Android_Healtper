<<<<<<< HEAD:app/src/main/java/com/umc/healthper/ui/chart/view/PartchartFragment.kt
package com.umc.healthper.ui.chart.view
=======
package com.umc.Healthper.ui.chart.view
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a:app/src/main/java/com/umc/healthper/ui/PartchartFragment.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.healthper.databinding.FragmentPartchartBinding
<<<<<<< HEAD:app/src/main/java/com/umc/healthper/ui/chart/view/PartchartFragment.kt
import com.umc.healthper.ui.chart.adapter.PartchartVPAdapter
=======
import com.umc.Healthper.ui.chart.adapter.PartchartVPAdapter
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a:app/src/main/java/com/umc/healthper/ui/PartchartFragment.kt

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