package com.umc.healthper.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.data.remote.GetDayDetailSecond
import com.umc.healthper.databinding.FragmentDetailWorkRecordFirstBinding
import com.umc.healthper.ui.main.adapter.DetailWorkRecordRvAdapter
import com.umc.healthper.util.VarUtil

class DetailWorkRecordFirstFragment: Fragment() {
    lateinit var binding: FragmentDetailWorkRecordFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailWorkRecordFirstBinding.inflate(inflater, container, false)


        val adapter = DetailWorkRecordRvAdapter()
        adapter.setListener(object: DetailWorkRecordRvAdapter.Listener{
            override fun onClick(pos: Int) {
                VarUtil.glob.mainActivity.changeMainFragment(4)
            }

        })
        binding.detailWorkRecordFirstListRv.adapter = adapter
        return binding.root
    }


}