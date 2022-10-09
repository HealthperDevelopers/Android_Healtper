package com.umc.healthper.ui.mypage.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.flexbox.FlexboxLayoutManager
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.FragmentMypageFavoritesBinding
import com.umc.healthper.ui.dialog.AddFavWorkDialog
import com.umc.healthper.ui.mypage.adapter.ItemMove
import com.umc.healthper.ui.mypage.adapter.PartRVAdapter
import com.umc.healthper.ui.mypage.adapter.ShowFavWorkRVAdapter
import com.umc.healthper.util.VarUtil


class FavoritesMypageFragment : Fragment() {
    var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    var currentPart: String = "어깨" // default value
    lateinit var workList: List<Work>
    lateinit var binding : FragmentMypageFavoritesBinding
    lateinit var partAdapter: PartRVAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageFavoritesBinding.inflate(inflater, container, false)
        setListener()

        binding.mypagefavUserNameTv.text = VarUtil.glob.Nickname
        binding.mypagefavUserNameTv.setOnClickListener {
            VarUtil.glob.mainActivity.Mypage()
        }

        //초기 dp
        currentPart = db.WorkPartDao().getFirst().workPart
        binding.mypagefavSelectPartTv.backgroundTintList = ColorStateList.valueOf(
            Color.parseColor(db.WorkPartDao().getColorbyPartName(currentPart)))
        binding.mypagefavSelectPartTv.text = currentPart
        var tmpFav = db.WorkFavDao().getAllFavWorkByPartId(0)
        tmpFav = tmpFav.sortedWith(kotlin.Comparator {o1, o2 -> o1.order - o2.order})
        val tmpAll = db.WorkDao().findWorkbyPartId(0)
        VarUtil.glob.favWorkList.clear()
                for (i in tmpFav) {
                    for (j in tmpAll) {
                        if (i.workId == j.id) {
                            VarUtil.glob.favWorkList.add(db.WorkDao().findWorkbyId(j.id))
                        }
                    }
                }


        VarUtil.glob.favPageWorkListAdapter = ShowFavWorkRVAdapter()
        val itemAdapter = ItemMove(VarUtil.glob.favPageWorkListAdapter)
        val touchHelper = ItemTouchHelper(itemAdapter)
        touchHelper.attachToRecyclerView(binding.mypagefavWorkListRv)
        binding.mypagefavWorkListRv.adapter = VarUtil.glob.favPageWorkListAdapter


        partAdapter = PartRVAdapter()
        binding.mypagefavPartListRv.layoutManager = FlexboxLayoutManager(VarUtil.glob.mainContext)
        binding.mypagefavPartListRv.adapter = partAdapter

        val gridLayoutManager = GridLayoutManager(VarUtil.glob.mainContext, 5)
        binding.mypagefavPartListRv.layoutManager = gridLayoutManager


        partAdapter.setListen(object: PartRVAdapter.listener{
            override fun onClick(str: String) {
                binding.mypagefavSelectPartTv.text = str
                binding.mypagefavSelectPartTv.backgroundTintList = ColorStateList.valueOf(
                    Color.parseColor(db.WorkPartDao().getColorbyPartName(str)))

                currentPart = str
                val partId = db.WorkPartDao().getWorkPartIdbyPartName(str)
                val tmpFav = db.WorkFavDao().getAllFavWorkByPartId(partId)
                tmpFav.sortedWith(compareBy { it.order })
                VarUtil.glob.favWorkList.clear()
                for (i in 0..tmpFav.size - 1) {
                    for (j in tmpFav) {
                        if (j.order == i) {
                            VarUtil.glob.favWorkList.add(db.WorkDao().findWorkbyId(j.workId))
                            break
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