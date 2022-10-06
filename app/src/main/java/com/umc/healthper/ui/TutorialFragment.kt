package com.umc.healthper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.umc.healthper.databinding.FragmentTutorialBinding
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
        return binding.root
    }
}