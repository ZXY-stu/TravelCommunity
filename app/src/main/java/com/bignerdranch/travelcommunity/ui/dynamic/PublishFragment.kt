package com.bignerdranch.travelcommunity.ui.dynamic

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe

import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.databinding.FragmentPublishBinding
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.user.UserViewModel
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.travelcommunity.base.BaseDialogFragment
import com.bignerdranch.travelcommunity.base.BaseViewModel
import com.bignerdranch.travelcommunity.ui.adapters.ChoosePictureAdapter
import com.bignerdranch.travelcommunity.ui.utils.PermissionAsk
import com.bignerdranch.travelcommunity.ui.utils.Utils
import com.bignerdranch.travelcommunity.ui.utils.getUriFromDrawableRes
import com.bignerdranch.travelcommunity.ui.utils.openAlbum
import com.bignerdranch.travelcommunity.util.ToastUtil
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.my_toolbar.view.*


const val   OPEN_ALBUM = 2
const val   takePhoto = 1


 class PublishFragment : BaseDialogFragment<FragmentPublishBinding>() {

    override val layoutId: Int = R.layout.fragment_publish
    override val needLogin: Boolean = true
    override val themeResId: Int = R.style.DialogFullScreen_Bottom

    private  val _dynamicViewModel  by activityViewModels<PersonDynamicViewModel> {
        InjectorUtils.personDynamicViewModelFactory(requireContext())
    }

    private val _userViewModel by activityViewModels<UserViewModel>{
        InjectorUtils.userViewModelFactory(requireContext())
    }

    private  var fileList = ArrayList<Uri>()

    private var lastView:View? = null
    private lateinit var adapter:ChoosePictureAdapter
    var commentsContent = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

             super.onCreateView(inflater, container, savedInstanceState)
             return binding.root

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            OPEN_ALBUM ->{
                if(resultCode == Activity.RESULT_OK && data !=null){
                    if(fileList.size>0){
                        fileList.removeAt(fileList.lastIndex)

                    }
                    fileList.addAll(Matisse.obtainResult(data))
                    eee("fileList$fileList")
                    if(fileList.size<9 && !fileList[0].toString().contains("video"))
                    fileList.add(getUriFromDrawableRes(requireContext(),R.drawable.add_dynamic))
                    adapter.submitList(fileList)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        eee("open Album1")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        eee("open Album2")
         when(requestCode){
           OPEN_ALBUM -> {
                  if(grantResults.isNotEmpty() &&  grantResults[0] == PackageManager.PERMISSION_GRANTED){
                      openAlbum(OPEN_ALBUM)
                      eee("open Album")
                  }else{
                      ToastUtil.show("You denied the permission")
                  }
           }
       }
    }

   private fun openAlbum(requestCode:Int){
       Utils.build(this).openAlbum(requestCode)
   }

     override fun subscribeUi() {

         adapter = ChoosePictureAdapter(requireActivity(),requireActivity().supportFragmentManager)
         binding.dynamic.adapter = adapter
         binding.dynamicViewModel = _dynamicViewModel
         binding.userViewModel = _userViewModel
         adapter.setItemClickListener(object :ChoosePictureAdapter.ItemClickListener{
             override fun onItemClick(position: Int) {
                 PermissionAsk.checkPermission(requireActivity(),
                     android.Manifest.permission.READ_EXTERNAL_STORAGE, OPEN_ALBUM){
                     openAlbum(OPEN_ALBUM)
                 }
             }
         })
         adapter.setOnItemChangedListener(object :ImageEditorDialog.OnItemChangedListener{
             override fun itemChanged(position: Int) {
                 fileList?.removeAt(position)
                 adapter.submitList(fileList)
                 adapter.notifyDataSetChanged()
             }
         })

        if(fileList.isEmpty())
            fileList.add(getUriFromDrawableRes(requireContext(),R.drawable.add_dynamic))

         adapter.submitList(fileList)

         with(binding.publishToolbar.publicToolbar) {
             setNavigationOnClickListener {
                 dismiss()
             }
             titleContent.text = "发布"
             with(your_action) {
                 visibility = View.VISIBLE
                 text = "发布"
                 setOnClickListener {
                     if(fileList.size>1) fileList.removeAt(fileList.lastIndex)
                     _dynamicViewModel.contentsArgs.putAll(Utils.getUploadRequestMap(fileList,requireContext(),commentsContent))
                     _dynamicViewModel.toAddDynamic()
                     dismiss()
                 }
             }
         }


         /*   binding.add_dynamic.setOnClickListener {
                PermissionAsk.checkPermission(requireActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE, OPEN_ALBUM){
                    openAlbum(OPEN_ALBUM)
                }
            }*/

         binding.setPermission.setOnClickListener {
             PrivateFragment(_dynamicViewModel).show(requireActivity().supportFragmentManager,"")
         }
     }

     override fun subscribeListener() {

     }

     override fun subscribeObserver() {
         with(_dynamicViewModel){
             textContent.observe(viewLifecycleOwner){
                 commentsContent = it
             }


         }

     }


 }




