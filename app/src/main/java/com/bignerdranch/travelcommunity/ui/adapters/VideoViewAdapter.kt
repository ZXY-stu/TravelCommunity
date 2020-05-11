package com.bignerdranch.travelcommunity.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.tclib.LogUtil
import com.bignerdranch.tclib.LogUtil.eee
import com.bignerdranch.tclib.data.db.entity.CommentsMsg
import com.bignerdranch.tclib.data.db.entity.PersonDynamic
import com.bignerdranch.tclib.utils.DeviceUtils
import com.bignerdranch.travelcommunity.R
import com.bignerdranch.travelcommunity.databinding.ItemRecyclerviewVideoLayoutBinding
import com.bignerdranch.travelcommunity.tcvideoplayer.TCPlayer
import com.bignerdranch.travelcommunity.tcvideoplayer.TCPlayer.OnNetChangeListener
import com.bignerdranch.travelcommunity.ui.dynamic.CommentsDialog
import com.bignerdranch.travelcommunity.ui.dynamic.viewModels.PersonDynamicViewModel
import com.bignerdranch.travelcommunity.ui.user.FriendFragment
import com.bignerdranch.travelcommunity.util.ToastUtil.show


class VideoViewAdapter(val context: Context,
                       val fragmentManager: FragmentManager,
                       val _viewModel:PersonDynamicViewModel
): ListAdapter<PersonDynamic, RecyclerView.ViewHolder>(
Diff()
) {
    var mHeight = 0
    private var playPosition = 0
    private var progress = 0
    private var currentPage = 0



    fun setPlayPosition(position: Int): VideoViewAdapter {
        playPosition = position
        return this
    }


    fun setCurrentPage(page:Int):VideoViewAdapter{
        currentPage = page
        return this
    }

    fun setProgress(progress: Int): VideoViewAdapter {
        this.progress = progress
        return this
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VideoViewHolder(
            ItemRecyclerviewVideoLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        (holder as VideoViewHolder).update(position,data)
    }



    inner class VideoViewHolder(private val  binding: ItemRecyclerviewVideoLayoutBinding)
        : RecyclerView.ViewHolder(binding.root){

        private val playerLayout: ConstraintLayout = binding.layoutControl
        val  tcPlayer = binding.adapterSuperVideo
        val cover  = binding.videoCover
        val progressBar  = binding.progressBar
        val like = binding.like
        val comments = binding.comments
        private val headProfile = binding.headUrl
        private val addFocus = binding.addFocus
        val share =  binding.share
        private var mPosition = 0
        private var url: String? = null
        private var netChange = false
        private lateinit  var personDynamic: PersonDynamic
        private  val commentsDialog = CommentsDialog(_viewModel)



        init {
            val layoutParams = playerLayout.layoutParams

            layoutParams.height = (DeviceUtils.deviceHeight(context)-30*2).toInt()
            playerLayout.layoutParams = layoutParams

            val  onNetChangeListener =  object : OnNetChangeListener {
                override fun onWifi() {
                    if (netChange && mPosition == playPosition) {
                        tcPlayer.toPrepare(url)
                        play(progress)
                    }
                    netChange = false
                }

                override fun onMobile() {
                    if (netChange && mPosition == playPosition) {
                        tcPlayer.toPrepare(url)
                        play(tcPlayer.currentPosition)
                    }

                    netChange = false
                }

                override fun onDisConnect() {

                }

                override fun onNoAvailable() {
                    netChange = true

                }
            }

            val onProgressChangedListener = object :TCPlayer.OnProgressChangedListener{
                override fun progressDuration(duration: Int) {
                }

                override fun progressPosition(progress: Int) {
                    progressBar.progress = progress
                }
            }


            headProfile.setOnClickListener {
                 onDestroy()
                val friendFragment1 =  FriendFragment(_viewModel = _viewModel)
                friendFragment1.setFriendId(personDynamic.userId)
                friendFragment1.show(fragmentManager,"")
            }

            comments.setOnClickListener {
             //   show("去评论")

                commentsDialog.arguments = bundleOf(
                    "currentPage" to currentPage,
                    "dynamicId" to personDynamic.id,
                    "authorUserId" to personDynamic.userId,
                     "friendAccount" to personDynamic.account
                )

                commentsDialog.show(fragmentManager.beginTransaction(),"commentsDialog")
            }

            share.setOnClickListener {
                show("share")
                 checkLogin(it){

                 }
            }

            like.setOnClickListener  {
                show("like")
                 checkLogin(it){
                     _viewModel.toAddLike(personDynamic.id,0)
                 }
            }

            addFocus.setOnClickListener {
                show("add foucs")
                checkLogin(it) {
                    _viewModel.toAddFriend(personDynamic.userId)
                }
            }

            tcPlayer.setOnProgressChangedListener(onProgressChangedListener)
                .setOnNetChangeListener(onNetChangeListener)
                .setDoubleClickListener {  }
                .onPrepared { if(mPosition == playPosition) play(progress) }
                .onComplete { play(0) }
                .setNetChangeListener(true)
                .setLive(false)
                .setSupportGesture(false).setShowTopControl(false)

        }



        fun play(progress: Int) {
            tcPlayer.toStart(progress)
        }

        fun pause() {
            tcPlayer.pauseLater()
        }

        fun release() {
            tcPlayer.release()
        }

        fun onResume() {
            tcPlayer.onResume()
        }

        fun currentProgress(): Int {
            return tcPlayer.currentPosition
        }

        fun onDestroy() {
            release()
            tcPlayer.onDestroy()
        }

        fun update(position: Int,personDynamic: PersonDynamic) {
            mPosition = position
            this.personDynamic  = personDynamic
            binding.personDynamic = personDynamic
            url = personDynamic.videoUrl
            tcPlayer.url = url
            tcPlayer.toPrepare(url)
            binding.executePendingBindings()
        }

        fun checkLogin(view: View, doWorkOk:()->Unit){
            if(_viewModel.isLogin.value == false){
                view.findNavController().navigate(R.id.login_and_register)
            }else{
                doWorkOk()
            }
        }

    }

    class Diff : DiffUtil.ItemCallback<PersonDynamic>(){
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


/*
class VideoViewAdapter(val context:Context): BaseRecyclerViewAdapter<String>() {
    var mHeight = 0
    private var playPosition = 0
    private var progress = 0


    fun setPlayPosition(position: Int): VideoViewAdapter {
        playPosition = position
        return this
    }

    fun setProgress(progress: Int): VideoViewAdapter {
        this.progress = progress
        return this
    }

    override fun bindData(holder: BaseRecyclerViewHolder?, position: Int) {
         val  data = getItemData(position)
        LogUtil.ee("aa 又创建了 $position")
        (holder as VideoViewHolder).update(position,data)
    }

    override fun createItem(parent: ViewGroup?, viewType: Int): BaseRecyclerViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.item_recyclerview_video_layout,null)
        LogUtil.ee("aa bb yya 又创建了 ")
        return VideoViewHolder(view)
    }





    inner class VideoViewHolder(view: View)
       : BaseRecyclerViewHolder(view) {

        lateinit var playerControlLayout: RelativeLayout
        var playerLayout: ConstraintLayout = itemView.findViewById(R.id.layoutControl)
        private  var tcPlayer: TCPlayer = itemView.findViewById(R.id.adapter_super_video)
        lateinit var cover: ImageView
        var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        private var mPosition = 0
        private var url: String? = null
        private var netChange = false
        private var hasCreated = false
  //  下50dp  上30dp 80*1.5
        init {


            val layoutParams = playerLayout.layoutParams
            layoutParams.height = (DeviceUtils.deviceHeight(context)-30*2).toInt()
            playerLayout.layoutParams = layoutParams

            val  onNetChangeListener =  object : OnNetChangeListener {
               override fun onWifi() {
                   if (netChange && mPosition == playPosition) {
                       tcPlayer.toPrepare(url)
                       play(progress)
                   }
                   netChange = false
               }

               override fun onMobile() {
                   if (netChange && mPosition == playPosition) {
                       tcPlayer.toPrepare(url)
                       play(tcPlayer.currentPosition)
                   }

                   netChange = false
               }

               override fun onDisConnect() {

               }

               override fun onNoAvailable() {
                   netChange = true

               }
           }

       val onProgressChangedListener = object :TCPlayer.OnProgressChangedListener{
          override fun progressDuration(duration: Int) {
              LogUtil.eee("duration $duration")
          }

          override fun progressPosition(progress: Int) {
              LogUtil.eee("progress eeeee$progress")
              progressBar.progress = progress

          }
      }



        tcPlayer.setOnProgressChangedListener(onProgressChangedListener)
            .setOnNetChangeListener(onNetChangeListener)
            .setDoubleClickListener {  }
            .onPrepared { if(mPosition == playPosition) play(progress) }
            .onComplete { play(0) }
            .setNetChangeListener(true)
            .setLive(false)
            .setSupportGesture(false).setShowTopControl(false)

  }

        fun play(progress: Int) {
            tcPlayer.toStart(progress)
        }

        fun pause() {
            tcPlayer.pauseLater()
        }

        fun release() {
            tcPlayer.release()
        }

        fun onResume() {
            tcPlayer.onResume()
        }

        fun currentProgress(): Int {
            return tcPlayer.currentPosition
        }

        fun onDestroy() {
            release()
            tcPlayer.onDestroy()
        }

        fun update(position: Int,url:String) {
                mPosition = position
                this.url = url
                tcPlayer.url = url
                tcPlayer.toPrepare(url)
        }

         override fun getView(): View {
             return itemView
         }
    }




}
*/

