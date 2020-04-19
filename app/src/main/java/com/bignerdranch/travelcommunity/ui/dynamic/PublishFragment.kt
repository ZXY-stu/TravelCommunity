package com.bignerdranch.travelcommunity.ui.dynamic

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.get

import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.base.BaseFragment
import com.bignerdranch.travelcommunity.databinding.FragmentPublishBinding
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.user.UserViewModel
import com.bignerdranch.travelcommunity.util.InjectorUtils
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.fragment_publish.*
import kotlinx.android.synthetic.main.my_toolbar.*
import kotlinx.android.synthetic.main.my_toolbar.view.*
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PublishFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PublishFragment :BaseFragment<FragmentPublishBinding>() {

    override val layoutId: Int = R.layout.fragment_publish

    private  val _dynamicViewModel  by activityViewModels<PersonDynamicViewModel> {
          InjectorUtils.personDynamicViewModelFactory(requireContext())
    }

    private val _userViewModel by activityViewModels<UserViewModel>{
        InjectorUtils.userViewModelFactory(requireContext())
    }

   private lateinit var  imageUrl: Uri
    private lateinit var outputImage: File
    private val takePhoto = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         super.onCreateView(inflater, container, savedInstanceState)
          binding.dynamicViewModel = _dynamicViewModel
          binding.userViewModel = _userViewModel
          subscribeObserve()
          initComponent()   //初始化控件
          return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            takePhoto->{
                if(resultCode == Activity.RESULT_OK){
                    val bitmap = BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(imageUrl))

                    cover_img.setImageBitmap(rotateIfRequired(bitmap))
                }
            }
            2 ->{
                if(resultCode == Activity.RESULT_OK && data !=null){
                    data.data?.let {
                        val bitmap = getBitMapFromUri(it)
                        cover_img.setImageBitmap(bitmap)
                    }
                }

            }
        }
    }

    private fun getBitMapFromUri(uri:Uri) = requireContext().contentResolver.openFileDescriptor(uri,"r")
        ?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }
    private  fun rotateIfRequired(bitmap: Bitmap):Bitmap{
        val exif = ExifInterface(outputImage.path)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL)
        return when(orientation){
            ExifInterface.ORIENTATION_ROTATE_90 ->  rotateBitMap(bitmap,90)
            ExifInterface.ORIENTATION_ROTATE_180 ->  rotateBitMap(bitmap,180)
            ExifInterface.ORIENTATION_ROTATE_270 ->  rotateBitMap(bitmap,270)
            else -> bitmap
        }
    }

    private fun rotateBitMap(bitmap: Bitmap,degree: Int):Bitmap{
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.width,bitmap.height,matrix,true)
        bitmap.recycle()
        return  rotatedBitmap
    }

    private fun subscribeObserve(){

    }

    private  fun   initComponent() {

        with(binding.publishToolbar.public_toolbar) {
            setNavigationOnClickListener {
                findNavController().also {
                    it.popBackStack(R.id.action_global_HomePageFragment, false)
                }  //返回上个页面
            }
            titleContent.text = "发布"
            with(your_action) {
                visibility = View.VISIBLE
                text = "发布"
                setOnClickListener {
                    //click event
                    //openAlbum()


                }
            }
        }
        binding.chooseCover.setOnClickListener {
            //click event
          takePhoto(requireContext())
        }
    }

    override fun initImmersionBar() {

    }

     private fun  takePhoto(context: Context){
         outputImage = File(context.externalCacheDir, "out.jpg")
         if (outputImage.exists()) outputImage.delete()
         outputImage.createNewFile()
         imageUrl = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             FileProvider.getUriForFile(context, "com.bignerdranch.travelcommunity.ui.HomePageActivity", outputImage)
         } else {
             Uri.fromFile(outputImage)
         }
         val intent = Intent("android.media.action.IMAGE_CAPTURE")
         intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUrl)
         startActivityForResult(intent,takePhoto)
     }

     private fun openAlbum(){
         val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
         intent.addCategory(Intent.CATEGORY_OPENABLE)
         intent.type = "image/*"
         startActivityForResult(intent,2)
     }


}
