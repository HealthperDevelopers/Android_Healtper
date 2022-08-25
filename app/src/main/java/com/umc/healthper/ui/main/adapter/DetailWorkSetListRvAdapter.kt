package com.umc.healthper.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.remote.GetDayDetailSecond
import com.umc.healthper.databinding.ItemDetailSecondBinding

class DetailWorkSetListRvAdapter(var data: ArrayList<GetDayDetailSecond>, var arrPos: Int): RecyclerView.Adapter<DetailWorkSetListRvAdapter.ViewHolder>() {

    lateinit var binding: ItemDetailSecondBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemDetailSecondBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, data, arrPos)
    }

    override fun getItemCount(): Int {
        if (data[arrPos].details == null) {
            return 0
        }
        return data[arrPos].details!!.size
    }

    class ViewHolder(val binding: ItemDetailSecondBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, data: ArrayList<GetDayDetailSecond>, arrPos: Int) {
            val detail = data[arrPos].details
            if (detail != null) {
                binding.itemDetailSecondSetTv.text = detail[pos].setNumber.toString() + "세트"
                binding.itemDetailSecondVolTv.text = detail[pos].weight.toString() + "kg"
                binding.itemDetailSecondCountTv.text = detail[pos].repeatTime.toString() + "회"

            }
        }
    }

}