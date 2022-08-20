package com.umc.healthper.ui.mypage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.entity.WorkFav
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.ItemWorkdetailWorknameBinding
import com.umc.healthper.util.VarUtil

class AddFavWorkRVAdapter(val nonFavList: MutableList<Work>, val partId: Int):RecyclerView.Adapter<AddFavWorkRVAdapter.WorkListBinding>() {
    interface Listener {
        fun onClick(pos: Int)
    }
    lateinit var listenerSet: Listener
    fun setListener(set: Listener) {
        listenerSet = set
    }

    lateinit var binding: ItemWorkdetailWorknameBinding
    val db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkListBinding {
        binding = ItemWorkdetailWorknameBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return WorkListBinding(binding, partId, db)
    }

    override fun onBindViewHolder(holder: WorkListBinding, position: Int) {
        holder.bind(position, nonFavList, listenerSet)
    }

    override fun getItemCount(): Int {
        return nonFavList.size
    }

    class WorkListBinding(val binding: ItemWorkdetailWorknameBinding, val partId: Int, val db: LocalDB): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, data: MutableList<Work>, listener: Listener) {
            binding.itemWorkdetailWorknameTv.text = data[pos].workName
            binding.root.setOnClickListener {

                val data = WorkFav( 0,db.WorkFavDao().getAllFavWorkByPartId(partId).size, data[pos].id, partId)
                db.WorkFavDao().insert(data)
                listener.onClick(pos)
            }
        }
    }
}