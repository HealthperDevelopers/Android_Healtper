package com.umc.healthper.ui.mypage.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.umc.healthper.util.VarUtil

class FavoritesMypageFragment : Fragment() {
    var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    var currentPart: String = "어깨" // default value
    lateinit var workList: List<Work>
    lateinit var binding : FragmentMypageFavoritesBinding
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageFavoritesBinding.inflate(inflater, container, false)
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
}