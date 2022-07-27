package com.umc.healthper.ui.mypage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.Healthper.databinding.FragmentMypageMusicBinding

class MusicMypageFragment : Fragment() {

    lateinit var binding : FragmentMypageMusicBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageMusicBinding.inflate(inflater, container, false)
        return binding.root
    }
}