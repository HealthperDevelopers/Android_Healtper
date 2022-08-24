package com.umc.healthper.ui.main.view

import android.content.res.ColorStateList
import android.graphics.Color
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
    lateinit var currentPart: String
    var workList = ArrayList<Work>()

    override fun onStart() {
        super.onStart()
        Log.d("WorkDetail", "Start")
        val adapter = WorkdetailListRVAdapter(workList)
        binding.workdetailWorkListRv.adapter = adapter

        adapter.setListener(object: WorkdetailListRVAdapter.onClickListener {
            override fun onClick(pos: Int) {
                VarUtil.glob.currentWork = workList[pos].workName
                VarUtil.glob.mainActivity.goTimer()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("WorkDetail", "create")

        binding = FragmentWorkdetailBinding.inflate(inflater, container, false)
        currentPart = VarUtil.glob.currentPart
        setListener()

        var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
        var partId = db.WorkPartDao().getWorkPartIdbyPartName(currentPart)
        val tmpFav = db.WorkFavDao().getAllFavWorkByPartId(partId)
        val tmpAll = db.WorkDao().findWorkbyPartId(partId)
        workList.clear()
        for (i in tmpFav) {
            for (j in tmpAll) {
                if (i.workId == j.id) {
                    workList.add(db.WorkDao().findWorkbyId(j.id))
                }
            }
        }
        binding.workdetailWorkTitleTv.text = currentPart
        binding.workdetailWorkTitleTv.backgroundTintList = ColorStateList.valueOf(Color.parseColor(db.WorkPartDao().getColorbyPartName(currentPart)))

//        val adapter = WorkdetailListRVAdapter(workList)
//        binding.workdetailWorkListRv.adapter = adapter

//        adapter.setListener(object: WorkdetailListRVAdapter.onClickListener {
//            override fun onClick(pos: Int) {
//                VarUtil.glob.currentWork = workList[pos].workName
//                VarUtil.glob.mainActivity.goTimer()
//            }
//
//        })

        return binding.root
    }


    fun setListener() {
        binding.workdetailGobackTv.setOnClickListener {
            VarUtil.glob.mainActivity.changeMainFragment(1)
        }
    }



}