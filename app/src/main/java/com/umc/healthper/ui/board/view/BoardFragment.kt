package com.umc.healthper.ui.board.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentBoardBinding
import com.umc.healthper.ui.MainActivity

class BoardFragment : Fragment() {
    lateinit var binding: FragmentBoardBinding
    var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardBinding.inflate(inflater, container, false)

        binding.boardMyboardIv.setOnClickListener {
            mainActivity!!.changeBoardFragment(0)
        }

        binding.boardWritePostIv.setOnClickListener {
            mainActivity!!.changeBoardFragment(1)
        }
        return binding.root
    }
}


