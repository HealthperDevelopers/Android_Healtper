package com.umc.healthper.ui.board.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentBoardQuestionpostBinding

class BoardQuestionpostFragment : Fragment() {
    lateinit var binding : FragmentBoardQuestionpostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardQuestionpostBinding.inflate(inflater, container, false)

        return binding.root
    }
}