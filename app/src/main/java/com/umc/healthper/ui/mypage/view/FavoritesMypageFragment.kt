package com.umc.healthper.ui.mypage.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.umc.healthper.R
import com.umc.healthper.databinding.FragmentMypageFavoritesBinding
import com.umc.healthper.ui.dialog.AddFavWorkDialog
import com.umc.healthper.ui.dialog.EditWorkDialog
import com.umc.healthper.ui.mypage.adapter.PartRVAdapter
import com.umc.healthper.util.VarUtil

class FavoritesMypageFragment : Fragment() {

    lateinit var binding : FragmentMypageFavoritesBinding
    lateinit var partAdapter: PartRVAdapter
    var currentPart = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageFavoritesBinding.inflate(inflater, container, false)

        setListener()

        partAdapter = PartRVAdapter()
        binding.mypagefavPartListRv.layoutManager = FlexboxLayoutManager(VarUtil.glob.mainContext)
        binding.mypagefavPartListRv.adapter = partAdapter

        partAdapter.setListen(object: PartRVAdapter.listener{
            override fun onClick(str: String) {
                binding.mypagefavSelectPartTv.text = str
                currentPart = str
            }
        })

        binding.mypagefavWorkListRv
        return binding.root
    }


    fun setListener() {
        binding.mypagefavAddWorkIv.setOnClickListener {
            AddFavWorkDialog(currentPart).show(childFragmentManager.beginTransaction(), "addFavWorkDialog")

        }
    }




}