package com.umc.healthper.ui.mypage.util

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.umc.healthper.R
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.DialogFavBinding
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.util.VarUtil

class FavWorkDialog : DialogFragment() {
    lateinit var currentPart: String
    lateinit var workList: List<Work>
    lateinit var binding: DialogFavBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("dialog", "start")
        binding = DialogFavBinding.inflate(inflater, container, false)
        currentPart = VarUtil.glob.currentPart

        var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
        var partId = 1
        workList = db.WorkDao().findWorkbyId(partId)

        val adapter = FavDialogRVAdapter(workList)
        binding.favDialogRv.adapter = adapter

//        val mDialogView = LayoutInflater.from(MainActivity()).inflate(R.layout.dialog_rest, null)
//        val mBuilder = AlertDialog.Builder(MainActivity())
//            .setView(mDialogView)
//
//        mBuilder.show()

        return binding.root
    }
}