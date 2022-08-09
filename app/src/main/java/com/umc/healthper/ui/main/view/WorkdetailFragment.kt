package com.umc.healthper.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.FragmentWorkdetailBinding
import com.umc.healthper.ui.main.adapter.WorkdetailListRVAdapter
import com.umc.healthper.util.VarUtil

class WorkdetailFragment: Fragment() {
    lateinit var binding: FragmentWorkdetailBinding
    lateinit var currentWork: String
    lateinit var workList: List<Work>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkdetailBinding.inflate(inflater, container, false)
        currentWork = VarUtil.glob.currentWork
        setListener()

        var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
        var partId = db.WorkPartDao().getWorkPartId(currentWork)
        workList = db.WorkDao().findWorkbyId(partId)

        binding.workdetailWorkTitleTv.text = currentWork

        val adapter = WorkdetailListRVAdapter(workList)
        binding.workdetailWorkListRv.adapter = adapter

        adapter.setListener(object: WorkdetailListRVAdapter.onClickListener {
            override fun onClick(pos: Int) {
                VarUtil.glob.currentWork = workList[pos].workName
                VarUtil.glob.mainActivity.goTimer()
            }

        })

        return binding.root
    }


    fun setListener() {
        binding.workdetailGobackTv.setOnClickListener {
            parentFragmentManager.popBackStack(
                "workDetail",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }



}