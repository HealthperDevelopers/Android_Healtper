package com.umc.healthper.ui.board.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.R
import com.umc.healthper.data.entity.Content
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.data.remote.Children
import com.umc.healthper.data.remote.Comments
import com.umc.healthper.data.remote.PostId
import com.umc.healthper.databinding.ItemBoardQuestionpostBinding
import com.umc.healthper.databinding.ItemCommentBinding
import com.umc.healthper.util.VarUtil

class CommentRVAdapter(val data: List<Comments>, val postId: Int): RecyclerView.Adapter<CommentRVAdapter.NameHolder>() {

    interface onClickListener {
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

            binding.itemCommentMoreBtn.setOnClickListener {
                val mDialogView = LayoutInflater.from(VarUtil.glob.mainActivity).inflate(R.layout.dialog_comment_more, null)
                val mBuilder = AlertDialog.Builder(VarUtil.glob.mainActivity!!)
                    .setView(mDialogView)

                val  mAlertDialog = mBuilder.show()
                mAlertDialog.window?.setLayout(800, 600)

                val firbt = mDialogView.findViewById<TextView>(R.id.comment_more_fir_bt)
                val secbt = mDialogView.findViewById<TextView>(R.id.comment_more_sec_bt)

                if (VarUtil.glob.Nickname == data[pos].writer.nickName) {
                    firbt.text = "수정하기"
                    secbt.text = "삭제하기"

                    firbt.setOnClickListener {
                        mAlertDialog.dismiss()
                        val mDialogView = LayoutInflater.from(VarUtil.glob.mainActivity).inflate(R.layout.dialog_comment_modify, null)
                        val mBuilder = AlertDialog.Builder(VarUtil.glob.mainActivity)
                            .setView(mDialogView)

                        val  mModifyDialog = mBuilder.show()
                        mModifyDialog.window?.setLayout(800, 600)

                        val okButton = mDialogView.findViewById<TextView>(R.id.comment_modify_ok_tv)
                        val noneButton = mDialogView.findViewById<TextView>(R.id.comment_modify_none_tv)
                        okButton.setOnClickListener {
                            mModifyDialog.dismiss()
                            binding.itemCommentContentEt.visibility = View.VISIBLE
                            binding.itemCommentContentEt.setText(binding.itemCommentContentTv.text)
                            binding.itemCommentContentTv.visibility = View.INVISIBLE
                            binding.itemCommentContentEt.requestFocus()
                            binding.itemCommentContentEt.isEnabled = true

                            VarUtil.glob.mainActivity.softkeyboardHide().toggleSoftInput(
                                InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                            VarUtil.glob.mainActivity.boardFreepostContentFragment?.binding?.boardFreepostContentCommentCommentTv?.text = "댓글 수정"
                            VarUtil.glob.mainActivity.boardFreepostContentFragment?.binding?.boardFreepostContentCommentEt?.isEnabled = false
                            VarUtil.glob.mainActivity.boardFreepostContentFragment?.binding?.boardFreepostContentCommentCommentTv?.setOnClickListener {
                                // 댓글 수정 api 연결
                                var authService = AuthService()
                                authService.modifyComment(data[pos].commentId, Content(binding.itemCommentContentEt.text.toString()))

                                VarUtil.glob.mainActivity.softkeyboardHide().hideSoftInputFromWindow(binding.itemCommentContentEt.windowToken, 0)
                                binding.itemCommentContentEt.isEnabled = false
                                binding.itemCommentContentEt.visibility = View.INVISIBLE
                                binding.itemCommentContentTv.text = binding.itemCommentContentEt.text
                                binding.itemCommentContentTv.visibility = View.VISIBLE
                                VarUtil.glob.mainActivity.boardFreepostContentFragment?.binding?.boardFreepostContentCommentCommentTv?.text = "댓글 생성"
                                VarUtil.glob.mainActivity.boardFreepostContentFragment?.binding?.boardFreepostContentCommentEt?.isEnabled = true
                            }
                        }
                        noneButton.setOnClickListener {
                            mModifyDialog.dismiss()
                        }
                    }
                    secbt.setOnClickListener {
                        mAlertDialog.dismiss()
                        val mDialogView = LayoutInflater.from(VarUtil.glob.mainActivity).inflate(R.layout.dialog_comment_delete, null)
                        val mBuilder = AlertDialog.Builder(VarUtil.glob.mainActivity)
                            .setView(mDialogView)

                        val  mDeleteDialog = mBuilder.show()
                        mDeleteDialog.window?.setLayout(800, 600)

                        val doneButton = mDialogView.findViewById<TextView>(R.id.dialog_comment_delete_delete_bt)
                        val noneButton = mDialogView.findViewById<TextView>(R.id.dialog_comment_delete_none_bt)
                        doneButton.setOnClickListener {
                            mDeleteDialog.dismiss()
                            VarUtil.glob.mainActivity.boardFreepostContentFragment?.deleteComment(data[pos].commentId)
                            VarUtil.glob.mainActivity.boardQuestionpostContentFragment?.deleteComment(data[pos].commentId)
                        }
                        noneButton.setOnClickListener {
                            mDeleteDialog.dismiss()
                        }
                    }
                }
                else {
                    firbt.text = "신고하기"
                    secbt.text = "차단하기"
                    firbt.setOnClickListener {
                        // 신고하기 기능 구현
                    }
                    secbt.setOnClickListener {
                        // 차단하기 기능 구현
                    }
                }
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
