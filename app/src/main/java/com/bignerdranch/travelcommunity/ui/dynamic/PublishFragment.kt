package com.bignerdranch.travelcommunity.ui.dynamic

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.FragmentPublishBinding
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.user.UserViewModel
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.ui.clearAndToHome
import com.bignerdranch.travelcommunity.ui.utils.PermissionAsk
import com.bignerdranch.travelcommunity.ui.utils.Utils
import com.bignerdranch.travelcommunity.ui.utils.openAlbum
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.my_toolbar.view.*


const val OPEN_ALBUM = 2
const val   takePhoto = 1

 class PublishFragment : BaseDialogFragment<FragmentPublishBinding>() {

    override val layoutId: Int = R.layout.fragment_publish
    override val needLogin: Boolean = true
     override val themeResId: Int = R.style.DialogFullScreen
     override val windowHeight: Double = 3.0

    private  val _dynamicViewModel  by activityViewModels<PersonDynamicViewModel> {
          InjectorUtils.personDynamicViewModelFactory(requireContext())
    }

    private val _userViewModel by activityViewModels<UserViewModel>{
        InjectorUtils.userViewModelFactory(requireContext())
    }
    private lateinit var imageList: MutableList<Uri>

    private var lastView:View? = null
     private var hasCreated = false
      private  var hasViewCreated = false

     var commentsContent = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

          super.onCreateView(inflater, container, savedInstanceState)
          eee("onCreateView PublishFragment")
          binding.dynamicViewModel = _dynamicViewModel
          binding.userViewModel = _userViewModel
          initComponent()   //初始化控件
          lastView = binding.root

        return lastView
    }



     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


             super.onViewCreated(view, savedInstanceState)
              subscribeObserve()


     }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LogUtil.e("我来了")
        when(requestCode){
            OPEN_ALBUM ->{
                if(resultCode == Activity.RESULT_OK && data !=null){
                    imageList = Matisse.obtainResult(data)
                 /*   data.data?.let {
                        ToastUtil.test(it.toString()+_dynamicViewModel.textContent.value)
                        val bitmap = getBitMapFromUri(it)
                        _dynamicViewModel.contentsArgs.putAll(getUploadRequestMap(listOf(it)))
                        cover_img.setImageBitmap(bitmap)
                    }*/
                }
            }
        }
    }




    private fun subscribeObserve(){
        with(_dynamicViewModel){
            textContent.observe(viewLifecycleOwner){
                commentsContent = it
            }
        }

    }

    private  fun   initComponent() {

        with(binding.publishToolbar.publicToolbar) {
            setNavigationOnClickListener {
               dismiss()
            }

            titleContent.text = "发布"
            with(your_action) {
                visibility = View.VISIBLE
                text = "发布"
                setOnClickListener {
                    _dynamicViewModel.contentsArgs.putAll(Utils.getUploadRequestMap(
                        imageList,requireContext(),commentsContent))
                    _dynamicViewModel.toAddDynamic(imageList[0],requireContext())
                    imageList.clear()
                   // clearAndToHome(requireContext())
                   dismiss()
                }
            }
        }


        binding.chooseCover.setOnClickListener {
            PermissionAsk.checkPermission(requireActivity(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE, OPEN_ALBUM){
                openAlbum(OPEN_ALBUM)
            }
        }

        binding.setPermission.setOnClickListener {
             PrivateFragment().show(requireActivity().supportFragmentManager,"")
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         when(requestCode){
           OPEN_ALBUM -> {
                  if(grantResults.isNotEmpty() &&  grantResults[0] == PackageManager.PERMISSION_GRANTED){
                      openAlbum(OPEN_ALBUM)
                  }else{
                      ToastUtil.show("You denied the permission")
                  }
           }
       }

    }

   private fun openAlbum(requestCode:Int){
       Utils.build(this).openAlbum(requestCode)
   }



}




