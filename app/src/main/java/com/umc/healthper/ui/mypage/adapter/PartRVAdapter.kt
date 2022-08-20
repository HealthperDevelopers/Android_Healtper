package com.umc.healthper.ui.mypage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.ItemEditWorkPartBinding
import com.umc.healthper.databinding.ItemMypageFavoritesBinding
import com.umc.healthper.util.VarUtil

class PartRVAdapter(): RecyclerView.Adapter<PartRVAdapter.ListBinding>() {

    interface listener {
        fun onClick(str: String)
    }
    lateinit var setListener: listener
    fun setListen(set: listener) {
        setListener = set
    }

    lateinit var binding: ItemEditWorkPartBinding
    var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListBinding {
        binding = ItemEditWorkPartBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListBinding(binding, db)
    }

    override fun onBindViewHolder(holder: ListBinding, position: Int) {
        holder.bind(position, setListener)
    }

    override fun getItemCount(): Int {
        return db.WorkPartDao().getAllWork().size
    }

    class ListBinding(val binding: ItemEditWorkPartBinding, val db: LocalDB): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, listener: listener) {
            binding.itemEditWorkPartTv.text = db.WorkPartDao().getAllWork()[pos]
            binding.root.setOnClickListener {
                listener.onClick(db.WorkPartDao().getAllWork()[pos])
            }
        }
    }
}