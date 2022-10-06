package com.umc.healthper.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.umc.healthper.databinding.FragmentBoardWritingBinding
import com.umc.healthper.databinding.FragmentPrivacyInfoRulesBinding
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.util.VarUtil

class PrivacyInfoRulesFragment: Fragment() {
    lateinit var binding: FragmentPrivacyInfoRulesBinding
    var mainActivity: MainActivity? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.privacyInfoCancelIv.setOnClickListener {
            VarUtil.glob.mainActivity.supportFragmentManager.popBackStack(
                "boardWrite",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }
}
