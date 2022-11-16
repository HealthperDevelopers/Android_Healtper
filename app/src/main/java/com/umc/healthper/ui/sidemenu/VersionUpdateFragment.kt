package com.umc.healthper.ui.sidemenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.umc.healthper.R
import com.umc.healthper.databinding.FragmentVersionUpdateBinding
import com.umc.healthper.util.VarUtil

class VersionUpdateFragment : Fragment() {
    lateinit var binding : FragmentVersionUpdateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVersionUpdateBinding.inflate(inflater, container, false)
        binding.versionUpdateBackBtn.setOnClickListener {
            VarUtil.glob.mainActivity.supportFragmentManager.popBackStack(
                "versionUpdate",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        return binding.root
    }
}