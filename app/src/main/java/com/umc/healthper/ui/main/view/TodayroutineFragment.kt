package com.umc.healthper.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentTodayroutineBinding

class TodayroutineFragment: Fragment() {

    lateinit var binding: FragmentTodayroutineBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodayroutineBinding.inflate(inflater, container, false)

        return binding.root
    }
}