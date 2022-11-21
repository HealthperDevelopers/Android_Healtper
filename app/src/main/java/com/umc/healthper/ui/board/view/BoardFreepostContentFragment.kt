package com.umc.healthper.ui.board.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.umc.healthper.R
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

        binding.boardFreepostContentReturnIv.setOnClickListener {
            // 뒤로 가기 구현
            VarUtil.glob.mainActivity.supportFragmentManager.popBackStack(
                "boardFreeComment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }

        binding.boardFreepostContentCommentCommentTv.setOnClickListener{
            comment(Comment(postId, binding.boardFreepostContentCommentEt.text.toString()))
            VarUtil.glob.mainActivity.softkeyboardHide().hideSoftInputFromWindow(binding.boardFreepostContentCommentEt.windowToken, 0)
        }

        binding.boardFreepostContentRecommendBtIv.setOnClickListener {
            RecommendPost(postId)
        }

        val spinner = binding.boardFreepostContentPostintSettingIv
        spinner.adapter = ArrayAdapter.createFromResource(VarUtil.glob.mainContext, R.array.itemBoardPostList, android.R.layout.simple_spinner_item)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    //새로고침
                    1 -> {
                        viewPost(postId)
                    }
                    //신고
                    2 -> {

                        /*val fragmentManager = activity!!.supportFragmentManager
                        fragmentManager.popBackStack()*/
                        val mPopupView = LayoutInflater.from(VarUtil.glob.mainActivity).inflate(R.layout.popup_window_report_user, null)
                        val mPopupContentView = LayoutInflater.from(VarUtil.glob.mainActivity).inflate(R.layout.popup_window_report_detail, null)
                        val mBuilder = AlertDialog.Builder(VarUtil.glob.mainActivity) .setView(mPopupView)
                        val mBuilderContent=AlertDialog.Builder(VarUtil.glob.mainActivity) .setView(mPopupContentView)

                        val mAlertDialog = mBuilder.show()
                        val mAlertContentPopup=mBuilderContent.show()

                        mAlertDialog.window?.setLayout(800,1200)
                        val nineteenButton = mPopupView.findViewById<TextView>(R.id.popup_window_report_19_tv)
                        val commerceButton = mPopupView.findViewById<TextView>(R.id.popup_window_report_commerce_tv)
                        val condemnButton = mPopupView.findViewById<TextView>(R.id.popup_window_report_condemn_tv)
                        val fraudButton = mPopupView.findViewById<TextView>(R.id.popup_window_report_fraud_tv)
                        val repeatButton = mPopupView.findViewById<TextView>(R.id.popup_window_report_repeat_tv)
                        val politicButton = mPopupView.findViewById<TextView>(R.id.popup_window_report_politic_tv)
                        val propertyButton = mPopupView.findViewById<TextView>(R.id.popup_window_report_property_tv)

                        nineteenButton.setOnClickListener {
                            mAlertDialog.dismiss()
                            mAlertContentPopup.window?.setLayout(800,600)
                            val doneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_reportBtn_tv)
                            val noneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_cancelBtn_tv)
                            doneButton.setOnClickListener {
                                mAlertContentPopup.dismiss()
                                reportPost(postId)
                            }
                            noneButton.setOnClickListener {
                                mAlertContentPopup.dismiss()
                            }
                        }
                         commerceButton.setOnClickListener {
                             mAlertDialog.dismiss()
                             mAlertContentPopup.window?.setLayout(800,600)
                             val doneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_reportBtn_tv)
                             val noneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_cancelBtn_tv)
                             doneButton.setOnClickListener {
                                 mAlertContentPopup.dismiss()
                                 reportPost(postId)
                             }
                             noneButton.setOnClickListener {
                                 mAlertContentPopup.dismiss()
                             }
                        }
                        condemnButton.setOnClickListener {
                            mAlertDialog.dismiss()
                            mAlertContentPopup.window?.setLayout(800,600)
                            val doneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_reportBtn_tv)
                            val noneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_cancelBtn_tv)
                            doneButton.setOnClickListener {
                                mAlertContentPopup.dismiss()
                                reportPost(postId)
                            }
                            noneButton.setOnClickListener {
                                mAlertContentPopup.dismiss()
                            }
                        }
                        fraudButton.setOnClickListener {
                            mAlertDialog.dismiss()
                            mAlertContentPopup.window?.setLayout(800,600)
                            val doneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_reportBtn_tv)
                            val noneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_cancelBtn_tv)
                            doneButton.setOnClickListener {
                                mAlertContentPopup.dismiss()
                                reportPost(postId)
                            }
                            noneButton.setOnClickListener {
                                mAlertContentPopup.dismiss()
                            }
                        }
                        repeatButton.setOnClickListener {
                            mAlertDialog.dismiss()
                            mAlertContentPopup.window?.setLayout(800,600)
                            val doneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_reportBtn_tv)
                            val noneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_cancelBtn_tv)
                            doneButton.setOnClickListener {
                                mAlertContentPopup.dismiss()
                                reportPost(postId)
                            }
                            noneButton.setOnClickListener {
                                mAlertContentPopup.dismiss()
                            }
                        }
                        politicButton.setOnClickListener {
                            mAlertDialog.dismiss()
                            mAlertContentPopup.window?.setLayout(800,600)
                            val doneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_reportBtn_tv)
                            val noneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_cancelBtn_tv)
                            doneButton.setOnClickListener {
                                mAlertContentPopup.dismiss()
                                reportPost(postId)
                            }
                            noneButton.setOnClickListener {
                                mAlertContentPopup.dismiss()
                            }
                        }
                        propertyButton.setOnClickListener {
                            mAlertDialog.dismiss()
                            mAlertContentPopup.window?.setLayout(800,600)
                            val doneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_reportBtn_tv)
                            val noneButton = mPopupContentView.findViewById<TextView>(R.id.popup_window_report_detail_cancelBtn_tv)
                            doneButton.setOnClickListener {
                                mAlertContentPopup.dismiss()
                                reportPost(postId)
                            }
                            noneButton.setOnClickListener {
                                mAlertContentPopup.dismiss()
                            }
                        }

                    }
                    //삭제
                    3 -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            // dialog
                            val mDialogView = LayoutInflater.from(VarUtil.glob.mainActivity).inflate(R.layout.dialog_comment_delete, null)
                            val mBuilder = AlertDialog.Builder(VarUtil.glob.mainActivity)
                                .setView(mDialogView)

                            val  mAlertDialog = mBuilder.show()
                            mAlertDialog.window?.setLayout(800, 600)

                            val doneButton = mDialogView.findViewById<TextView>(R.id.dialog_comment_delete_delete_bt)
                            val noneButton = mDialogView.findViewById<TextView>(R.id.dialog_comment_delete_none_bt)
                            doneButton.setOnClickListener {
                                mAlertDialog.dismiss()
                                deletePost(postId)
                            }
                            noneButton.setOnClickListener {
                                mAlertDialog.dismiss()
                            }
                            spinner.setSelection(0)
                        }
                    }
                    4->{
                        CoroutineScope(Dispatchers.Main).launch {
                            VarUtil.glob.mainActivity.changeBoardFragment(1)
                            VarUtil.glob.mainActivity.boardWritingFragement!!.modify = true
                            VarUtil.glob.mainActivity.boardWritingFragement!!.postId = postId
                            VarUtil.glob.mainActivity.boardWritingFragement!!.title = binding.boardFreepostContentPostTitleTv.text.toString()
                            VarUtil.glob.mainActivity.boardWritingFragement!!.content = binding.boardFreepostContentPostContentTv.text.toString()
                            VarUtil.glob.mainActivity.boardWritingFragement!!.postType = "NORMAL"
                        }
                        spinner.setSelection(0)
                    }
                }
            }
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
                        CoroutineScope(Dispatchers.Main).launch {
                            var yyyymmdd = resp.createdAt.substring(0 until 10)
                            var hhss = resp.createdAt.substring(11 until 16)
                            binding.boardFreepostContentPostingTimeTv.text = String.format("%s %s", yyyymmdd, hhss)
                        }
                        binding.boardFreepostContentPostTitleTv.text = resp.title
                        binding.boardFreepostContentPostContentTv.text = resp.content

                        val like = arguments!!.getIntegerArrayList("like&commentCount")!!.first()
                        val comment = arguments!!.getIntegerArrayList("like&commentCount")!!.get(1)
                        binding.boardFreepostContentRecommendTv.text = like.toString()
                        binding.boardFreepostContentCommentTv.text = comment.toString()

                        adapter = CommentRVAdapter(resp.comments, postId)
                        binding.boardFreepostContentCommentRv.adapter = adapter
                        adapter.setListener(object: CommentRVAdapter.onClickListener{
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

                            override fun onRecommend(commendId: Int, pos : Int) {
                                recommendComment(commendId, pos)
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

    fun RecommendPost(postId: Int)
    {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.RecommendPost(postId).enqueue(object :Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>
            ) {
                when (response.code()){
                    200 -> {
                        Log.d("RecommendPost/success", response.toString())
                        binding.boardFreepostContentRecommendTv.text = (binding.boardFreepostContentRecommendTv.text.toString().toInt() + 1).toString()
                        VarUtil.glob.boardFreepostFragment.adapter.addRecommend(arguments?.getIntegerArrayList("like&commentCount")!!.last())
                    }
                    409 -> {
                        Toast.makeText(VarUtil.glob.mainContext, "이미 추천한 게시글입니다.", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.d("RecommendPost/failure", "fail")
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("RecommendPost/FAILURE", t.message.toString())
            }
        })
    }

    fun UnrecommendPost(postId: Int)
    {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.UnrecommendPost(postId).enqueue(object :Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>
            ) {
                if (response.code() == 200) {
                    Log.d("deletePost/success", response.toString())
                }
                else {
                    Log.d("deletePost/failure", "fail")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("deletePost/FAILURE", t.message.toString())
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
                        adapter.addRecommend(pos)
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

    fun deletePost(postId: Int)
    {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.deletePost(postId).enqueue(object :Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d("deletePost/success", response.body().toString())
                        VarUtil.glob.boardFreepostFragment.adapter.notifyItemRemoved(
                            arguments!!.getIntegerArrayList(
                                "like&commentCount"
                            )!!.last()
                        )
                        val fragmentManager = activity!!.supportFragmentManager
                        fragmentManager.popBackStack()
                    }
                    401 -> {
                        Toast.makeText(
                            VarUtil.glob.mainContext,
                            "게시글을 수정/삭제할 수 있는 권한이 없습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("deletePost/FAILURE", t.message.toString())
            }
        })
    }

    fun reportPost(postId : Int){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.reportPost(postId).enqueue(object :Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d("reportPost/success", response.body().toString())
                        VarUtil.glob.boardFreepostFragment.adapter.notifyItemRemoved(
                            arguments!!.getIntegerArrayList(
                                "report&blockCount"
                            )!!.last()
                        )
                        val fragmentManager = activity!!.supportFragmentManager
                        fragmentManager.popBackStack()
                        /*용도?*/
                    }
                    401 -> {
                        Toast.makeText(
                            VarUtil.glob.mainContext,
                            "게시글을 신고할 수 있는 권한이 없습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    404 -> {
                        Log.d("reportPost/success", response.body().toString())
                        VarUtil.glob.boardFreepostFragment.adapter.notifyItemReported(
                            arguments!!.getIntegerArrayList(
                                "report&blockCount"
                            )!!.last()
                        )
                        val fragmentManager = activity!!.supportFragmentManager
                        fragmentManager.popBackStack()

                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("deletePost/FAILURE", t.message.toString())
            }
        })
    }
    }
}