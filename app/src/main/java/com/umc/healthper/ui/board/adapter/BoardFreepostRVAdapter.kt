package com.umc.healthper.ui.board.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.R
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.entity.WorkRecord
import com.umc.healthper.data.remote.Contents
import com.umc.healthper.data.remote.PostsResponse
import com.umc.healthper.databinding.ItemBoardFreepostBinding
import com.umc.healthper.databinding.ItemWorkdetailWorknameBinding
import com.umc.healthper.util.VarUtil

//class BoardFreepostRVAdapter(): RecyclerView.Adapter<BoardFreepostRVAdapter.NameHolder>() {

class BoardFreepostRVAdapter(val data: List<Contents>): RecyclerView.Adapter<BoardFreepostRVAdapter.NameHolder>() {

    interface onClickListener {
        fun onClick(pos: Int)
    }
    lateinit var onClick: onClickListener

    fun setListener(set: onClickListener) {
        onClick = set
    }

    lateinit var binding: ItemBoardFreepostBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameHolder {
        val binding = ItemBoardFreepostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NameHolder(binding, data)
    }

    override fun onBindViewHolder(holder: NameHolder, position: Int) {
        holder.bind(position, onClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class NameHolder(val binding: ItemBoardFreepostBinding, val data: List<Contents>): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, onClick: onClickListener) {
            binding.itemBoardPostTitleTv.text = data[pos].title
            Log.d("post title", data[pos].title)

            binding.root.setOnClickListener {
                onClick.onClick(pos)
            }
        }
    }
}