package com.umc.healthper.ui.main.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.entity.Work
import com.umc.healthper.databinding.ItemWorkdetailWorknameBinding
import com.umc.healthper.util.VarUtil

class WorkdetailListRVAdapter(val data: ArrayList<Work>): RecyclerView.Adapter<WorkdetailListRVAdapter.NameHolder>() {

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
            try {
                for (work in VarUtil.glob.work){
                    if (work.work == data[pos].workName) {
                        Log.d("workDetailRVAdapter", "color change")
                        binding.itemWorkdetailWorknameTv.setBackgroundColor(Color.parseColor("#FF0000"))
                    }
                }
            }catch(e : Exception){
                Log.d("workDetailRVAdapter", "error")
            }
            binding.root.setOnClickListener {
                onClick.onClick(pos)
            }
        }
    }
}