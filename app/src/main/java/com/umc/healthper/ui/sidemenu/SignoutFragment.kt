package com.umc.healthper.ui.sidemenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.umc.healthper.R
import com.umc.healthper.databinding.FragmentSignoutBinding
import com.umc.healthper.util.VarUtil

class SignoutFragment : Fragment() {
    lateinit var binding : FragmentSignoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignoutBinding.inflate(inflater, container, false)
        clickBack()

        return binding.root
    }

    private fun clickBack() {
        binding.signoutBackBtn.setOnClickListener {
            VarUtil.glob.mainActivity.supportFragmentManager.popBackStack(
                "signout",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }
}