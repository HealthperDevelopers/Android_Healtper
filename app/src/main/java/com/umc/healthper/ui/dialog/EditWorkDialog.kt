package com.umc.healthper.ui.dialog

import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.google.android.flexbox.*
import com.umc.healthper.databinding.DialogEditWorkBinding
import com.umc.healthper.ui.main.adapter.EditWorkRvAdapter
import com.umc.healthper.util.VarUtil

class EditWorkDialog: DialogFragment() {
    lateinit var binding: DialogEditWorkBinding
    var adapterList =  ArrayList<EditWorkRvAdapter>()
    var tempedSelected = ArrayList<String>()
    var tempedUnselected = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogEditWorkBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        adapterList.add(EditWorkRvAdapter(VarUtil.glob.selectedPart))
        adapterList.add(EditWorkRvAdapter(VarUtil.glob.unselectedPart))

        binding.dialogEditWorkNoRv.layoutManager = FlexboxLayoutManager(context)
        binding.dialogEditWorkYesRv.layoutManager = FlexboxLayoutManager(context)
        binding.dialogEditWorkYesRv.adapter = adapterList[0]
        binding.dialogEditWorkNoRv.adapter = adapterList[1]

        tempedSelected = VarUtil.glob.selectedPart
        tempedUnselected = VarUtil.glob.unselectedPart

        setListener()


        return binding.root
    }

    fun setListener() {
        adapterList[0].setonClickListener(object :EditWorkRvAdapter.OnClickListener{
            override fun onClick(pos: Int) {
                val tmp = VarUtil.glob.selectedPart[pos]
                VarUtil.glob.selectedPart.removeAt(pos)
                VarUtil.glob.unselectedPart.add(tmp)
                adapterList[0].notifyDataSetChanged()
                adapterList[1].notifyDataSetChanged()
            }

        }
        )
        adapterList[1].setonClickListener(object :EditWorkRvAdapter.OnClickListener{
            override fun onClick(pos: Int) {
                val tmp =  VarUtil.glob.unselectedPart[pos]
                VarUtil.glob.unselectedPart.removeAt(pos)
                VarUtil.glob.selectedPart.add(tmp)
                adapterList[0].notifyDataSetChanged()
                adapterList[1].notifyDataSetChanged()
            }

        }
        )

        binding.dialogEditWorkCompleteTv.setOnClickListener {
            VarUtil.glob.workReadyAdapter.notifyDataSetChanged()
            dismiss()
        }

        binding.dialogEditWorkCloseIv.setOnClickListener {
            VarUtil.glob.selectedPart = tempedSelected
            VarUtil.glob.unselectedPart = tempedUnselected
            dismiss()
        }

    }

    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes

        params?.width = (VarUtil.glob.size.x * 0.9).toInt()
        params?.height = (VarUtil.glob.size.y * 0.5).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams

    }
}