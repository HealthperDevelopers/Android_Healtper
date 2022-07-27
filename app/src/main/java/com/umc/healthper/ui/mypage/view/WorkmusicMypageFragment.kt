package com.umc.Healthper.ui.mypage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.Healthper.databinding.FragmentMypageWorkmusicBinding

class WorkmusicMypageFragment : Fragment() {

    lateinit var binding : FragmentMypageWorkmusicBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageWorkmusicBinding.inflate(inflater, container, false)
        return binding.root
    }
}