package com.bignerdranch.travelcommunity.base

import android.R
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * @author zhongxinyu
 * @date 2020/5/12
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 */
class AlertDialogFragment : DialogFragment() {

    interface ClickListener {
        fun onComplete(tittle: String?)
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val tittle = arguments!!.getString("tittle")
        val message = arguments!!.getString("message")
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle(tittle)
        builder.setMessage(message)
        builder.setPositiveButton("确定") { dialogInterface, i ->
            val clickListener = activity as ClickListener?
            clickListener!!.onComplete(tittle)
        }
        builder.setNegativeButton("取消") { dialogInterface, i ->
            dismiss()
        }
        val alertDialog = builder.create()

        val window = alertDialog.window
        window!!.setBackgroundDrawableResource(R.color.white)
        window.decorView.setPadding(0, 0, 0, 0)
        val wlp = window.attributes
        wlp.gravity = Gravity.BOTTOM
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = wlp
        return alertDialog
    }

    companion object {
        fun newInstance(tittle: String?, message: String?): AlertDialogFragment {
            val fragment = AlertDialogFragment()
            val bundle = Bundle()
            bundle.putString("tittle", tittle)
            bundle.putString("message", message)
            fragment.arguments = bundle
            return fragment
        }
    }
}