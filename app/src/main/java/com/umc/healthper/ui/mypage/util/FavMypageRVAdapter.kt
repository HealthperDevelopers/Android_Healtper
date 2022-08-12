package com.umc.healthper.ui.mypage.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.R
import com.umc.healthper.data.entity.FavWork
import com.umc.healthper.databinding.ItemMypageFavoritesBinding

class FavMypageRVAdapter(val data: List<FavWork>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    interface onClickListener {
        fun onClick(pos: Int)
    }
    lateinit var onClick: onClickListener

    fun setListener(set: onClickListener) {
        onClick = set
    }

    lateinit var binding: ItemMypageFavoritesBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMypageFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NameHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NameHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class NameHolder(val binding: ItemMypageFavoritesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.mypagefavWork.text = data[pos].FavWorkPartName
            binding.root.setOnClickListener {
                onClick.onClick(pos)
            }
        }
    }
}