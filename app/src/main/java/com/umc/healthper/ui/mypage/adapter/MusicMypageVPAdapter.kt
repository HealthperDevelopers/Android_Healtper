package com.umc.Healthper.ui.mypage.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.Healthper.ui.mypage.view.WorkmusicMypageFragment

class MusicMypageVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> WorkmusicMypageFragment()
            else -> WorkmusicMypageFragment()
        }
    }
}