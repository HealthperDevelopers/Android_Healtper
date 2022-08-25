package com.umc.healthper.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.data.remote.GetDayDetailSecond
import com.umc.healthper.databinding.ItemWorkreadyWorkpartBinding
import com.umc.healthper.util.VarUtil

class DetailWorkRecordRvAdapter(): RecyclerView.Adapter<DetailWorkRecordRvAdapter.ViewHolder>() {

    interface Listener {
        fun onClick(pos: Int, partname: String)
    }
    lateinit var userListener: Listener
    fun setListener(data: Listener) {
        userListener = data
    }

    lateinit var binding: ItemWorkreadyWorkpartBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemWorkreadyWorkpartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, userListener)
    }

    override fun getItemCount(): Int {
        return VarUtil.glob.recordPartList.size
    }

    class ViewHolder(val binding: ItemWorkreadyWorkpartBinding):RecyclerView.ViewHolder(binding.root) {
        val db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
        fun bind(pos: Int, userListener: Listener) {
            val data = ArrayList<GetDayDetailSecond>()
            for (i in VarUtil.glob.recordList) {
                if (VarUtil.glob.recordPartList[pos] == i.section) {
                    data.add(i)
                }
            }
            binding.itemWorkreadyWorkpartPartTv.text = VarUtil.glob.recordPartList[pos]
            var totalTime = 0
            for (i in data) {
                totalTime += i.exerciseTime
            }
            binding.itemWorkreadyWorkpartTimeTv.text = totalTime.toString()
            var totalVol = 0
            for (i in data) {
                for (j in i.details!!) {
                    totalVol = j.repeatTime * j.weight
                }
            }
            binding.itemWorkreadyWorkpartVolTv.text = totalVol.toString()


            binding.root.setOnClickListener {
                userListener.onClick(pos, binding.itemWorkreadyWorkpartPartTv.text.toString())
            }
        }
    }
}