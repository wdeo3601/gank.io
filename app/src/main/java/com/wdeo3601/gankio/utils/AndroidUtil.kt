package com.wdeo3601.gankio.utils

import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Vibrator
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.app.Fragment
import android.telephony.TelephonyManager
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


import com.f2prateek.rx.preferences.Preference
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.logger.Logger
import com.wdeo3601.gankio.App

import org.greenrobot.eventbus.EventBus

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.lang.ref.WeakReference
import java.lang.reflect.Field
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID
import java.util.regex.Matcher
import java.util.regex.Pattern

import me.yokeyword.fragmentation.SupportFragment
import rx.Observable
import rx.Subscription
import rx.functions.Action1
import rx.schedulers.Schedulers
import top.zibin.luban.Luban

object AndroidUtil {
    private val TWO_MINUTES = 1000 * 60 * 2
    private val TAG = "AndroidUtil"
    private val outOfBanlanceDialog: Dialog? = null

    fun copyToClipBoard(context: Context, text: String, success: String) {
        val clipData = ClipData.newPlainText("Venjer_copy", text)
        val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        manager.primaryClip = clipData
        ToastUtil.showShort(success)
    }

    fun startAct(context: Context, klass: Class<*>) {
        val intent = Intent(context, klass)
        context.startActivity(intent)
    }

    fun fragmentStartForResult(context: Fragment, klass: Class<*>, requestCode: Int) {
        val intent = Intent(context.activity, klass)
        context.startActivityForResult(intent, requestCode)
    }


    //工具类：判断是否是字母或者数字
    fun isNumOrLetters(str: String): Boolean {
        val regEx = "^[A-Za-z0-9_]+$"
        val p = Pattern.compile(regEx)
        val m = p.matcher(str)
        return m.matches()
    }

    fun bitmap2Byte(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }


    fun hasNetwork(locationManager: LocationManager): Boolean {
        val providerList = locationManager.getProviders(true)
        for (provider in providerList) {
            if (provider == "network") {
                return true
            }
        }
        return false
    }

    fun hasPassive(locationManager: LocationManager): Boolean {
        val providerList = locationManager.getProviders(true)
        for (provider in providerList) {
            if (provider == "passive") {
                return true
            }
        }
        return false
    }

    //判断新位置是否相比老位置更准确
    fun isBetterLocation(newLocation: Location, currentBestLocation: Location?): Boolean {
        if (currentBestLocation == null) {
            // A new newLocation is always better than no newLocation
            return true
        }

        // Check whether the new newLocation fix is newer or older
        val timeDelta = newLocation.time - currentBestLocation.time
        val isSignificantlyNewer = timeDelta > TWO_MINUTES
        val isSignificantlyOlder = timeDelta < -TWO_MINUTES
        val isNewer = timeDelta > 0

        // If it's been more than two minutes since the current newLocation, use the new newLocation
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true
            // If the new newLocation is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false
        }

        // Check whether the new newLocation fix is more or less accurate
        val accuracyDelta = (newLocation.accuracy - currentBestLocation.accuracy).toInt()
        val isLessAccurate = accuracyDelta > 0
        val isMoreAccurate = accuracyDelta < 0
        val isSignificantlyLessAccurate = accuracyDelta > 200

        // Check if the old and new newLocation are from the same provider
        val isFromSameProvider = isSameProvider(newLocation.provider, currentBestLocation.provider)

