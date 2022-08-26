package com.umc.healthper.ui.main.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.ItemMainDetailListBinding
import com.umc.healthper.util.VarUtil

class DetailItemPartListRvAdapter(val data: ArrayList<String>?): RecyclerView.Adapter<DetailItemPartListRvAdapter.ViewHolder>() {

    lateinit var binding: ItemMainDetailListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMainDetailListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, data)
    }

    override fun getItemCount(): Int {
        if (data.isNullOrEmpty()) {
            return 0
        }
        return data.size
    }

    class ViewHolder(val binding: ItemMainDetailListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, data: ArrayList<String>? ) {
            if (!data.isNullOrEmpty()) {
                val db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
                binding.itemMainDetailIlstBackgroundTv.backgroundTintList =
                    ColorStateList.valueOf(
                        Color.parseColor(
                            db.WorkPartDao().getColorbyPartName(data[pos])
                        )
                    )
                binding.itemMainDetailIlstTv.text = data[pos]
            }
        }
    }
}