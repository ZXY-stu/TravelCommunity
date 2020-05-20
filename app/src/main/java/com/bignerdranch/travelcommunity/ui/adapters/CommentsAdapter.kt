package com.bignerdranch.travelcommunity.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginStart
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.data.db.entity.CommentsMsg
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.databinding.CommentsBinding
import com.bignerdranch.travelcommunity.databinding.ItemCommentsBinding
import com.bignerdranch.travelcommunity.ui.dynamic.CommentsDialog
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.user.FriendFragment
import com.bignerdranch.travelcommunity.ui.user.LoginFragment
import com.bignerdranch.travelcommunity.ui.user.NoticeLoginDialog
import com.bignerdranch.travelcommunity.ui.user.NoticeLoginFragment


/**
 * @author zhongxinyu
 * @date 2020/4/6
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/


class CommentsAdapter(val _viewModel: PersonDynamicViewModel,
                      val fragmentManager: FragmentManager,
                      val fragment: Fragment
) : BaseAdapter<ItemCommentsBinding,CommentsMsg>() {


    override val itemLayoutId: Int = R.layout.item_comments
    private  lateinit var commentsListener:CommentsLinkListener
    private val friendFragment = FriendFragment(_viewModel = _viewModel)

    interface CommentsLinkListener{
        fun linkName(name:String)
    }

    fun setCommentsLinkListener( commentsListener:CommentsLinkListener):CommentsAdapter{
        this.commentsListener = commentsListener
        return this
    }


    inner class CommentsFirstViewHolder(private val binding:ItemCommentsBinding)
        :RecyclerView.ViewHolder(binding.root){
         val like = binding.like
         val content = binding.content
         val userHead= binding.userHead
         val userNickName = binding.userNickName
         val lineItem = binding.lineItem
         private lateinit var commentsMsg:CommentsMsg
         val imageSize = fragment.requireContext().resources.getDimensionPixelSize(R.dimen.head_size)
        val newImageSize = fragment.requireContext().resources.getDimensionPixelSize(R.dimen.action_pic_size)
        val marginEndSize = fragment.requireContext().resources.getDimensionPixelSize(R.dimen.lineItemMarginEnd)

         init {
              like.setOnClickListener {
                  checkLogin {
                      _viewModel.toAddLike(commentsMsg.userId, 1)
                  }
              }
              content.setOnClickListener {
                  openInputMethod()
                  commentsListener?.linkName(commentsMsg.userNickName)
                  //第二次赋值，更新当前评论的组id为该条评论id
                  _viewModel.toSetFriendCommentsInfo(transferInfo(commentsMsg))

              }
              userHead.setOnClickListener {
                dismiss()
                  friendFragment.setFriendId(commentsMsg.userId)
                friendFragment.show(fragmentManager,"CommentsAdapter")
              }
              userNickName.setOnClickListener {
                  dismiss()
                  friendFragment.setFriendId(commentsMsg.userId)
                  friendFragment.show(fragmentManager,"CommentsAdapter")
              }
              lineItem.setOnClickListener {
                  openInputMethod()
                  commentsListener?.linkName(commentsMsg.userNickName)
                      //第二次赋值，更新当前评论的组id为该条评论id
                  _viewModel.toSetFriendCommentsInfo(transferInfo(commentsMsg))

              }
         }

        fun bind(Msg: CommentsMsg){
              with(binding){
                  if(Msg.commentGroupId!=0) {
                      val secendLayoutParams = lineItem.layoutParams as ViewGroup.MarginLayoutParams
                     secendLayoutParams.leftMargin = imageSize
                      lineItem.layoutParams = secendLayoutParams
                      val imageLayout = userHead.layoutParams as ViewGroup.MarginLayoutParams
                      imageLayout.width = newImageSize
                      imageLayout.height = newImageSize
                      imageLayout.setMargins(10,0,10,20)
                      userHead.layoutParams = imageLayout
                  }
                  comments = Msg
                  viewModel =  _viewModel
                  commentsMsg = Msg
                  executePendingBindings()
              }
        }
   }





    fun transferInfo(commentsMsg: CommentsMsg):CommentsMsg{
        return CommentsMsg(
            id = commentsMsg.id,
            userId = commentsMsg.userId,
            userNickName = commentsMsg.userNickName,
            dynamicId =  commentsMsg.dynamicId,
            commentGroupId = commentsMsg.id)

    }
    fun checkLogin(doWorkOk:()->Unit){
        if(_viewModel.isLogin.value == false){
            NoticeLoginDialog().show(fragmentManager,"CommentsAdapter")

        }else{
            doWorkOk()
        }
    }

    fun openInputMethod(){
            (fragment as BaseDialogFragment<*>).openInputMethod()
    }

    fun closeInputMethod(){
        (fragment as BaseDialogFragment<*>).closeInputMethod()
    }

    fun getStatus() = (fragment as BaseDialogFragment<*>).getInputMethodStatus()


    fun dismiss(){
        (fragment as BaseDialogFragment<*>).dismiss()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comment = getItem(position)
      //  eee("执行了bind $comment")
        (holder as CommentsFirstViewHolder).bind(comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return  CommentsFirstViewHolder(ItemCommentsBinding.
           inflate(LayoutInflater.from(parent.context),parent,false)
       )
    }


}
/*
class CommentsAdapter(val _viewModel: PersonDynamicViewModel,
                      val fragment:Fragment
) : BaseRecyclerViewAdapter<CommentsMsg>() {

    private  lateinit var commentsListener:CommentsLinkListener

    interface CommentsLinkListener{
        fun linkName(name:String)
    }

    fun setCommentsLinkListener( commentsListener:CommentsLinkListener):CommentsAdapter{
        this.commentsListener = commentsListener
        return this
    }


    inner class CommentsReplyViewHolder(private val binding: CommentsBinding):RecyclerView.ViewHolder(binding.root){
    }


    inner class CommentsViewHolder(private val binding:ItemCommentsBinding
    )
        :BaseRecyclerViewHolder(binding){

        val like = binding.like
        val content = binding.content
        val userHead= binding.userHead
        val userNickName = binding.userNickName
        val lineItem = binding.lineItem
        private lateinit var commentsMsg:CommentsMsg

        init {
            like.setOnClickListener {
                openInputMethod()
                checkLogin { _viewModel.toAddLike(commentsMsg.user_id, 1) }
            }
            content.setOnClickListener {
                openInputMethod()
                commentsListener.linkName(commentsMsg.userNickName)
                checkLogin {
                    //第二次赋值，更新当前评论的组id为该条评论id
                    _viewModel.toSetFriendCommentsInfo(transferInfo(commentsMsg))
                }
            }
            userHead.setOnClickListener {
                dismiss()
                val bundle = bundleOf("userId" to commentsMsg.user_id)
                fragment.findNavController().navigate(R.id.action_HomePageFragment_to_list_to_video,bundle)
            }
            userNickName.setOnClickListener {
                dismiss()
                val bundle = bundleOf("userId" to commentsMsg.user_id)
                fragment.findNavController().navigate(R.id.action_HomePageFragment_to_list_to_video,bundle)
            }
            lineItem.setOnClickListener {
                openInputMethod()
                commentsListener.linkName(commentsMsg.userNickName)
                checkLogin {
                    //第二次赋值，更新当前评论的组id为该条评论id
                    _viewModel.toSetFriendCommentsInfo(transferInfo(commentsMsg))
                }
            }
        }

        fun bind(Msg: CommentsMsg){
            with(binding){
                comments = Msg
                viewModel =  _viewModel
                commentsMsg = Msg
                executePendingBindings()
            }
        }
    }

    fun transferInfo(commentsMsg: CommentsMsg):CommentsMsg{
        return CommentsMsg(
            id = commentsMsg.id,
            user_id = commentsMsg.user_id,
            userAccount = commentsMsg.userAccount,
            userNickName = commentsMsg.userNickName,
            dynamicId =  commentsMsg.dynamicId,
            authorUserId = commentsMsg.authorUserId,
            commentGroupId = commentsMsg.id

        )

    }
    fun checkLogin(doWorkOk:()->Unit){
        if(_viewModel.isLogin.value == false){
            dismiss()
            fragment.findNavController().navigate(R.id.login_and_register)
        }else{
            doWorkOk()
        }
    }

    fun openInputMethod(){
        (fragment as CommentsDialog).openInputMethod()
    }

    fun closeInputMethod(){
        (fragment as CommentsDialog).closeInputMethod()
    }

    fun getStatus() = (fragment as CommentsDialog).getInputMethodStatus()


    fun dismiss(){
        (fragment as CommentsDialog).dismiss()
    }

    override fun createItem(parent: ViewGroup?, viewType: Int):CommentsViewHolder  {
        return CommentsViewHolder(ItemCommentsBinding.inflate(
            LayoutInflater.from(parent?.context),parent,false)
        )
    }

    override fun bindData(holder: BaseRecyclerViewHolder?, position: Int) {
        val comment = getItemData(position)
        if (comment != null) {
            (holder as CommentsViewHolder).bind(comment)
        }
    }


    /*  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
          // eee("执行了bind onCreateViewHolder")

          return  CommentsNoReplyViewHolder(ItemCommentsBinding.
              inflate(LayoutInflater.from(parent.context),parent,false)
          )
      }*/
}*/



