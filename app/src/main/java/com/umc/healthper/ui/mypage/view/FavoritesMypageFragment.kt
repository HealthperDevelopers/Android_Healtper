package com.umc.Healthper.ui.mypage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.Healthper.databinding.FragmentMypageFavoritesBinding

class FavoritesMypageFragment : Fragment() {

    lateinit var binding : FragmentMypageFavoritesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }
}