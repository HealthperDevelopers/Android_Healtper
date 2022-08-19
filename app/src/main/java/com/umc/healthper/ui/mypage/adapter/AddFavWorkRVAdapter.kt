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

class AddFavWorkRVAdapter(val nonFavList: MutableList<Work>, val partId: Int):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface Listener {
        fun onClick(pos: Int)
    }
    lateinit var listenerSet: Listener
    fun setListener(set: Listener) {
        listenerSet = set
    }
    lateinit var binding: ItemWorkdetailWorknameBinding
    val db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemWorkdetailWorknameBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return WorkListBinding(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WorkListBinding).bind(position)
    }

    override fun getItemCount(): Int {
        return nonFavList.size
    }

    inner class WorkListBinding(binding: ItemWorkdetailWorknameBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            Log.d("allList", nonFavList.toString())
            binding.itemWorkdetailWorknameTv.text = nonFavList[pos].workName
            binding.root.setOnClickListener {

                val data = WorkFav( 0,db.WorkFavDao().getAllFavWorkByPartId(partId).size, nonFavList[pos].id, partId)
                db.WorkFavDao().insert(data)
                listenerSet.onClick(pos)
            }
        }
    }
}