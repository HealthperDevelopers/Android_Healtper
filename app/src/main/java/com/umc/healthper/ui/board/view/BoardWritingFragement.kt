package com.umc.healthper.ui.board.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.umc.healthper.data.entity.Post
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.databinding.FragmentBoardWritingBinding
import com.umc.healthper.util.VarUtil

class BoardWritingFragement : Fragment() {
    lateinit var binding: FragmentBoardWritingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardWritingBinding.inflate(inflater, container, false)

        binding.boardMyboardWritingPostingBtnIv.setOnClickListener{
            val authService = AuthService()
            authService.postPost(Post(postType = "NORMAL", binding.boardWritingTitleEt.text.toString(), binding.boardWritingContentEt.text.toString()))
            VarUtil.glob.mainActivity.supportFragmentManager.popBackStack(
                "boardWrite",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }

        return binding.root
    }

}