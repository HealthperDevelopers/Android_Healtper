package com.umc.Healthper.ui.mypage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.healthper.databinding.FragmentMypageMusicBinding
import com.umc.Healthper.ui.mypage.adapter.MusicMypageVPAdapter

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
        binding.mypagemusicContentVp.adapter = musicMypageAdapter
        TabLayoutMediator(binding.mypagemusicContentTb, binding.mypagemusicContentVp) {
                tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }
}