package com.bignerdranch.travelcommunity.ui.dynamic

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Base64DataException
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.data.db.entity.CommentsMsg
import com.bignerdranch.tclib.data.db.entity.FriendId
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.databinding.FragmentMessageBinding
import com.bignerdranch.travelcommunity.ui.adapters.CommentsAdapter
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.util.*
import java.util.*


/**
 * @author zhongxinyu
 * @date 2020/5/5
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 */
class CommentsDialog(
    val _viewModel: PersonDynamicViewModel,
    override val  themeResId: Int = R.style.MyAnimDialog,
    override val layoutId: Int = R.layout.fragment_message,
    override val needLogin: Boolean = false,
    override val windowHeight: Double = 0.8
) : BaseDialogFragment<FragmentMessageBinding>(){




    private lateinit var recyclerView: RecyclerView
    lateinit  var adapter: CommentsAdapter
    private lateinit  var commentsMsgList: MutableList<CommentsMsg>



    private var  friendNickName =""

    private var currentPage = 0
    private var currentPosition = 0
    private lateinit var inputMethodManager: InputMethodManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 预先设置Dialog的一些属性
        setStyle(STYLE_NO_TITLE,themeResId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        initView()
        subscribeObserver()
        return binding.root
    }



    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        binding.editTextContent.setText("")
    }


    private  fun subscribeObserver(){
           with(_viewModel) {
               commentsMsgLocal.observe(viewLifecycleOwner){
                   eee("data"+it)
                   if(it.isNotEmpty())
                   adapter.submitList(it.asReversed())
                 //  adapter.notifyItemInserted(0)
                //   adapter.notifyItemInserted(0)
               }
           }
    }




      private fun initView(){

         setFirstFriendInfo(_viewModel)

         adapter = CommentsAdapter(_viewModel,this)
          with(binding) {
              recyclerView = messageRecycleView
              recyclerView.adapter = adapter
              viewModel = _viewModel
              close.setOnClickListener {
                  dismiss()
              }

              editTextContent.setOnFocusChangeListener { v, hasFocus ->
                  if (hasFocus) {
                      if (_viewModel.isLogin.value == false) {
                          dismiss()
                          findNavController().navigate(R.id.login_and_register)
                      }
                  }
              }

              adapter.setCommentsLinkListener(object :CommentsAdapter.CommentsLinkListener{
                  override fun linkName(name: String) {
                      with(editTextContent){
                       /*   hint = (HtmlTextStyleUtil.getInstance()
                              .setColor(Color.GRAY.toString())
                              .setFontSize("15")
                              .setText("@$name ")
                              .buildStyle(2))
                          requestFocus()  //获取到焦点
                          setSelection(getText().length); */ //光标移动到最后
                          setText("")
                          hint = "回复 @$name:"
                          requestFocus()
                      }
                  }
              })

              submit.setOnClickListener {
                  closeInputMethod()
                  _viewModel.toAddComments()
              //    editTextContent.setText("")
              }
          }
     }




}