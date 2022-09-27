package com.umc.healthper.ui.board.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.R
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.data.remote.Children
import com.umc.healthper.data.remote.Comments
import com.umc.healthper.data.remote.PostId
import com.umc.healthper.databinding.ItemBoardQuestionpostBinding
import com.umc.healthper.databinding.ItemCommentBinding
import com.umc.healthper.util.VarUtil

class CommentRVAdapter(val data: List<Comments>, val postId: Int): RecyclerView.Adapter<CommentRVAdapter.NameHolder>() {

    interface onClickListener {
        fun onDeleteClick(commendId: Int)
        fun onChildClick(commendId: Int)
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

    lateinit var binding: ItemCommentBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        var NormalData = ArrayList<Comments>()

        for (tmp in data)
        {
            if (tmp.status == "NORMAL")
                NormalData.add(tmp)
        }
        return NameHolder(binding, NormalData, postId)
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

    class NameHolder(val binding: ItemCommentBinding, val data: List<Comments>, val postId: Int): RecyclerView.ViewHolder(binding.root)
    {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(pos: Int, onClick: onClickListener) {
            /**대댓글 존재 시 어댑터 바인딩*/
            if (data[pos].children.isNotEmpty()) {
                val childRVAdapter = childCommentRVAdapter(data[pos].children)
                binding.itemChildCommentRv.adapter = childRVAdapter

                childRVAdapter.setListener(object: childCommentRVAdapter.onClickListener{
                    override fun onDeleteClick(commentId: Int) {
                        Log.d("commentId", commentId.toString())

                        val mDialogView = LayoutInflater.from(VarUtil.glob.mainActivity).inflate(R.layout.dialog_comment_delete, null)
                        val mBuilder = AlertDialog.Builder(VarUtil.glob.mainActivity)
                            .setView(mDialogView)

                        val  mAlertDialog = mBuilder.show()
                        mAlertDialog.window?.setLayout(800, 600)

                        val doneButton = mDialogView.findViewById<TextView>(R.id.dialog_comment_delete_delete_bt)
                        val noneButton = mDialogView.findViewById<TextView>(R.id.dialog_comment_delete_none_bt)

                        doneButton.setOnClickListener {
                            mAlertDialog.dismiss()
                            val authService = AuthService()
                            authService.deleteComment(commentId, postId)
                        }
                        noneButton.setOnClickListener {
                            mAlertDialog.dismiss()
                        }
                    }

                    override fun onRecommend(commendId: Int, pos: Int) {
                        childRVAdapter.recommendComment(commendId, pos)
                    }
                })
                childRVAdapter.notifyDataSetChanged()
            }

            binding.itemCommentNicknameTv.text = data[pos].writer.nickName

            binding.itemCommentTimeTv.text = String.format(
                "%s/%s %s:%s",
                data[pos].createdAt.substring(5 until 7),
                data[pos].createdAt.substring(8 until 10),
                data[pos].createdAt.substring(11 until 13),
                data[pos].createdAt.substring(14 until 16)
            )

            binding.itemCommentContentTv.text = data[pos].content
            binding.itemCommentRecommendTv.text = data[pos].likeCount.toString()

            binding.itemCommentDeleteTv.setOnClickListener {
                onClick.onDeleteClick(data[pos].commentId)
            }

            binding.itemCommentChildCommentBtn.setOnClickListener{
                onClick.onChildClick(data[pos].commentId)
            }

            binding.itemCommentRecommendBtn.setOnClickListener{
                onClick.onRecommend(data[pos].commentId, pos)
            }
        }
    }
}
