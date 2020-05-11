package com.bignerdranch.travelcommunity.ui.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.tclib.LogUtil

/**
 * @author zhongxinyu
 * @date 2020/4/8
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class HomePageViewListener: RecyclerView.OnScrollListener() {

    private lateinit var _dynamicViewModel:PersonDynamicViewModel
    private var firstCount = 50 //提前加载一个，避免不能上滑刷新

   fun setViewModel(viewModel: PersonDynamicViewModel):HomePageViewListener{
       _dynamicViewModel = viewModel
        return this
   }


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val l = recyclerView.layoutManager as LinearLayoutManager

        val lastItemCount = l.findLastVisibleItemPosition()
        val totalItemCount = l.itemCount


        LogUtil.e("la  ss  $lastItemCount")


        if (lastItemCount == totalItemCount - 1) {
            if (_dynamicViewModel.refreshing.value == false || firstCount>0) {
                if(firstCount>0)
                    firstCount--
                LogUtil.e("23")
                _dynamicViewModel.refreshing.value = true
                _dynamicViewModel.loading.value = true
            }
        } else {
            _dynamicViewModel.refreshing.value = false
        }
    }



}