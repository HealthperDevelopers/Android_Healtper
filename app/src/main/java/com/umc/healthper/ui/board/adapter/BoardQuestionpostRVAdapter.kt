package com.umc.healthper.ui.board.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.remote.Contents
import com.umc.healthper.databinding.ItemBoardFreepostBinding
import com.umc.healthper.databinding.ItemBoardQuestionpostBinding

class BoardQuestionpostRVAdapter(val data: List<Contents>): RecyclerView.Adapter<BoardQuestionpostRVAdapter.NameHolder>() {

    interface onClickListener {
        fun onClick(pos: Int)
    }
    lateinit var onClick: onClickListener

    fun setListener(set: onClickListener) {
        onClick = set
    }

    lateinit var binding: ItemBoardQuestionpostBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameHolder {
        val binding = ItemBoardQuestionpostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NameHolder(binding, data)
    }

    override fun onBindViewHolder(holder: NameHolder, position: Int) {
        holder.bind(position, onClick)
    }

    override fun getItemCount(): Int {
        var size = data.size

        for (tmp in data){
            if (tmp.postType != "QUESTION")
                size--
        }
        Log.d("Qsize", size.toString())
        return size
    }

    class NameHolder(val binding: ItemBoardQuestionpostBinding, val data: List<Contents>): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, onClick: onClickListener) {
            if (data[pos].postType == "QUESTION") {
                binding.itemBoardQuestionpostTitleTv.text = data[pos].title
                binding.itemBoardPostNicknameTv.text = data[pos].writer.nickName
                binding.itemBoardPostTimeTv.text = data[pos].createdAt
                binding.itemBoardQuestionpostTitleCommentnumTv.text = data[pos].commentCount.toString()
            }
            binding.root.setOnClickListener {
                onClick.onClick(data[pos].postId)
            }
        }
    }
}