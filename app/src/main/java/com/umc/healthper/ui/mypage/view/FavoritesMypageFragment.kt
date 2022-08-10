package com.umc.healthper.ui.mypage.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.R
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.FragmentMypageFavoritesBinding
import com.umc.healthper.databinding.FragmentWorkdetailBinding
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.ui.main.adapter.WorkdetailListRVAdapter
import com.umc.healthper.ui.mypage.util.FavDialogRVAdapter
import com.umc.healthper.ui.mypage.util.FavWorkDialog
import com.umc.healthper.ui.timer.TimerActivity
import com.umc.healthper.util.VarUtil

class FavoritesMypageFragment : Fragment() {
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

        binding.mypagefavChestBt.setOnClickListener{ setCurrentPart("가슴") }
        binding.mypagefavBackBt.setOnClickListener{ setCurrentPart("등") }
        binding.mypagefavLegBt.setOnClickListener{ setCurrentPart("하체") }
        binding.mypagefavHipBt.setOnClickListener{ setCurrentPart("엉덩이") }
        binding.mypagefavSholderBt.setOnClickListener{ setCurrentPart("어깨") }
        binding.mypagefavAbsBt.setOnClickListener{ setCurrentPart("복근") }
        binding.mypagefavAerobicBt.setOnClickListener{ setCurrentPart("유산소") }
        binding.mypagefavFullBt.setOnClickListener{ setCurrentPart("전신") }

//        val adapter = FavDialogRVAdapter(workList)
//        binding.mypagefavRv.adapter = adapter

        binding.mypagefavPlusIv.setOnClickListener{
            var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
            var partId = db.WorkPartDao().getWorkPartId(currentPart)
            workList = db.WorkDao().findWorkbyId(partId)
            val adapter = FavDialogRVAdapter(workList)

            val mDialogView = LayoutInflater.from(mainActivity!!).inflate(R.layout.dialog_fav, null)
            val mBuilder = AlertDialog.Builder(mainActivity!!)
                .setView(mDialogView)

            mDialogView.findViewById<RecyclerView>(R.id.fav_dialog_rv).adapter = adapter

            mBuilder.show()
        }
        return binding.root
    }

    @JvmName("setCurrentPart1")
    private fun setCurrentPart(Pick: String) {
        currentPart = Pick
        binding.mypagefavPickBt.text = Pick
    }

}