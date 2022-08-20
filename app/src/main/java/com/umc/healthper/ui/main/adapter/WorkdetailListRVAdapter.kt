package com.umc.healthper.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.entity.Work
import com.umc.healthper.databinding.ItemWorkdetailWorknameBinding

class WorkdetailListRVAdapter(val data: List<Work>): RecyclerView.Adapter<WorkdetailListRVAdapter.NameHolder>() {

    interface onClickListener {
        fun onClick(pos: Int)
    }
    lateinit var onClick: onClickListener

    fun setListener(set: onClickListener) {
        onClick = set
    }
    lateinit var binding: ItemWorkdetailWorknameBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameHolder {
        val binding = ItemWorkdetailWorknameBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NameHolder(binding, data)
    }

    override fun onBindViewHolder(holder: NameHolder, position: Int) {
        holder.bind(position, onClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class NameHolder(val binding: ItemWorkdetailWorknameBinding, val data: List<Work>): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, onClick: onClickListener) {
            binding.itemWorkdetailWorknameTv.text = data[pos].workName
            binding.root.setOnClickListener {
                onClick.onClick(pos)
            }
        }
    }
}