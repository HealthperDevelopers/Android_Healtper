package com.umc.healthper.ui.board

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardMyboardBinding.inflate(inflater, container, false)

        return binding.root
    }
}