package com.umc.healthper.ui.mypage.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.R
import com.umc.healthper.data.entity.FavWork
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.FragmentMypageFavoritesBinding
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.ui.mypage.util.FavDialogRVAdapter
import com.umc.healthper.ui.mypage.util.FavMypageRVAdapter
=======
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexboxLayoutManager
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.FragmentMypageFavoritesBinding
import com.umc.healthper.ui.dialog.AddFavWorkDialog
import com.umc.healthper.ui.mypage.adapter.PartRVAdapter
import com.umc.healthper.ui.mypage.adapter.ShowFavWorkRVAdapter
>>>>>>> workFlow
import com.umc.healthper.util.VarUtil

class FavoritesMypageFragment : Fragment() {
    var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    var currentPart: String = "어깨" // default value
    lateinit var workList: List<Work>
    lateinit var binding : FragmentMypageFavoritesBinding
<<<<<<< HEAD
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

=======
    lateinit var partAdapter: PartRVAdapter
    val db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    var currentPart = ""
>>>>>>> workFlow
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageFavoritesBinding.inflate(inflater, container, false)
<<<<<<< HEAD
        setCurrentPart(currentPart)

        binding.mypagefavChestBt.setOnClickListener{ setCurrentPart("가슴") }
        binding.mypagefavBackBt.setOnClickListener{ setCurrentPart("등") }
        binding.mypagefavLegBt.setOnClickListener{ setCurrentPart("하체") }
        binding.mypagefavHipBt.setOnClickListener{ setCurrentPart("엉덩이") }
        binding.mypagefavSholderBt.setOnClickListener{ setCurrentPart("어깨") }
        binding.mypagefavAbsBt.setOnClickListener{ setCurrentPart("복근") }
        binding.mypagefavAerobicBt.setOnClickListener{ setCurrentPart("유산소") }
        binding.mypagefavFullBt.setOnClickListener{ setCurrentPart("전신") }
        binding.mypagefavBicepsBt.setOnClickListener{ setCurrentPart("이두") }
        binding.mypagefavTricepsBt.setOnClickListener{ setCurrentPart("삼두") }

        val favWork = db.FavWorkDao().getAll(currentPart)
        val FavAdapter = FavMypageRVAdapter(favWork)
        binding.mypagefavRv.adapter = FavAdapter

        binding.mypagefavPlusIv.setOnClickListener{
            var partId = db.WorkPartDao().getWorkPartId(currentPart)
            workList = db.WorkDao().findWorkbyId(partId)
            val adapter = FavDialogRVAdapter(workList)
            binding.mypagefavRv.adapter = FavMypageRVAdapter(db.FavWorkDao().getAll(currentPart))

            val mDialogView = LayoutInflater.from(mainActivity).inflate(R.layout.dialog_fav, null)
            val mBuilder = AlertDialog.Builder(mainActivity)
                .setView(mDialogView)

            mDialogView.findViewById<RecyclerView>(R.id.fav_dialog_rv).adapter = adapter

            val  mAlertDialog = mBuilder.show()

            adapter.setListener(object: FavDialogRVAdapter.onClickListener {
                override fun onClick(pos: Int) {
                    Log.d("posint", workList[pos].workName)
                    Log.d("partId", workList[pos].id.toString())
                    db.FavWorkDao().insert( FavWork( 0,  currentPart, 0, workList[pos].id, workList[pos].workName))
                    mAlertDialog.dismiss()
                }
            })
        }
        return binding.root
    }

    @JvmName("setCurrentPart1")
    private fun setCurrentPart(Pick: String) {
        currentPart = Pick
        binding.mypagefavPickBt.text = Pick
        binding.mypagefavRv.adapter = FavMypageRVAdapter(db.FavWorkDao().getAll(currentPart))
//        binding.mypagefavRv.adapter = FavMypageRVAdapter(db.FavWorkDao().getAll(db.WorkPartDao().getWorkPartId(currentPart)))

    }
=======

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




>>>>>>> workFlow
}