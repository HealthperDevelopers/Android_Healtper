package com.umc.healthper.ui.board.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.umc.healthper.data.entity.ChildComment
import com.umc.healthper.data.entity.Comment
import com.umc.healthper.data.remote.APostResponse
import com.umc.healthper.data.remote.AuthRetrofitInterface
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.databinding.FragmentBoardFreepostContentBinding
import com.umc.healthper.ui.board.adapter.CommentRVAdapter
import com.umc.healthper.util.VarUtil
import com.umc.healthper.util.getRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardFreepostContentFragment : Fragment() {
    lateinit var binding : FragmentBoardFreepostContentBinding
    lateinit var adapter : CommentRVAdapter
    var postId = 0

    override fun onPause() {
        super.onPause()
        val trans = VarUtil.glob.mainActivity.supportFragmentManager
        trans.popBackStack(
            "boardFreeContent",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardFreepostContentBinding.inflate(inflater, container, false)
        viewPost(postId)

        binding.noTouch.setOnClickListener{
            Log.d("touch", "touch")
        }

        binding.boardFreepostContentCommentCommentTv.setOnClickListener{
            comment(Comment(postId, binding.boardFreepostContentCommentEt.text.toString()))
            VarUtil.glob.mainActivity.softkeyboardHide().hideSoftInputFromWindow(binding.boardFreepostContentCommentEt.windowToken, 0)
        }

        return binding.root
    }

    fun childcomment(childcomment : ChildComment){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.childcomment(childcomment).enqueue(object :Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>
            ) {
                if (response.code() == 200) {
                    Log.d("comment/success", response.toString())
                    CoroutineScope(Dispatchers.Main).launch{
                        viewPost(postId)
                        binding.boardFreepostContentCommentEt.setText("")
                    }
                }
                else {
                    Log.d("comment/failure", "fail")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("comment/FAILURE", t.message.toString())
            }
        })
    }

    /** 댓글 생성 함수 */
    fun comment(comment : Comment){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.comment(comment).enqueue(object :Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>
            ) {
                if (response.code() == 200) {
                    Log.d("comment/success", response.toString())
                    CoroutineScope(Dispatchers.Main).launch{
                        viewPost(postId)
                        binding.boardFreepostContentCommentEt.setText("")
                    }
                }
                else {
                    Log.d("comment/failure", "fail")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("comment/FAILURE", t.message.toString())
            }
        })
    }

    /** 게시판 정보 가져오는 함수
     * postID를 주어야함.
     * */
    fun viewPost(postId: Int){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.viewPost(postId).enqueue(object: Callback<APostResponse> {
            override fun onResponse(
                call: Call<APostResponse>,
                response: Response<APostResponse>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d("viewPost/success", response.body().toString())
                        var resp = response.body()
                        binding.boardFreepostContentWriterTv.text = resp!!.writer.nickName
                        binding.boardFreepostContentPostingTimeTv.text = resp.createdAt
                        binding.boardFreepostContentPostTitleTv.text = resp.title
                        binding.boardFreepostContentPostContentTv.text = resp.content

                        val like = arguments!!.getIntegerArrayList("like&commentCount")!!.first()
                        val comment = arguments!!.getIntegerArrayList("like&commentCount")!!.last()
                        binding.boardFreepostContentRecommendTv.text = like.toString()
                        binding.boardFreepostContentCommentTv.text = comment.toString()

                        adapter = CommentRVAdapter(resp.comments, postId)
                        binding.boardFreepostContentCommentRv.adapter = adapter
                        adapter.setListener(object: CommentRVAdapter.onClickListener{
                            override fun onDeleteClick(commentId: Int) {
                                Log.d("commentId", commentId.toString())
                                deleteComment(commentId)
                            }

                            override fun onChildClick(parentId: Int) {
                                Log.d("child", "child")

                                VarUtil.glob.mainActivity.softkeyboardHide().toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                                binding.boardFreepostContentCommentCommentTv.setOnClickListener {
                                    childcomment(
                                        ChildComment(
                                            postId,
                                            parentId,
                                            binding.boardFreepostContentCommentEt.text.toString()
                                        )
                                    )
                                    VarUtil.glob.mainActivity.softkeyboardHide().hideSoftInputFromWindow(binding.boardFreepostContentCommentEt.windowToken, 0)
                                }
                            }
                        })
                    }
                    else -> {
                        Log.d("viewPost/fail", response.body().toString())
                    }
                }
            }

            override fun onFailure(call: Call<APostResponse>, t: Throwable) {
                Log.d("viewPost/fail onfailure", t.toString())
            }

        })
    }

    fun deleteComment(commentId : Int) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.deleteComment(commentId).enqueue(object: Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d("deleteComment/success", response.body().toString())
                        viewPost(postId)
                    }
                    401 -> {
                        Toast.makeText(VarUtil.glob.mainContext, "댓글을 수정/삭제할 수 있는 권한이 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.d("deleteComment/fail", response.body().toString())
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("deleteComment/onfailure", t.toString())
            }

        })
    }
}