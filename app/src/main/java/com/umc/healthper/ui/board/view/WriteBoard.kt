package com.umc.healthper.ui.board.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentBoardBinding
import com.umc.healthper.databinding.FragmentBoardWritingBinding

class WriteBoardFragment : Fragment() {
        lateinit var binding: FragmentBoardWritingBinding

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentBoardWritingBinding.inflate(inflater, container, false)
            return binding.root
        }


    }
