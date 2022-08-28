package com.umc.healthper.ui.board.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.remote.Contents
import com.umc.healthper.databinding.ItemBoardFreepostBinding

class BoardFreepostRVAdapter(val data: List<Contents>): RecyclerView.Adapter<BoardFreepostRVAdapter.NameHolder>() {

    interface onClickListener {
        fun onClick(pos: Int, likeCount:Int, CommentCount : Int)
    }
    lateinit var onClick: onClickListener

    fun setListener(set: onClickListener) {
        onClick = set
    }

    lateinit var binding: ItemBoardFreepostBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameHolder {
        val binding = ItemBoardFreepostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        var FreeData = ArrayList<Contents>()

        for (tmp in data)
        {
            if (tmp.postType == "NORMAL")
                FreeData.add(tmp)
        }
        return NameHolder(binding, FreeData)
    }

    override fun onBindViewHolder(holder: NameHolder, position: Int) {
        holder.bind(position, onClick)
    }

    override fun getItemCount(): Int {
        var size = data.size

        for (tmp in data){
            if (tmp.postType != "NORMAL")
                size--
        }
        Log.d("Freesize", size.toString())
        return size
    }

    class NameHolder(val binding: ItemBoardFreepostBinding, val data: List<Contents>): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, onClick: onClickListener) {
            if (data[pos].postType == "NORMAL") {
                binding.itemBoardPostTitleTv.text = data[pos].title
                binding.itemBoardPostNicknameTv.text = data[pos].writer.nickName
                binding.itemBoardPostTimeTv.text = data[pos].createdAt
                binding.itemBoardPostRecommendTv.text = data[pos].likeCount.toString()
                binding.itemBoardPostCommentTv.text = data[pos].commentCount.toString()
            }
            binding.root.setOnClickListener {
                onClick.onClick(data[pos].postId, data[pos].likeCount, data[pos].commentCount)
            }
        }
    }
}