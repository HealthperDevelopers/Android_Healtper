package com.umc.healthper.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentWorkreadyBinding
import com.umc.healthper.ui.dialog.EditWorkDialog
import com.umc.healthper.ui.main.adapter.WorkReadyListAdapter
import com.umc.healthper.util.VarUtil

class WorkReadyFragment: Fragment() {
    lateinit var binding: FragmentWorkreadyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkreadyBinding.inflate(inflater, container, false)

        setListener()

        var dataList = ArrayList<Int>()
        dataList.add(1)

        val workListAdapter = WorkReadyListAdapter(dataList)

        workListAdapter.setOnClickListener(object: WorkReadyListAdapter.onClickListener{
            override fun onClick() {
                VarUtil.glob.mainActivity.changeMainFragment(2)
            }

        })
        binding.routinereadyRoutineListRv.adapter = workListAdapter
        return binding.root
    }

    fun setListener() {
        binding.routinereadyAddTv.setOnClickListener {
            EditWorkDialog().show(childFragmentManager.beginTransaction(), "editWorkDialog")
        }
    }


}