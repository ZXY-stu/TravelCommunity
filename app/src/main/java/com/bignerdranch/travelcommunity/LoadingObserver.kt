package com.bignerdranch.travelcommunity

/**
 * @author zhongxinyu
 * @date 2020/4/9
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
import android.content.Context
import androidx.lifecycle.Observer
import com.bignerdranch.tclib.LogUtil
import com.kaopiz.kprogresshud.KProgressHUD



class LoadingObserver(context: Context) : Observer<Boolean> {
    private val dialog = KProgressHUD(context)
        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        .setCancellable(false)
        .setAnimationSpeed(2)
        .setDimAmount(0.5f)

    override fun onChanged(show: Boolean?) {
        if (show == null) return
        if (show) {
            dialog.show()
            LogUtil.e("show")
        } else {
            dialog.dismiss()
            LogUtil.e("hide")
        }
    }
}