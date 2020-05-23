package com.bignerdranch.travelcommunity.task

/**
 * @author zhongxinyu
 * @date 2020/5/22
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/


/**
 * @author zhongxinyu
 * @date 2020/5/22
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
/*
class RenderImage(val execute:ExecutorService,
                  val infoList:List<ImageInfo>):Runnable{

    var width = 300
    var height = 500
    private var recycle = ArrayList<ImageData>()
    private val completionService =  ExecutorCompletionService<ImageData>(execute)

    fun setSize(width: Int,height: Int):RenderImage{
        this.width = width
        this.height = height
        return this
    }

    override fun run() {
        if(recycle.isEmpty())  loadOnLine()
        else loadLocal()
    }

    fun loadLocal(){
        recycle.forEach {
            it.imageView.ktxRunOnUi {
                setImageBitmap(it.bitmap)
            }
        }
    }

    fun loadOnLine(){
        infoList.forEach {
            completionService.submit {
                it.prepareImage(width, height)
            }
        }
        try {
            repeat(infoList.size){
                val take = completionService.take()
                val imageData = take.get()
                imageData.imageView.ktxRunOnUi {
                    setImageBitmap(imageData.bitmap)
                }
                recycle.add(imageData)
            }
        } catch (interrupt: InterruptedException) {
            Thread.currentThread().interrupt()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
    }
}

class ImageInfo(val url:String,val imageView: ImageView){

    fun prepareImage(width: Int,height: Int):ImageData{
        val bitmap = createVideoThumbnail(url,width,height)
        return ImageData(imageView,bitmap)
    }

    fun createVideoThumbnail(videoUrl: String, width: Int, height: Int): Bitmap? {
        var bitmap: Bitmap? = null

        val retriever = MediaMetadataRetriever()
        val kind = MediaStore.Video.Thumbnails.MINI_KIND
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(videoUrl, HashMap())
            } else {
                retriever.setDataSource(videoUrl)
            }
            bitmap = retriever.frameAtTime
        } catch (ex: java.lang.IllegalArgumentException) { // Assume this is a corrupt video file
            ex.printStackTrace()
        } catch (ex: RuntimeException) { // Assume this is a corrupt video file.
            ex.printStackTrace()
        } finally {
            try {
                retriever.release()
            } catch (ex: RuntimeException) { // Ignore failures while cleaning up.
                ex.printStackTrace()
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(
                bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT
            )
        }
        LogUtil.eee("bitmapsss$bitmap")
        return bitmap

    }
}


data class ImageData(val imageView:ImageView, val bitmap: Bitmap?)
*/