package com.umc.healthper.ui.main.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.ItemWorkreadyWorkpartBinding
import com.umc.healthper.ui.timer.TimerActivity
import com.umc.healthper.util.VarUtil

class WorkReadyListAdapter(): RecyclerView.Adapter<WorkReadyListAdapter.ListHolder>() {

    interface onClickListener {
        fun onClick(pos: Int)
    }
    lateinit var listener: onClickListener

    fun setOnClickListener(listen: onClickListener) {
        listener = listen
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(position, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val binding = ItemWorkreadyWorkpartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListHolder(binding)
    }

    override fun getItemCount(): Int {
        return VarUtil.glob.selectedPart.size
    }

    class ListHolder(val binding: ItemWorkreadyWorkpartBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, listener: onClickListener) {
            var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!

            binding.itemWorkreadyWorkpartPartTv.text = VarUtil.glob.selectedPart[pos]
            binding.itemWorkreadyWorkpartPartTv.backgroundTintList = ColorStateList.valueOf(Color.parseColor(db.WorkPartDao().getColorbyPartName(VarUtil.glob.selectedPart[pos])))

            /** set item's part time*/
            var second = TimerActivity().partTime(VarUtil.glob.selectedPart[pos])
            var minute = second / 60
            var hour = minute / 60
            binding.itemWorkreadyWorkpartTimeTv.text = String.format("%02d : %02d : %02d", hour, minute, second % 60)

            /** set item's part volume*/
            var volume = TimerActivity().partVolume(db.WorkPartDao().getWorkPartIdbyPartName(VarUtil.glob.selectedPart[pos]))
            binding.itemWorkreadyWorkpartVolTv.text = String.format("%d kg", volume)

            setListener(pos, listener)
        }

        fun setListener(pos: Int, listener: onClickListener) {
            binding.root.setOnClickListener {
                listener.onClick(pos)
            }
        }
    }
}