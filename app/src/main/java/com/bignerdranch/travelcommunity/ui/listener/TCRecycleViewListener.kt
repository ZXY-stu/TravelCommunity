package com.bignerdranch.travelcommunity.ui.listener

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.travelcommunity.base.BaseViewModel
import com.bignerdranch.travelcommunity.data.repository.BaseRepository
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.util.LogUtil

/**
 * @author zhongxinyu
 * @date 2020/4/8
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class TCRecycleViewListener: RecyclerView.OnScrollListener() {

    private lateinit var _dynamicViewModel:PersonDynamicViewModel
    private var firstCount = 2   //提前加载一个，避免不能上滑刷新
   fun setViewModel(viewModel: PersonDynamicViewModel):TCRecycleViewListener{
       _dynamicViewModel = viewModel
        return this
   }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val l = recyclerView.layoutManager as LinearLayoutManager
        val lastItemCount = l.findLastVisibleItemPosition()
        val totalItemCount = l.itemCount


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