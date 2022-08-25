package com.umc.healthper.ui.board.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.healthper.R
import com.umc.healthper.databinding.FragmentBoardBinding
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.ui.board.adapter.BoardVPAdapter

class BoardFragment : Fragment() {
    lateinit var binding: FragmentBoardBinding
    var mainActivity: MainActivity? = null
    private val information = arrayListOf("자유 게시판", "질문 게시판")

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

        val boardVPAdapter = BoardVPAdapter(this)
        binding.boardFreePostVp.adapter = boardVPAdapter
        TabLayoutMediator(binding.boardTb, binding.boardFreePostVp)
        { tab, position ->
            tab.text = information[position]
        }.attach()

        binding.boardMyboardIv.setOnClickListener {
            mainActivity!!.changeBoardFragment(0)
        }

        binding.boardWritePostIv.setOnClickListener {
            mainActivity!!.changeBoardFragment(1)
        }
        return binding.root
    }
}


