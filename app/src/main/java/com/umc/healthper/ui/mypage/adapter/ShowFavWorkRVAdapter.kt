package com.umc.healthper.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.ItemMypageFavoritesBinding
import com.umc.healthper.util.VarUtil

class ShowFavWorkRVAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var binding: ItemMypageFavoritesBinding
    val db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemMypageFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ListHolder(binding: ItemMypageFavoritesBinding): RecyclerView.ViewHolder(binding.root) {

    }
}