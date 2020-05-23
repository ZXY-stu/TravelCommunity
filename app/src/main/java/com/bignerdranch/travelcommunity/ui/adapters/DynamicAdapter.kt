package com.bignerdranch.travelcommunity.ui.adapters

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.LogUtil.eeee
import com.bignerdranch.tclib.LogUtil.eeeee
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.adapters.Coverters
import com.bignerdranch.travelcommunity.base.BaseViewModel
import com.bignerdranch.travelcommunity.databinding.ItemDynamicBinding
import com.bignerdranch.travelcommunity.task.TaskServer
import com.bignerdranch.travelcommunity.task.RenderImage
import com.bignerdranch.travelcommunity.ui.dynamic.ImageDynamicFragment
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.user.FriendFragment
import com.bignerdranch.travelcommunity.ui.user.NoticeLoginDialog
import com.bignerdranch.travelcommunity.util.GlideUtil
import java.util.concurrent.Executors

/**
 * @author zhongxinyu
 * @date 2020/4/5
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class DynamicAdapter(val viewModel: PersonDynamicViewModel,
                     val framentManage:FragmentManager,
                     val context: Context
):ListAdapter<PersonDynamic,RecyclerView.ViewHolder>(PersonDynamicDiff()) {
    var bitmap:Bitmap? = null
    private val TAG = "DynamicAdapter"

    private val executor = Executors.newFixedThreadPool(2)

    private  var render:RenderImage

    companion object{
        val map = HashMap<String,DynamicViewHolder>()
        var curPersonDynamic:PersonDynamic? = null
    } 

    val limit = 10


    private var listener:RenderImage.OnItemCompletedListener? = null


    fun diffImageList():MutableList<PersonDynamic>?{
        val curSize = currentList.size
        if(BaseViewModel.perImageSize ==0){
            BaseViewModel.perImageSize = curSize
            return currentList
        }else{
            val diffSize  = curSize - BaseViewModel.perImageSize
            BaseViewModel.perImageSize = curSize
            if(diffSize>0) {
                return currentList.subList(curSize - diffSize - 1,curSize-1)
            }else  return  null
        }
    }

    fun diffVideoList():MutableList<PersonDynamic>?{
        val curSize = currentList.size
        if(BaseViewModel.preVideoSize ==0){
            BaseViewModel.preVideoSize = curSize
            return currentList
        }else{
            val diffSize  = curSize - BaseViewModel.preVideoSize
            BaseViewModel.preVideoSize = curSize
            if(diffSize>0) {
                return currentList.subList(curSize - diffSize - 1,curSize-1)
            }else  return  null
        }
    }

    private fun preLoadVideo(){
        val curList = diffVideoList() ?: return
        val urls =  curList.map {
             Coverters.getVideoUrl(it.videoUrl)
        }.filter {
                it.length > 3
        }
        eeeee(" preLoad urls video  $urls")
        render.setList(urls)
        executor.submit {
            render.preload()
        }

    }

    private fun preLoadImage(){
        val curList = diffImageList() ?: return
        curList.filter {
            (""+it.imageUrls).length>3
        }.forEach {
            preLoadImage(it)
        }

        eeeee(" preLoad urls image  ${curList.map { it.imageUrls }}")

    }

    init {
        render = RenderImage(executor,context).setListener(object :RenderImage.OnItemCompletedListener{
            override fun itemCompleted(url:String,bitmap: Bitmap) {
                eeee("update $url")
                eeee("map $map")
                TaskServer.ktxRunOnUi {
                    map[url]?.update(url,bitmap)
                }
            }
        })
    }




    inner class  DynamicViewHolder(private val binding: ItemDynamicBinding)
        :RecyclerView.ViewHolder(binding.root){
         var   mPersonDynamic:PersonDynamic? = null
        var isLike = false
        var haveLoad = false
        val coverView = binding.imageUrl

        init {
            with(binding){


                 dynamicLayout.setOnClickListener {
                     view->
                     var showVideo = false
                     mPersonDynamic?.videoUrl?.let {
                         if(it.length>4){
                              curPersonDynamic = mPersonDynamic
                              view.findNavController().navigate(R.id.action_HomePageFragment_to_videoDynamicFragment)
                             showVideo = true
                         }
                     }
                   if(!showVideo) ImageDynamicFragment(_viewModel = viewModel,mPersonDynamic = mPersonDynamic!!,
                       activity = context as Activity).show(framentManage,TAG)
                 }


                 headUrl.setOnClickListener {
                     val friendFragment1 =  FriendFragment(_viewModel = viewModel)
                     friendFragment1.setFriendId(mPersonDynamic!!.userId)
                     viewModel.toQueryFriendById(mPersonDynamic!!.userId)
                     friendFragment1.show(framentManage,TAG)
                 }

                 like.setOnClickListener {
                     checkLogin {
                         if (!isLike) {
                             like.setBackgroundResource(R.drawable.like_active)
                             viewModel.toAddLike(mPersonDynamic!!.id, 0)
                             isLike = true
                         } else {
                             isLike = false
                             like.setBackgroundResource(R.drawable.like)
                             viewModel.toDeleteLike(mPersonDynamic!!.id, 0)
                         }
                     }
                 }

            }
        }

        fun bind(item:PersonDynamic,position:Int){
            with(binding){
                personDynamic = item
                mPersonDynamic = item

                eee(""+mPersonDynamic?.videoUrl + "position$position")


                executePendingBindings()
            }
        }

        fun update(url:String,bitmap: Bitmap){
            eeee("update $url")
            coverView.setImageBitmap(bitmap)
        }


    }



    fun preLoadVideoImage(personDynamic: PersonDynamic,imageView: ImageView){
        val videoUrl = Coverters.getVideoUrl(personDynamic.videoUrl)

        videoUrl.let {
            if(it.length>4){

            }
        }
    }

    fun preLoadImage(personDynamic: PersonDynamic){
        val urls = Coverters.getUrlList(""+personDynamic.imageUrls)
        eeee("urls preLoadImage $urls")
        urls?.let {
            if(it.size>1)
            GlideUtil.preLoadImagesByUrl(
                context,it
            )
        }
        val headurl = Coverters.getImageUrl(personDynamic.headPortraitUrl)
       if(headurl.length>4){
           GlideUtil.preLoadImageByUrl(context,headurl)
       }



    }


    fun checkLogin(doWorkOk:()->Unit){
        if(viewModel.isLogin.value == false){
            NoticeLoginDialog().show(framentManage,"CommentsAdapter")

        }else{
            doWorkOk()
        }
    }




    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dynamic = getItem(position)
        LogUtil.w("绑定")
        val view = holder as DynamicViewHolder
        val url =  Coverters.getVideoUrl(dynamic.videoUrl)

        if(url.length>3){
            preLoadVideo()
            eeeee("onBindViewHolder video url $url")
            map[url] = view
        }else{
            preLoadImage()
            eeeee("onBindViewHolder  image url $url")
        }





        view.bind(dynamic,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        eee("onCreateViewHolder $currentList")

        val view = DynamicViewHolder(ItemDynamicBinding.inflate(LayoutInflater.from(parent.context),parent,false))

        return  view
    }

    class PersonDynamicDiff :DiffUtil.ItemCallback<PersonDynamic>(){
        override fun areContentsTheSame(oldItem: PersonDynamic, newItem: PersonDynamic): Boolean {
            LogUtil.w("比较")
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: PersonDynamic, newItem: PersonDynamic): Boolean {
            LogUtil.w("比较")
            return oldItem.id == newItem.id
        }
    }

}