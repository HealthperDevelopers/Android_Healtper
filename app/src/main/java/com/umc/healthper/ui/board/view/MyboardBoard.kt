package com.umc.healthper.ui.board.view

import android.content.Context
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentBoardMyboardBinding
import com.umc.healthper.ui.MainActivity

class MyboardBoardFragment : Fragment() {
    lateinit var binding: FragmentBoardMyboardBinding
    var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
}