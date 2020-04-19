package com.bignerdranch.travelcommunity.ui.listener

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.util.LogUtil

/**
 * @author zhongxinyu
 * @date 2020/4/8
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class TCRecycleViewListener(): RecyclerView.OnScrollListener() {


    private var lastIndex = 0
    private var hasNextPage = false
    private var currentPage = 1
    private lateinit var _dynamicViewModel: PersonDynamicViewModel
    private lateinit var _lifecycleOwner:LifecycleOwner
    private  var _StateChangedCommand:()->Unit = {}
    private  var _onScrolledCommand:()->Unit = {}

    constructor(StateChangedCommand:()->Unit):this() {
        _StateChangedCommand = StateChangedCommand
    }


    constructor(StateChangedCommand:()->Unit,
                onScrolledCommand:()->Unit) : this(StateChangedCommand) {
        _onScrolledCommand = onScrolledCommand
    }

   fun setViewModel(viewModel: PersonDynamicViewModel):TCRecycleViewListener{
       _dynamicViewModel = viewModel
        return this
   }



    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        _StateChangedCommand()
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

         val l = recyclerView.layoutManager as LinearLayoutManager
         val lastItemCount = l.findLastVisibleItemPosition()
         val totalItemCount = l.itemCount


          if(lastItemCount == totalItemCount-1) {
           //   _dynamicViewModel.isLoading.value = true

          }


        _onScrolledCommand()
    }




}