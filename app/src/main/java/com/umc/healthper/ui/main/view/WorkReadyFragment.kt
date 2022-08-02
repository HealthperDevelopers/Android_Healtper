package com.umc.healthper.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentWorkreadyBinding
import com.umc.healthper.ui.main.adapter.RoutineReadyListAdapter

class WorkReadyFragment: Fragment() {
    lateinit var binding: FragmentWorkreadyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkreadyBinding.inflate(inflater, container, false)

        var dataList = ArrayList<Int>()

        val routineListAdapter = RoutineReadyListAdapter(dataList)

        binding.routinereadyRoutineListRv.adapter = routineListAdapter
        return binding.root
    }


}