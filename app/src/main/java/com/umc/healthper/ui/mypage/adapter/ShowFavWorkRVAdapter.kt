package com.umc.healthper.ui.mypage.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.R
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.ItemMypageFavWorkBinding
import com.umc.healthper.databinding.ItemMypageFavoritesBinding
import com.umc.healthper.util.VarUtil

class ShowFavWorkRVAdapter(): RecyclerView.Adapter<ShowFavWorkRVAdapter.ListHolder>(), ItemMove.ItemAdapter {
    lateinit var binding: ItemMypageFavWorkBinding
    val db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        binding = ItemMypageFavWorkBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListHolder(binding)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
         holder.bind(position)
    }

    override fun getItemCount(): Int {
        return VarUtil.glob.favWorkList.size
    }

    override fun getSelected(viewHolder: RecyclerView.ViewHolder) {
        val custom = viewHolder as ListHolder
        custom.setback()
//            onBindViewHolder(custom , viewHolder.adapterPosition)
//        }
//        else {
//            binding.itemMypagefavWorkTv.backgroundTintList =
//                ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
//        }
    }

    class ListHolder(val binding: ItemMypageFavWorkBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.itemMypagefavWorkTv.text = VarUtil.glob.favWorkList[pos].workName
        }

        fun setback() {

            binding.itemMypagefavWorkTv.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#99D9EA"))
        }
    }

    override fun onItemMove(fromPos: Int, targetPos: Int) {
        val target = VarUtil.glob.favWorkList[targetPos]
        val from = VarUtil.glob.favWorkList[fromPos]
        val targetWorkId = target.id
        val fromWorkId = from.id
        db.WorkFavDao().editOrder(fromWorkId, targetPos)
        db.WorkFavDao().editOrder(targetWorkId, fromPos)

        var tmp = target
        VarUtil.glob.favWorkList[targetPos] = from
        VarUtil.glob.favWorkList[fromPos] = tmp

        notifyItemMoved(targetPos, fromPos)

    }

    override fun onItemDismiss(pos: Int) {
        val target = VarUtil.glob.favWorkList[pos]
        val targetWorkId = target.id
        db.WorkFavDao().delFavWork(targetWorkId)

        VarUtil.glob.favWorkList.removeAt(pos)

        notifyItemRemoved(pos)
    }
}