<<<<<<< HEAD:app/src/main/java/com/umc/healthper/ui/chart/view/YearPartchartFragment.kt
package com.umc.healthper.ui.chart.view
=======
package com.umc.Healthper.ui.chart.view
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a:app/src/main/java/com/umc/healthper/ui/YearPartchartFragment.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentPartchartYearBinding

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