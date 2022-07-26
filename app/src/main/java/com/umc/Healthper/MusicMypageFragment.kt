package com.umc.Healthper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.Healthper.databinding.FragmentMypageMusicBinding

class MusicMypageFragment : Fragment() {

    lateinit var binding : FragmentMypageMusicBinding
    private val information = arrayListOf("운동 음악 모음", "휴식 음악 모음")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageMusicBinding.inflate(inflater, container, false)

        val musicMypageAdapter = MusicMypageVPAdapter(this)
        binding.musicContentVp.adapter = musicMypageAdapter
        TabLayoutMediator(binding.musicContentTb, binding.musicContentVp) {
                tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }
}