package com.umc.healthper.ui.tutorial.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.umc.healthper.R
import com.umc.healthper.databinding.FragmentTutorialBinding
import com.umc.healthper.ui.tutorial.adapter.tutorialVPAdapter
import com.umc.healthper.util.VarUtil

class TutorialFragment : Fragment() {
    lateinit var binding : FragmentTutorialBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorialBinding.inflate(inflater, container, false)
        binding.tutorialSkip.setOnClickListener {
            VarUtil.glob.mainActivity.supportFragmentManager.popBackStack(
                "tutorial",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }

        binding.tutorialVp.adapter = tutorialVPAdapter(getTutorialIMG())
        binding.tutorialVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        return binding.root
    }

    private fun getTutorialIMG(): ArrayList<Int> {
        return arrayListOf(
            R.drawable.tutorial_1,
            R.drawable.tutorial_2
        )
    }
}