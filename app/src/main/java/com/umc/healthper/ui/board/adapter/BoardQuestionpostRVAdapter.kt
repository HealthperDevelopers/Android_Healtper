package com.umc.healthper.ui.board.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.remote.Contents
import com.umc.healthper.data.remote.WriterInfo
import com.umc.healthper.databinding.ItemBoardQuestionpostBinding
import com.umc.healthper.databinding.ItemLoadingBinding
import com.umc.healthper.util.VarUtil

class BoardQuestionpostRVAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val items = ArrayList<Contents>()
    lateinit var data : List<Contents>

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
        this.data = data
        for (tmp in data)
            items.add(tmp)
        if (items[items.lastIndex].postId != -1)
            items.add(Contents(-1, " ", WriterInfo(0, "", ""), "", 0, 0, ""))
    }

    fun deleteLoading(sortType : String, page : Int){
        if (this.data.isNotEmpty()) {
            Log.d("end", "delete")

            items.removeAt(items.lastIndex) // 로딩이 완료되면 프로그레스바를 지움
            VarUtil.glob.boardQuestionpostFragment.getPosts("NORMAL", sortType, page)
        }
    }

    override fun getItemViewType(position: Int): Int {
        // 게시물과 프로그레스바 아이템뷰를 구분할 기준이 필요하다.
        return when (items[position].postType) {
            " " -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    interface onClickListener {
        fun onClick(postId: Int)
    }
    lateinit var onClick: onClickListener

    fun setListener(set: onClickListener) {
        onClick = set
    }

    lateinit var binding: ItemBoardQuestionpostBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding = ItemBoardQuestionpostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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

    class NameHolder(val binding: ItemBoardQuestionpostBinding, val data: List<Contents>): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, onClick: onClickListener) {
            if (data[pos].postType == "QUESTION") {
                binding.itemBoardQuestionpostTitleTv.text = data[pos].title
                binding.itemBoardPostNicknameTv.text = data[pos].writer.nickName
                binding.itemBoardPostTimeTv.text = String.format(" | %s %s", data[pos].createdAt.substring(0 until 10), data[pos].createdAt.substring(11 until 16))
                binding.itemBoardQuestionpostTitleCommentnumTv.text = data[pos].commentCount.toString()
            }
            binding.root.setOnClickListener {
                onClick.onClick(data[pos].postId)
            }
        }
    }
}