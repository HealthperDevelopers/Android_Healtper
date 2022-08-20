package com.umc.healthper.ui.mypage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.ItemEditWorkPartBinding
import com.umc.healthper.databinding.ItemMypageFavoritesBinding
import com.umc.healthper.util.VarUtil

class PartRVAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface listener {
        fun onClick(str: String)
    }
    lateinit var setListener: listener
    fun setListen(set: listener) {
        setListener = set
    }

    lateinit var binding: ItemEditWorkPartBinding
    var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemEditWorkPartBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListBinding(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ListBinding).bind(position)
    }

    override fun getItemCount(): Int {
        return db.WorkPartDao().getAllWork().size
    }

    inner class ListBinding(binding: ItemEditWorkPartBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.itemEditWorkPartTv.text = db.WorkPartDao().getAllWork()[pos]
            binding.root.setOnClickListener {
                setListener.onClick(db.WorkPartDao().getAllWork()[pos])
            }
        }
    }
}