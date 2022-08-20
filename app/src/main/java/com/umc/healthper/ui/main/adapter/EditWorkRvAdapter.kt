package com.umc.healthper.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.databinding.ItemEditWorkPartBinding

class EditWorkRvAdapter(var data: ArrayList<String>): RecyclerView.Adapter<EditWorkRvAdapter.EditWorkHolder>() {

    interface OnClickListener {
        fun onClick(pos: Int)
    }
    lateinit var listener: OnClickListener

    fun setonClickListener(listen: OnClickListener) {
        listener = listen
    }
    lateinit var binding: ItemEditWorkPartBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditWorkHolder {
        binding = ItemEditWorkPartBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return EditWorkHolder(binding, data)
    }

    override fun onBindViewHolder(holder: EditWorkHolder, position: Int) {
        holder.bind(position, listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class EditWorkHolder(val binding: ItemEditWorkPartBinding, var data: ArrayList<String>): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, listener: OnClickListener) {
            binding.itemEditWorkPartTv.text = data[pos]
            binding.root.setOnClickListener {
                listener.onClick(pos)
            }
        }
    }
}