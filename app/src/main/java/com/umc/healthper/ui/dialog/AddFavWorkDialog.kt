package com.umc.healthper.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.DialogAddFavWorkBinding
import com.umc.healthper.ui.mypage.adapter.AddFavWorkRVAdapter
import com.umc.healthper.util.VarUtil

class AddFavWorkDialog(val partId: Int): DialogFragment() {
    lateinit var binding: DialogAddFavWorkBinding
    val db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    lateinit var nonFavList: MutableList<Work>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddFavWorkBinding.inflate(inflater, container, false)

        val favList = db.WorkFavDao().getAllFavWorkByPartId(partId)
        nonFavList = db.WorkDao().findWorkbyPartId(partId).toMutableList()
        for (i in favList) {
            for (j in 0..nonFavList.size) {
                if (i.workId == nonFavList[j].id) {
                    nonFavList.removeAt(j)
                    break
                }
            }
        }

        val adapter = AddFavWorkRVAdapter(nonFavList, partId)
        binding.dialogAddFavWorkListRv.adapter = adapter
        //팝업에서 아이템 눌렀을때(추가)
        adapter.setListener(object: AddFavWorkRVAdapter.Listener{
            override fun onClick(pos: Int) {

                val nextFav = nonFavList[pos]
                VarUtil.glob.favWorkList.add(nextFav)
//                val favList
//                for (i in 0..favList.size) {
//                    for (j in favList) {
//                        if (j.order == i) {
//                            VarUtil.glob.favWorkList.add(db.WorkDao().findWorkbyId(j.workId))
//                        }
//                    }
//                }
                VarUtil.glob.favPageWorkListAdapter.notifyDataSetChanged()
                dismiss()
            }

        })
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes

        params?.width = (VarUtil.glob.size.x * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams

    }
}