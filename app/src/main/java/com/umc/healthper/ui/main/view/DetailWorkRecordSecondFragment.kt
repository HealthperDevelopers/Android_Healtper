package com.umc.healthper.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentDetailWorkRecordSecondBinding

class DetailWorkRecordSecondFragment: Fragment() {
    lateinit var binding: FragmentDetailWorkRecordSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailWorkRecordSecondBinding.inflate(inflater, container, false)

        return binding.root
    }

}