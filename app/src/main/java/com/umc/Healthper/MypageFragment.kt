package com.umc.Healthper

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.Healthper.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {

    lateinit var binding : FragmentMypageBinding
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
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        binding.favoritesIv.setOnClickListener{
            mainActivity!!.changeMypageFragment(0)
        }
        binding.musicIv.setOnClickListener{
            mainActivity!!.changeMypageFragment(1)
        }
        return binding.root
    }
}