        // Determine newLocation quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true
        } else if (isNewer && !isLessAccurate) {
            return true
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true
        }
        return false
    }

    //判断是否是同一个位置提供者
    fun isSameProvider(provider1: String?, provider2: String?): Boolean {
        return if (provider1 == null) {
            provider2 == null
        } else provider1 == provider2
    }


    /**
     * 多选打开相册 不带编辑功能
     *
     * @param supportFragment
     * @param isSingle
     * @param isPreViewImage
     */
    fun startGalleryFromFragment(supportFragment: SupportFragment, isSingle: Boolean, isPreViewImage: Boolean) {
        // 进入相册 以下是例子：用不到的api可以不写
        //        PictureSelector.create(supportFragment)
        //                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
        //                .theme(R.style.picture_Sina_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
        //                .maxSelectNum(9)// 最大图片选择数量 int
        //                .minSelectNum(1)// 最小选择数量 int
        //                .imageSpanCount(3)// 每行显示个数 int
        //                .selectionMode(isSingle ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
        //                .previewImage(isPreViewImage)// 是否可预览图片 true or false
        //                .previewVideo(true)// 是否可预览视频 true or false
        //                .enablePreviewAudio(true) // 是否可播放音频 true or false
        ////                .compressGrade()// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
        //                .isCamera(true)// 是否显示拍照按钮 true or false
        //                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
        //                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
        //                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
        //                .enableCrop(false)// 是否裁剪 true or false
        //                .compress(false)// 是否压缩 true or false
        //                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
        //                .glideOverride(200, 200)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
        ////                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
        ////                .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
        //                .isGif(false)// 是否显示gif图片 true or false
        ////                .freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
        ////                .circleDimmedLayer()// 是否圆形裁剪 true or false
        ////                .showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
        ////                .showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
        ////                .openClickSound(true)// 是否开启点击声音 true or false
        ////                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
        ////                .previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
        ////                .cropCompressQuality()// 裁剪压缩质量 默认90 int
        ////                .compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
        ////                .compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
        ////                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
        ////                .rotateEnabled() // 裁剪是否可旋转图片 true or false
        ////                .scaleEnabled()// 裁剪是否可放大缩小图片 true or false
        ////                .videoQuality()// 视频录制质量 0 or 1 int
        ////                .videoSecond()// 显示多少秒以内的视频or音频也可适用 int
        ////                .recordVideoSecond()//视频秒数录制 默认60s int
        //                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 多选打开相册 不带编辑功能
     *
     * @param activity
     * @param isSingle
     * @param isPreViewImage
     */
    fun startGalleryFromActivity(activity: Activity, isSingle: Boolean, isPreViewImage: Boolean) {
        // 进入相册 以下是例子：用不到的api可以不写
        //        PictureSelector.create(activity)
        //                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
        //                .theme(R.style.picture_Sina_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
        //                .maxSelectNum(9)// 最大图片选择数量 int
        //                .minSelectNum(1)// 最小选择数量 int
        //                .imageSpanCount(3)// 每行显示个数 int
        //                .selectionMode(isSingle ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
        //                .previewImage(isPreViewImage)// 是否可预览图片 true or false
        //                .previewVideo(true)// 是否可预览视频 true or false
        //                .enablePreviewAudio(true) // 是否可播放音频 true or false
        ////                .compressGrade()// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
        //                .isCamera(true)// 是否显示拍照按钮 true or false
        //                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
        //                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
        //                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
        //                .enableCrop(false)// 是否裁剪 true or false
        //                .compress(false)// 是否压缩 true or false
        //                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
        //                .glideOverride(200, 200)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
        ////                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
        ////                .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
        //                .isGif(false)// 是否显示gif图片 true or false
        ////                .freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
        ////                .circleDimmedLayer()// 是否圆形裁剪 true or false
        ////                .showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
        ////                .showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
        ////                .openClickSound(true)// 是否开启点击声音 true or false
        ////                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
        ////                .previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
        ////                .cropCompressQuality()// 裁剪压缩质量 默认90 int
        ////                .compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
        ////                .compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
        ////                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
        ////                .rotateEnabled() // 裁剪是否可旋转图片 true or false
        ////                .scaleEnabled()// 裁剪是否可放大缩小图片 true or false
        ////                .videoQuality()// 视频录制质量 0 or 1 int
        ////                .videoSecond()// 显示多少秒以内的视频or音频也可适用 int
        ////                .recordVideoSecond()//视频秒数录制 默认60s int
        //                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        //    }
        //
        //    /**
        //     * 拍照
        //     */
        //    public static void openCamera(SupportFragment fragment) {
        //        //直接打开相机
        //        // 单独拍照
        //        PictureSelector.create(fragment)
        //                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
        //                .compressGrade(com.luck.picture.lib.compress.Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
        //                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
        //                .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
        //                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    //    /**
    //     * 打开相机
    //     *
    //     * @Author:Karl-dujinyang 兼容7.0打开路径问题
    //     */
    //    public static void openZKCameraFromFragment(SupportFragment fragment) {
    //        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //        if (captureIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
    //            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
    //            String imageName = "immqy_%s.jpg";
    //            String filename = String.format(imageName, dateFormat.format(new Date()));
    //            File mImageStoreDir = new File(Environment.getExternalStorageDirectory(), "/DCIM/IMMQY/");
    //            ;
    //            if (!mImageStoreDir.exists()) {
    //                mImageStoreDir.mkdirs();
    //            }
    //            RxGalleryFinalApi.fileImagePath = new File(mImageStoreDir, filename);
    //            String mImagePath = RxGalleryFinalApi.fileImagePath.getAbsolutePath();
    //            cn.finalteam.rxgalleryfinal.utils.Logger.i("->mImagePath:" + mImagePath);
    //            if (mImagePath != null) {
    //                    /*获取当前系统的android版本号*/
    //                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    //                cn.finalteam.rxgalleryfinal.utils.Logger.i("->VERSION:" + currentapiVersion);
    //                if (currentapiVersion < 24) {
    //                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(RxGalleryFinalApi.fileImagePath));
    //                    /*captureIntent.putExtra(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");*/
    //                    fragment.startActivityForResult(captureIntent, RxGalleryFinalApi.TAKE_IMAGE_REQUEST_CODE);
    //                } else {
    //                    ContentValues contentValues = new ContentValues(1);
    //                    contentValues.put(MediaStore.Images.Media.DATA, mImagePath);
    //                    contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
    //                    Uri uri = fragment.getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    //                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    //                    fragment.startActivityForResult(captureIntent, RxGalleryFinalApi.TAKE_IMAGE_REQUEST_CODE);
    //                }
    //            }
    //        } else {
    //            Toast.makeText(fragment.getContext(), cn.finalteam.rxgalleryfinal.R.string.gallery_device_camera_unable, Toast.LENGTH_SHORT).show();
    //        }
    //    }

    //    /**
    //     * 单选不编辑
    //     */
    //    public static void startGallery(BaseSingleGalleyResultCallback callback) {
    //        //单选图片
    //        RxGalleryFinal
    //                .with(UIUtil.getContext())
    //                .image()
    //                .radio()
    ////                .crop()
    //                .imageLoader(ImageLoaderType.GLIDE)
    //                .subscribe(callback)
    //                .openGallery();
    //    }

    //    /**
    //     * 单选可以确定
    //     */
    //    public static void startGalleryWithConfirm(BaseGalleyResultCallback callback) {
    //        //多选图片
    //        RxGalleryFinal
    //                .with(UIUtil.getContext())
    //                .image()
    //                .multiple()
    //                .maxSize(1)
    //                .imageLoader(ImageLoaderType.GLIDE)
    //                .subscribe(callback)
    //                .openGallery();
    //        getMultiListener();
    //    }

    //    /**
    //     * 多选事件都会在这里执行
    //     */
    //    private static void getMultiListener() {
    //        //得到多选的事件
    //        RxGalleryListener.getInstance().setMultiImageCheckedListener(new IMultiImageCheckedListener() {
    //            @Override
    //            public void selectedImg(Object t, boolean isChecked) {
    //                //这个主要点击或者按到就会触发，所以不建议在这里进行Toast
    //            }
    //
    //            @Override
    //            public void selectedImgMax(Object t, boolean isChecked, int maxSize) {
    //                Toast.makeText(UIUtil.getContext(), "你最多只能选择" + maxSize + "张图片", Toast.LENGTH_SHORT).show();
    //            }
    //        });
    //    }

    /**
     * 将首字母大写 这种方式效率比较高
     * 即进行字母的ascii编码前移
     * 注释掉的为常规写法
     */
    fun initialToUpperCase(name: String): String {
        //     name = name.substring(0, 1).toUpperCase() + name.substring(1);
        //        return  name;
        val cs = name.toCharArray()
        cs[0] = (cs[0].toInt()-32).toChar()
        return String(cs)
    }

    fun formatDisplayTime(tDate: Date?): String {
        var display = ""
        val tMin = 60 * 1000
        val tHour = 60 * tMin
        val tDay = 24 * tHour

        if (tDate != null) {
            try {
                val today = Date()
                val thisYearDf = SimpleDateFormat("yyyy")
                val todayDf = SimpleDateFormat("yyyy-MM-dd")
                val thisYear = Date(thisYearDf.parse(thisYearDf.format(today)).time)
                val yesterday = Date(todayDf.parse(todayDf.format(today)).time)
                val beforeYes = Date(yesterday.time - tDay)
                val halfDf = SimpleDateFormat("MM月dd日")
                val dTime = today.time - tDate.time
                if (tDate.before(thisYear)) {
                    display = SimpleDateFormat("yyyy年MM月dd日").format(tDate)
                } else {
                    if (dTime < tMin) {
                        display = "刚刚"
                    } else if (dTime < tHour) {
                        display = Math.ceil((dTime / tMin).toDouble()).toInt().toString() + "分钟前"
                    } else if (dTime < tDay && tDate.after(yesterday)) {
                        display = Math.ceil((dTime / tHour).toDouble()).toInt().toString() + "小时前"
                    } else if (tDate.after(beforeYes) && tDate.before(yesterday)) {
                        display = "昨天" + SimpleDateFormat("HH:mm").format(tDate)
                    } else {
                        display = TimeUtils.date2String(tDate, "yyyy/MM/dd HH:mm:ss")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return display
    }

    /**
     * 根据文件路径得到文件名
     */
    fun getFileName(filePath: String): String? {
        return if (!TextUtils.isEmpty(filePath)) filePath.substring(filePath.lastIndexOf("/") + 1) else null
    }

    /**
     * Luban 压缩
     */
    @Throws(IOException::class)
    fun compressFile(context: Context, file: File): Observable<File> {
        return Observable.just(Luban.with(context).load(file).get())
    }

    /**
     * Luban 压缩
     */
    @Throws(IOException::class)
    fun compressFileReturnFile(context: Context, file: File): File {
        return Luban.with(context).load(file).get()
    }

    /**
     * 扩大 View 的点击范围
     */
    fun expandViewTouchDelegate(view: View) {
        (view.parent as View).post {
            val rect = Rect()
            rect.setEmpty()
            val touchDelegate = TouchDelegate(rect, view)
            if (View::class.java.isInstance(view.parent)) {
                (view.parent as View).touchDelegate = touchDelegate
            }
        }
    }

    /**
     * 验证手机号码
     *
     * @param phoneNumber 手机号码
     * @return boolean
     */
    fun checkPhoneNumber(phoneNumber: String): Boolean {
        val pattern = Pattern.compile("^1[34578][0-9]{9}$")
        val matcher = pattern.matcher(phoneNumber)
        return matcher.matches()
    }

    /**
     * 验证昵称
     *
     * @param username 用户名
     * @return boolean
     */
    fun checkNickname(username: String): Boolean {
        val regex = "([\\u4e00-\\u9fa5a-zA-Z0-9_])"
        val p = Pattern.compile(regex)
        val m = p.matcher(username)
        return m.matches()
    }

    /**
     * 改变局部文字大小
     *
     * @param text       文本
     * @param startIndex 要修改的文字的起始坐标
     * @param count      要修改的文字的个数
     */
    fun biggerText(textView: TextView, text: String, size: Int, startIndex: Int,
                   count: Int) {
        val spannable = SpannableString(text)
        spannable.setSpan(AbsoluteSizeSpan(UIUtil.dp2px(size.toFloat())), startIndex, startIndex + count,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannable
    }

    /**
     * 改变局部文字颜色
     *
     * @param text       文本
     * @param startIndex 要修改的文字的起始坐标
     * @param count      要修改的文字的个数
     */
    fun colorText(textView: TextView, text: String, color: Int, startIndex: Int,
                  count: Int) {
        val spannable = SpannableString(text)
        spannable.setSpan(ForegroundColorSpan(color), startIndex, startIndex + count,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannable
    }

    /**
     * 改变局部字体大小和颜色
     */
    fun biggerAndColorText(textView: TextView, text: String, size: Int, color: Int,
                           startIndex: Int, count: Int) {
        val spannable = SpannableString(text)
        spannable.setSpan(AbsoluteSizeSpan(UIUtil.dp2px(size.toFloat())), startIndex, startIndex + count,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(UIUtil.getColor(color)), startIndex,
                startIndex + count, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannable
    }

    /**
     * 数组中的最大值和最小值
     *
     * @return 0 为最小值 1为最大值
     */
    fun extremumInArray(array: IntArray): IntArray {
        var i: Int
        var min: Int
        var max: Int
        max = array[0]
        min = max
        i = 0
        while (i < array.size) {
            if (array[i] > max)
            // 判断最大值
            {
                max = array[i]
            }
            if (array[i] < min)
            // 判断最小值
            {
                min = array[i]
            }
            i++
        }
        return intArrayOf(min, max)
    }

    /**
     * 得到 attr 的值
     */
    fun getTypedValueFromAttr(context: Context, attr: Int): TypedValue {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attr, typedValue, true)
        return typedValue
    }

    /**
     * 某日期减去天数后得到的日期
     */
    fun minusData(date: Date, day: Int): Long {
        return TimeUtils.date2Millis(date) - day.toLong() * 24 * 3600 * 1000
    }

    /**
     * 把13位时间戳转换成日期
     *
     * @param time   13位时间戳
     * @param format 要转换的日期格式
     * @return 字符串类型的日期
     */
    fun formatUnixTime(time: Long, format: String): String {
        return SimpleDateFormat(format).format(Date(time))
    }


    //endregion


    fun isAdult(s: String): Boolean {
        var isAudit = false
        try {
            // 格式化
            val simpleDateFormat = SimpleDateFormat("yyyyMMdd", Locale.CHINA)
            // 出生日期
            val birthday = simpleDateFormat.parse(s)

            val calendar = Calendar.getInstance()
            calendar.time = birthday
            // 出生日期加上18年
            calendar.add(Calendar.YEAR, 18)

            // 当前时间
            val now = Date()
            val nowCalendar = Calendar.getInstance()

            // 将时分秒设置为0，格式为19880911 00:00:00
            nowCalendar.time = now
            // 如果用此Field则按十二小时格式设置值，Noon and midnight表示为0，而不是12.
            // nowCalendar.set(Calendar.HOUR, 0);
            nowCalendar.set(Calendar.HOUR_OF_DAY, 0)
            nowCalendar.set(Calendar.MINUTE, 0)
            nowCalendar.set(Calendar.SECOND, 0)

            if (calendar.before(nowCalendar)) {
                isAudit = true
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            isAudit = false
        }

        return isAudit
    }

    /**
     * 震动一次
     */
    fun vibrator() {
        val vibrator = App.instance.mContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val pattern = longArrayOf(100, 400)   // 停止 开启 停止 开启
        vibrator.vibrate(pattern, -1)
    }

    /**
     * get data of Internal Storage
     */
    fun getDataDirOfInternalStorage(context: Context): String {
        return context.cacheDir.absolutePath + File.separator + "data"
    }

    fun getNetCacheDir(context: Context): String {
        return getDataDirOfInternalStorage(context) + File.separator + "qxtNetCache"
    }

    /**
     * 跳转到 app 详情页面
     */
    fun goToAppDetail(activity: Activity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }


    fun isRemoteFile(fileUrl: String): Boolean {
        return fileUrl.startsWith("https://") || fileUrl.startsWith("http://")
    }

    interface OnClickNotificationInAppListener {
        fun onClick()
    }

    //region 获取通讯录集合
    private val PHONES_PROJECTION = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
    private val PHONES_DISPLAY_NAME_INDEX = 0
    private val PHONES_NUMBER_INDEX = 1


    //endregion

    /**
     * 发送短信
     *
     * @param smsBody
     */

    fun sendSMS(context: Context, smsPhone: String, smsBody: String) {
        val smsToUri = Uri.parse("smsto:" + smsPhone)
        var intent = Intent(Intent.ACTION_VIEW, smsToUri)
        intent.putExtra("address", smsPhone)
        intent.putExtra("sms_body", smsBody)
        intent.type = "vnd.android-dir/mms-sms"
        //        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (canOpenSmsNormally(context, intent)) {//打开短信app可以返回自己app
            context.startActivity(intent)
        } else {//不能返回自己app
            try {
                intent = Intent(Intent.ACTION_SENDTO, smsToUri)
                intent.putExtra("sms_body", smsBody)
                //                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                ToastUtil.showShort("邀请失败")
            }

        }

    }

    private fun canOpenSmsNormally(paramContext: Context, paramIntent: Intent): Boolean {
        return paramContext.packageManager.queryIntentActivities(paramIntent, PackageManager.MATCH_DEFAULT_ONLY).size > 0
    }

}
