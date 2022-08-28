package com.umc.healthper.ui.board.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.remote.Comments
import com.umc.healthper.databinding.ItemBoardQuestionpostBinding
import com.umc.healthper.databinding.ItemCommentBinding

class CommentRVAdapter(val data: List<Comments>): RecyclerView.Adapter<CommentRVAdapter.NameHolder>() {

    interface onClickListener {
        fun onClick(pos: Int)
    }
    lateinit var onClick: onClickListener

    fun setListener(set: onClickListener) {
        onClick = set
    }

    lateinit var binding: ItemCommentBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NameHolder(binding, data)
    }

    override fun onBindViewHolder(holder: NameHolder, position: Int) {
        holder.bind(position, onClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class NameHolder(val binding: ItemCommentBinding, val data: List<Comments>): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(pos: Int, onClick: onClickListener) {
            /**대댓글 존재 시 어댑터 바인딩*/
            if (data[pos].children.isNotEmpty()) {
                val childRVAdapter = childCommentRVAdapter(data[pos].children)
                binding.itemChildCommentRv.adapter = childRVAdapter

                childRVAdapter.setListener(object: childCommentRVAdapter.onClickListener{
                    override fun onClick(pos: Int) {
                        Log.d("pos", pos.toString())
                    }
                })
            }

            binding.itemCommentNicknameTv.text = data[pos].writer.nickName
            binding.itemCommentTimeTv.text = data[pos].createdAt
            binding.itemCommentContentTv.text = data[pos].content

            binding.root.setOnClickListener {
                onClick.onClick(pos)
            }
        }
    }
}
