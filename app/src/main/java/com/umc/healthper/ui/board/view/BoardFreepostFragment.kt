package com.umc.healthper.ui.board.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentBoardFreepostBinding

class BoardFreepostFragment : Fragment() {
    lateinit var binding : FragmentBoardFreepostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardFreepostBinding.inflate(inflater, container, false)

        return binding.root
    }
}