package com.bignerdranch.travelcommunity.ui.dynamic

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.databinding.ImageEditorBinding
import com.bignerdranch.travelcommunity.ui.RecyclerViewForViewPage2
import com.bignerdranch.travelcommunity.ui.adapters.ChoosePictureAdapter
import com.bignerdranch.travelcommunity.ui.adapters.PicturePreviewAdapter
import com.bignerdranch.travelcommunity.ui.utils.VideoPageSnapHelper

class ImageEditorDialog(val activity: Activity,
                  private val imageList:MutableList<Uri>,
                  val position:Int)
    : BaseDialogFragment<ImageEditorBinding>(){

    override val layoutId: Int = R.layout.image_editor
    override val needLogin: Boolean = false
    override val themeResId: Int = R.style.DialogFullScreen_Right
    private val fileList = imageList.toMutableList()
   private lateinit var layoutManager:MyLayoutManager
    private var curPosition = 0
    private lateinit var adapter:PicturePreviewAdapter
   private lateinit var recyclerView:RecyclerViewForViewPage2

    private var onItemChangedListener:OnItemChangedListener? = null

    interface OnItemChangedListener{
       fun itemChanged(position: Int)
    }

    fun setOnItemChangedListener(onItemChangedListener: OnItemChangedListener):ImageEditorDialog{
        this.onItemChangedListener = onItemChangedListener
        return this
    }



    override fun subscribeUi() {
        recyclerView = binding.imagePreviewEditor
        layoutManager = MyLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        adapter  = PicturePreviewAdapter(activity,layoutManager)

        recyclerView.adapter = adapter
       recyclerView.setUnableScorllXListener(object :RecyclerViewForViewPage2.EnableScorllXListener{
            override fun enable(flag: Boolean) {
                layoutManager.setScrollX(flag)
            }
        })

      binding.number.text =  "${fileList.size}/${position+1}"

        recyclerView.scrollToPosition(position)
      VideoPageSnapHelper().attachToRecyclerView(recyclerView).setScrollPlayListener {
          curPosition = it
          binding.number.text = "${fileList.size}/${curPosition+1}"
      }

        binding.delete.setOnClickListener {
            onItemChangedListener?.itemChanged(curPosition)
            fileList.removeAt(curPosition)
            if(fileList.size==0) dismiss()
            adapter.submitList(fileList)
            adapter.notifyItemRemoved(curPosition)
            binding.number.text = "${fileList.size}/${curPosition+1}"
        }

        binding.toolbar.setNavigationOnClickListener { dismiss() }

        adapter.submitList(fileList)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        eee("position $position")

    }




}