package com.umc.healthper.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentWorkdetailBinding

class WorkdetailFragment: Fragment() {
    lateinit var binding: FragmentWorkdetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkdetailBinding.inflate(inflater, container, false)

        setListener()

        return binding.root
    }


    fun setListener() {
        binding.workdetailGobackTv.setOnClickListener {

        }
    }



}