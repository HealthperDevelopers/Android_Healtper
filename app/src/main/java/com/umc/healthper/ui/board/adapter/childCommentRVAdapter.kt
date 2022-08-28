package com.umc.healthper.ui.board.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.remote.Children
import com.umc.healthper.data.remote.Comments
import com.umc.healthper.databinding.ItemChildCommentBinding
import com.umc.healthper.databinding.ItemCommentBinding

class childCommentRVAdapter(val data: List<Children>): RecyclerView.Adapter<childCommentRVAdapter.NameHolder>() {

    interface onClickListener {
        fun onClick(pos: Int)
    }
    lateinit var onClick: onClickListener

    fun setListener(set: onClickListener) {
        onClick = set
    }

    lateinit var binding: ItemChildCommentBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameHolder {
        val binding = ItemChildCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NameHolder(binding, data)
    }

    override fun onBindViewHolder(holder: NameHolder, position: Int) {
        holder.bind(position, onClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class NameHolder(val binding: ItemChildCommentBinding, val data: List<Children>): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, onClick: onClickListener) {

            binding.itemChildCommentNicknameTv.text = data[pos].writer.nickName
            binding.itemChildCommentTimeTv.text = data[pos].createdAt
            binding.itemChildCommentContentTv.text = data[pos].content

            binding.root.setOnClickListener {
                onClick.onClick(pos)
            }
        }
    }
}
