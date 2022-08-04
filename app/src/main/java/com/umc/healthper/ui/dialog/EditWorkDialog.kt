package com.umc.healthper.ui.dialog

import android.graphics.Point
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
    var ls = mutableListOf<String>("asdf", "ASDh", "ASDG", "ASGHSDGR", "ASDSDG ", "ASDgs", "ASDfdf", "ASDh", "ASDG", "ASGHSDGR")
    var ls2 = mutableListOf<String>("ASDSDG ", "ASDgs", "ASDfdf", "ASDh", "ASDG", "ASGHSDGR", "ASDSDG ", "ASDgs", "ASDfdf")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogEditWorkBinding.inflate(inflater, container, false)


        adapterList.add(EditWorkRvAdapter(ls))
        adapterList.add(EditWorkRvAdapter(ls2))


        binding.dialogEditWorkNoRv.layoutManager = FlexboxLayoutManager(context)
        binding.dialogEditWorkYesRv.layoutManager = FlexboxLayoutManager(context)
        binding.dialogEditWorkYesRv.adapter = adapterList[0]
        binding.dialogEditWorkNoRv.adapter = adapterList[1]

        setListener()


        return binding.root
    }

    fun setListener() {
        adapterList[0].setonClickListener(object :EditWorkRvAdapter.OnClickListener{
            override fun onClick(pos: Int) {
                val tmp = ls[pos]
                Log.d("tmp", tmp + pos.toString())
                ls.removeAt(pos)
                ls2.add(tmp)
                adapterList[0].notifyItemRemoved(pos)
                adapterList[0].notifyItemRangeChanged(pos, 1)
                adapterList[1].notifyItemInserted(ls2.size)
            }

        }
        )

    }

    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes

        params?.width = (VarUtil.glob.size.x * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams

    }
}