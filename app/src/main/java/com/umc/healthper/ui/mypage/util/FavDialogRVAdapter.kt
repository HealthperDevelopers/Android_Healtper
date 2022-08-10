package com.umc.healthper.ui.mypage.util

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.entity.Work
import com.umc.healthper.databinding.ItemFavdialogWorknameBinding

class FavDialogRVAdapter(val data: List<Work>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface onClickListener {
        fun onClick(pos: Int)
    }
     lateinit var onClick: onClickListener

    fun setListener(set: onClickListener) {
        onClick = set
    }

    lateinit var binding: ItemFavdialogWorknameBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFavdialogWorknameBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NameHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NameHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class NameHolder(val binding: ItemFavdialogWorknameBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.itemFavdialogWorknameTv.text = data[pos].workName
            binding.root.setOnClickListener {
                onClick.onClick(pos)
            }
        }
    }
}