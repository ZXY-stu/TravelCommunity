package com.bignerdranch.travelcommunity.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bignerdranch.travelcommunity.ui.BaseRecyclerViewHolder
import java.util.*

abstract class BaseRecyclerViewAdapter<T> :
    RecyclerView.Adapter<BaseRecyclerViewHolder>() {

    private val mData = ArrayList<T>()

    fun setData(data: List<T>?) {
        mData.clear()
        if(data!=null) {
            mData.addAll(data)
            notifyItemInserted(0);//通知演示插入动画
            notifyItemRangeChanged(0,itemCount);//通知数据与界面重新绑定

        }
    }

    fun getItemData(position: Int): T? {
        var res: T? = null
        if (position < mData.size) {
            res = mData[position]
        }
        return res
    }

    fun insertData(item:T,position:Int){
        mData.add(item)


    }


    fun clearData() {
        mData.clear()
    }


    fun getRealPosition(holder: BaseRecyclerViewHolder): Int {
        return  holder.layoutPosition
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder {
        return   createItem(parent, viewType)
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder,
        position: Int
    ) {
        val pos = getRealPosition(holder)
        val data = mData[pos]
        bindData(holder, position)
    }

    override fun getItemCount() = mData.size

    /**
     * 创建item view
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract fun createItem(parent: ViewGroup?, viewType: Int): BaseRecyclerViewHolder

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    protected abstract fun bindData(holder: BaseRecyclerViewHolder?, position: Int)

}