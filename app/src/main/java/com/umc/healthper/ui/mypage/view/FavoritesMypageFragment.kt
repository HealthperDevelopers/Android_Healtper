package com.umc.healthper.ui.mypage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexboxLayoutManager
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.FragmentMypageFavoritesBinding
import com.umc.healthper.ui.dialog.AddFavWorkDialog
import com.umc.healthper.ui.mypage.adapter.PartRVAdapter
import com.umc.healthper.ui.mypage.adapter.ShowFavWorkRVAdapter
import com.umc.healthper.util.VarUtil

class FavoritesMypageFragment : Fragment() {

    lateinit var binding : FragmentMypageFavoritesBinding
    lateinit var partAdapter: PartRVAdapter
    val db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    var currentPart = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageFavoritesBinding.inflate(inflater, container, false)

        setListener()

        //초기 dp
        currentPart = db.WorkPartDao().getFirst().workPart
        binding.mypagefavSelectPartTv.text = currentPart
        val tmpFav = db.WorkFavDao().getAllFavWorkByPartId(1)
        val tmpAll = db.WorkDao().findWorkbyPartId(1)
        VarUtil.glob.favWorkList.clear()
                for (i in tmpFav) {
                    for (j in tmpAll) {
                        if (i.workId == j.id) {
                            VarUtil.glob.favWorkList.add(db.WorkDao().findWorkbyId(j.id))
                        }
                    }
                }
        VarUtil.glob.favPageWorkListAdapter = ShowFavWorkRVAdapter()
        binding.mypagefavWorkListRv.adapter = VarUtil.glob.favPageWorkListAdapter


        partAdapter = PartRVAdapter()
        binding.mypagefavPartListRv.layoutManager = FlexboxLayoutManager(VarUtil.glob.mainContext)
        binding.mypagefavPartListRv.adapter = partAdapter

        partAdapter.setListen(object: PartRVAdapter.listener{
            override fun onClick(str: String) {
                binding.mypagefavSelectPartTv.text = str
                currentPart = str
                val partId = db.WorkPartDao().getWorkPartIdbyPartName(str)
                val tmpFav = db.WorkFavDao().getAllFavWorkByPartId(partId)
                VarUtil.glob.favWorkList.clear()
                for (i in 0..tmpFav.size - 1) {
                    for (j in tmpFav) {
                        if (j.order == i) {
                            VarUtil.glob.favWorkList.add(db.WorkDao().findWorkbyId(j.workId))
                        }
                    }
                }
                VarUtil.glob.favPageWorkListAdapter.notifyDataSetChanged()
            }
        })



        return binding.root
    }


    fun setListener() {
        binding.mypagefavAddWorkIv.setOnClickListener {
            val partId = db.WorkPartDao().getWorkPartIdbyPartName(currentPart)
            AddFavWorkDialog(partId).show(childFragmentManager.beginTransaction(), "addFavWorkDialog")

        }
    }




}