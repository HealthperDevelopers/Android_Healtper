package com.umc.healthper.ui.board.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.healthper.ui.board.view.BoardFreepostFragment
import com.umc.healthper.ui.board.view.BoardQuestionpostFragment

class BoardVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    val fragmentList = listOf(BoardFreepostFragment(), BoardQuestionpostFragment())

    override fun getItemCount(): Int = fragmentList.size
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}