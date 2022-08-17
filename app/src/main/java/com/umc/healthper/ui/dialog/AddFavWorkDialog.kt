package com.umc.healthper.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.umc.healthper.databinding.DialogAddFavWorkBinding
import com.umc.healthper.ui.mypage.adapter.AddFavWorkRVAdapter
import com.umc.healthper.util.VarUtil

class AddFavWorkDialog(val part: String): DialogFragment() {
    lateinit var binding: DialogAddFavWorkBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddFavWorkBinding.inflate(inflater, container, false)

        val adapter = AddFavWorkRVAdapter(part)
        binding.dialogAddFavWorkListRv.adapter = adapter
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes

        params?.width = (VarUtil.glob.size.x * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams

    }
}