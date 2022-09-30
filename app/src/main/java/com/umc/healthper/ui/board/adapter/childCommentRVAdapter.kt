package com.umc.healthper.ui.board.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.R
import com.umc.healthper.data.entity.Content
import com.umc.healthper.data.remote.*
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

//            binding.itemChildCommentDeleteTv.setOnClickListener {
//                onClick.onDeleteClick(data[pos].commentId)
//            }

            binding.itemChildCommentMoreBtn.setOnClickListener {
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
                            Log.d("modi/child", "comment")
                            binding.itemChildCommentContentEt.visibility = View.VISIBLE
                            binding.itemChildCommentContentEt.setText(binding.itemChildCommentContentTv.text.toString() + " hello")


                            var edit = binding.itemChildCommentContentEt
                            edit.isFocusableInTouchMode = true
                            edit.requestFocus()

                            binding.itemChildCommentContentTv.visibility = View.INVISIBLE

                            VarUtil.glob.mainActivity.softkeyboardHide().toggleSoftInput(
                                InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                            VarUtil.glob.mainActivity.boardFreepostContentFragment?.binding?.boardFreepostContentCommentCommentTv?.text = "댓글 수정"
                            VarUtil.glob.mainActivity.boardFreepostContentFragment?.binding?.boardFreepostContentCommentEt?.isEnabled = false
                            VarUtil.glob.mainActivity.boardFreepostContentFragment?.binding?.boardFreepostContentCommentCommentTv?.setOnClickListener {
                                // 댓글 수정 api 연결
                                var authService = AuthService()
                                authService.modifyComment(data[pos].commentId, Content(binding.itemChildCommentContentEt.text.toString()))

                                VarUtil.glob.mainActivity.softkeyboardHide().hideSoftInputFromWindow(binding.itemChildCommentContentEt.windowToken, 0)
                                binding.itemChildCommentContentEt.isEnabled = false
                                binding.itemChildCommentContentEt.visibility = View.INVISIBLE
                                binding.itemChildCommentContentTv.text = binding.itemChildCommentContentEt.text
                                binding.itemChildCommentContentTv.visibility = View.VISIBLE
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
