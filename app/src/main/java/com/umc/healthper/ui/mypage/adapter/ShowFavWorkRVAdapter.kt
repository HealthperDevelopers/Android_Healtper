package com.umc.healthper.ui.mypage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.ItemMypageFavoritesBinding
import com.umc.healthper.util.VarUtil

class ShowFavWorkRVAdapter(val favList: ArrayList<Work>): RecyclerView.Adapter<ShowFavWorkRVAdapter.ListHolder>() {
    lateinit var binding: ItemMypageFavoritesBinding
    val db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        binding = ItemMypageFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListHolder(binding)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
         holder.bind(position, favList)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    class ListHolder(val binding: ItemMypageFavoritesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, data: ArrayList<Work>) {
            Log.d("favtv", binding.itemMypagefavWorkTv.text.toString())
            binding.itemMypagefavWorkTv.text = data[pos].workName + pos.toString()
        }
    }
}