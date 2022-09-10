package com.umc.healthper.ui.board.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.remote.Contents
import com.umc.healthper.data.remote.WriterInfo
import com.umc.healthper.databinding.ItemBoardFreepostBinding
import com.umc.healthper.databinding.ItemLoadingBinding

class BoardFreepostRVAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val items = ArrayList<Contents>()

    fun addRecommend(pos : Int){
        items[pos].likeCount = items[pos].likeCount + 1
        notifyItemChanged(pos)
    }

    fun setList(clear : Boolean)
    {
        if (clear)
            items.clear()
    }

    fun setList(data: List<Contents>){

        for (tmp in data)
        {
            if (tmp.postType == "NORMAL")
                items.add(tmp)
        }
        items.add(Contents(-1, " ", WriterInfo(0, "", ""), "", 0, 0, ""))
    }
    fun deleteLoading(){
        items.removeAt(items.lastIndex) // 로딩이 완료되면 프로그레스바를 지움
    }
    override fun getItemViewType(position: Int): Int {
        // 게시물과 프로그레스바 아이템뷰를 구분할 기준이 필요하다.
        Log.d("position", position.toString())
        Log.d("position / postType", items[position].postType)
        return when (items[position].postType) {
            " " -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    interface onClickListener {
        fun onClick(postId: Int, likeCount:Int, CommentCount : Int, position: Int)
    }
    lateinit var onClick: onClickListener

    fun setListener(set: onClickListener) {
        onClick = set
    }

    lateinit var binding: ItemBoardFreepostBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding = ItemBoardFreepostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                NameHolder(binding, items)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var safePosition = holder.adapterPosition

        if (holder is NameHolder) {
            holder.bind(safePosition, onClick)
        }
        else{

        }
    }

    override fun getItemCount(): Int {
        var size = items.size

        return size
    }

    class LoadingHolder(val binding : ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root){

    }

    class NameHolder(val binding: ItemBoardFreepostBinding, val data: List<Contents>): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, onClick: onClickListener) {
            if (data[pos].postType == "NORMAL") {
                binding.itemBoardPostTitleTv.text = data[pos].title
                binding.itemBoardPostNicknameTv.text = data[pos].writer.nickName
                binding.itemBoardPostTimeTv.text = data[pos].createdAt
                binding.itemBoardPostRecommendTv.text = data[pos].likeCount.toString()
                binding.itemBoardPostCommentTv.text = data[pos].commentCount.toString()
            }
            binding.root.setOnClickListener {
                onClick.onClick(data[pos].postId, data[pos].likeCount, data[pos].commentCount, pos)
            }
        }
    }
}