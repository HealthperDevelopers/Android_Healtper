package com.umc.healthper.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.databinding.ItemEditWorkPartBinding

class EditWorkRvAdapter(var data: ArrayList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnClickListener {
        fun onClick(pos: Int)
    }
    lateinit var listener: OnClickListener

    fun setonClickListener(listen: OnClickListener) {
        listener = listen
    }
    lateinit var binding: ItemEditWorkPartBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemEditWorkPartBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return EditWorkHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EditWorkHolder).bind(position, holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class EditWorkHolder(val binding: ItemEditWorkPartBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, holdPos:Int) {
            binding.itemEditWorkPartTv.text = data[pos]
            binding.root.setOnClickListener {
                listener.onClick(pos)
            }
        }
    }
}