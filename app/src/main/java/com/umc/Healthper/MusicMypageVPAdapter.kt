package com.umc.Healthper

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MusicMypageVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> WorkmusicMypageFragment()
            else -> WorkmusicMypageFragment()
        }
    }
}