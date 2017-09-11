package com.wdeo3601.gankio.utils

import android.content.Context
import android.content.DialogInterface
import android.support.annotation.StringRes
import android.text.Spanned

import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.GravityEnum
import com.afollestad.materialdialogs.MaterialDialog

/**
 * Created by lwj on 8/18/2016.
 */
object MaterialDialogUtil {
    fun showCustomView(context: Context, res: Int, @StringRes position: Int,
                       callback: MaterialDialog.SingleButtonCallback): MaterialDialog {
        return MaterialDialog.Builder(context).customView(res, true)
                .onPositive(callback)
                .negativeText("取消")
                .onNegative { dialog, which -> dialog.dismiss() }
                .cancelable(false)
                .positiveText(position)
                .autoDismiss(false)
                .build()
    }


    /**
     * 单点登录被挤下线的通知dialog

     * @param context
     * *
     * @param title
     * *
     * @param content
     */
    fun showSignOutTip(context: Context, title: String, content: String) {
        showBasicDialog(context, title, content, "好的", null, null, null, false, null)
    }

    /**
     * 向TA提问发送成功dialog

     * @param context
     * *
     * @param title
     * *
     * @param content
     */
    fun showSendSuccessTip(context: Context, title: String, content: Spanned, positiveListener: MaterialDialog.SingleButtonCallback) {
        showBasicDialog(context, title, content, "好的", positiveListener, null, null, false, null)
    }


    fun showBasicNoTitle(context: Context, content: String,
                         callback: MaterialDialog.SingleButtonCallback) {
        showBasicDialog(context, null, content, "确定", callback, "取消", null, true, null)
    }

    fun showBasicNoTitle(context: Context, content: String, postiveText: String,
                         callback: MaterialDialog.SingleButtonCallback) {
        showBasicDialog(context, null, content, postiveText, callback, "取消", null, true, null)
    }

    fun showBasicNoTitle(context: Context, content: String,
                         callback: MaterialDialog.SingleButtonCallback, callback2: MaterialDialog.SingleButtonCallback,
                         cancelListener: DialogInterface.OnCancelListener) {
        showBasicDialog(context, null, content, "确定", callback, "取消", callback2, true, cancelListener)
    }

    fun showBasicNoTitleNoCancel(context: Context, content: String,
                                 callback: MaterialDialog.SingleButtonCallback) {
        showBasicDialog(context, null, content, "确定", callback, null, null, true, null)
    }

    fun showBasicWithTitle(context: Context, title: String, content: String,
                           callback: MaterialDialog.SingleButtonCallback) {
        showBasicDialog(context, title, content, "确定", callback, null, null, false, null)
    }

    fun showBasicWithTitleAndCancel(context: Context, title: String, content: String,
                                    callback: MaterialDialog.SingleButtonCallback, callback2: MaterialDialog.SingleButtonCallback,
                                    cancelListener: DialogInterface.OnCancelListener) {
        showBasicDialog(context, title, content, "确定", callback, "取消", callback2, true, cancelListener)
    }

    /**
     * dialog基础方法，包括所有设置，其他的定制调这个方法就可以

     * @param context           上下文
     * *
     * @param title             标题，没有就传 null
     * *
     * @param content           内容
     * *
     * @param positiveText      确定键文本
     * *
     * @param possitiveCallback 确定的回调 没有传null
     * *
     * @param negativeText      取消键文本
     * *
     * @param negativeCallback  取消的回调 没有就传null
     * *
     * @param canCancel         点击空白处是否可以dismiss
     * *
     * @param cancelListener    dismiss的回调监听 没有就传null
     */
    fun showBasicDialog(context: Context, title: String?, content: String, positiveText: String, possitiveCallback: MaterialDialog.SingleButtonCallback?, negativeText: String?, negativeCallback: MaterialDialog.SingleButtonCallback?, canCancel: Boolean, cancelListener: DialogInterface.OnCancelListener?) {
        MaterialDialog.Builder(context).title(title!!).content(content)
                .cancelable(canCancel)
                .positiveText(positiveText)
                .onPositive(possitiveCallback!!)
                .onNegative(negativeCallback!!)
                .negativeText(negativeText!!)
                .cancelListener(cancelListener!!)
                .build()
                .show()
    }

    fun showBasicDialog(context: Context, title: String, content: Spanned, positiveText: String, possitiveCallback: MaterialDialog.SingleButtonCallback, negativeText: String?, negativeCallback: MaterialDialog.SingleButtonCallback?, canCancel: Boolean, cancelListener: DialogInterface.OnCancelListener?) {
        MaterialDialog.Builder(context).title(title).content(content)
                .cancelable(canCancel)
                .positiveText(positiveText)
                .onPositive(possitiveCallback)
                .onNegative(negativeCallback!!)
                .negativeText(negativeText!!)
                .cancelListener(cancelListener!!)
                .build()
                .show()
    }


    /**
     * 显示带进度的 progressDialog
     */
    fun showProgressDeterminateDialog(context: Context, title: String, content: String,
                                      cancelListener: DialogInterface.OnCancelListener,
                                      showListener: DialogInterface.OnShowListener) {
        MaterialDialog.Builder(context).title(title)
                .content(content)
                .contentGravity(GravityEnum.CENTER)
                .progress(false, 150, true)
                .cancelListener(cancelListener)
                .showListener(showListener)
                .show()
    }

    /**
     * 显示不带标题的非横向 progressDialog
     */
    fun showProgressIndeterminateDialog(context: Context, content: String): MaterialDialog {
        return showIndeterminateProgressDialog(context, null, content, false)
    }

    private fun showIndeterminateProgressDialog(context: Context, title: String?,
                                                content: String, horizontal: Boolean): MaterialDialog {
        return MaterialDialog.Builder(context).title(title!!)
                .content(content)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .show()
    }

}
