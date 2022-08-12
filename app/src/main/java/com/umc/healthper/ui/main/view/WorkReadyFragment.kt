package com.umc.healthper.ui.main.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentWorkreadyBinding
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.ui.dialog.EditWorkDialog
import com.umc.healthper.ui.main.adapter.WorkReadyListAdapter
import com.umc.healthper.ui.timer.TimerActivity
import com.umc.healthper.util.VarUtil

class WorkReadyFragment: Fragment() {
    lateinit var binding: FragmentWorkreadyBinding
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
        binding = FragmentWorkreadyBinding.inflate(inflater, container, false)

        binding.routinereadyCompleteTv.setOnClickListener{
            mainActivity!!.change2Comment()
        }

        val workListAdapter = WorkReadyListAdapter()
        VarUtil.glob.workReadyAdapter = workListAdapter

        workListAdapter.setOnClickListener(object: WorkReadyListAdapter.onClickListener{
            override fun onClick(pos: Int) {
                VarUtil.glob.currentPart = VarUtil.glob.selectedPart[pos]
                VarUtil.glob.mainActivity.changeMainFragment(2)
            }

        })
        binding.routinereadyRoutineListRv.adapter = workListAdapter
        setListener()
        return binding.root
    }

    fun setListener() {
        binding.routinereadyAddTv.setOnClickListener {
            EditWorkDialog().show(childFragmentManager.beginTransaction(), "editWorkDialog")
        }
    }

}