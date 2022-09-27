package com.umc.healthper.ui.board.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.remote.AuthRetrofitInterface
import com.umc.healthper.data.remote.Children
import com.umc.healthper.data.remote.Comments
import com.umc.healthper.data.remote.Contents
import com.umc.healthper.databinding.ItemChildCommentBinding
import com.umc.healthper.databinding.ItemCommentBinding
import com.umc.healthper.util.VarUtil
import com.umc.healthper.util.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class childCommentRVAdapter(val data: List<Children>): RecyclerView.Adapter<childCommentRVAdapter.NameHolder>() {

    interface onClickListener {
        fun onDeleteClick(pos: Int)
        fun onRecommend(commendId: Int, pos: Int)
    }
    lateinit var onClick: onClickListener

    fun addRecommend(pos : Int){
        data[pos].likeCount = data[pos].likeCount + 1
        notifyItemChanged(pos)
    }

    fun setListener(set: onClickListener) {
        onClick = set
    }

    lateinit var binding: ItemChildCommentBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameHolder {
        val binding = ItemChildCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        var NormalData = ArrayList<Children>()

        for (tmp in data)
        {
            if (tmp.status == "NORMAL")
                NormalData.add(tmp)
        }
        return NameHolder(binding, NormalData)
    }

    override fun onBindViewHolder(holder: NameHolder, position: Int) {
        holder.bind(position, onClick)
    }

    override fun getItemCount(): Int {
        var size = data.size

        for (tmp in data){
            if (tmp.status != "NORMAL")
                size--
        }
        Log.d("NormalSize", size.toString())
        return size
    }

    class NameHolder(val binding: ItemChildCommentBinding, val data: List<Children>): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, onClick: onClickListener) {

            binding.itemChildCommentNicknameTv.text = data[pos].writer.nickName
            binding.itemChildCommentTimeTv.text = String.format(
                "%s/%s %s:%s",
                data[pos].createdAt.substring(5 until 7),
                data[pos].createdAt.substring(8 until 10),
                data[pos].createdAt.substring(11 until 13),
                data[pos].createdAt.substring(14 until 16)
            )

            binding.itemChildCommentContentTv.text = data[pos].content
            binding.itemChildCommentRecommendTv.text = data[pos].likeCount.toString()

            binding.itemChildCommentDeleteTv.setOnClickListener {
                onClick.onDeleteClick(data[pos].commentId)
            }

            binding.itemChildCommentRecommendBtn.setOnClickListener {
                onClick.onRecommend(data[pos].commentId, pos)
            }
        }
    }

    fun recommendComment(commentId : Int, pos : Int) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.recommendComment(commentId).enqueue(object: Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d("recommend/success", response.body().toString())
                        addRecommend(pos)
                    }
                    409 -> {
                        Toast.makeText(VarUtil.glob.mainContext, "이미 추천한 댓글입니다.", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.d("recommend/fail", response.body().toString())
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("recommend/onfailure", t.toString())
            }
        })
    }
}
