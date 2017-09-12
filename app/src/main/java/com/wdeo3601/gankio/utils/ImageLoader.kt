package com.wdeo3601.gankio.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.wdeo3601.gankio.R

import java.io.File
import java.util.concurrent.ExecutionException


/**
 * // TODO: 2017/2/21 这个类需要重构，只需要几个方法
 * 1. 加载头像的方法
 * 2. 加载普通图片的方法
 * 3. 加载本地图片的方法
 * User:        lwj (alwjlola@gmail.com)
 * Date:        6/19/2016  17:34
 * Explanation: 图片加载引擎类
 */
class ImageLoader private constructor() {


    //加载url网络图片 人像居中
    @JvmOverloads
    fun loadImageUrl(context: Context, url: String, imageView: ImageView,
                     requestListener: RequestListener<Bitmap>? = null) {
        //GlideFaceDetector.initialize(context);
        Glide.with(context)
                .asBitmap()
                .load(url)
                //.dontAnimate()
                .apply(RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                )
                .transition(BitmapTransitionOptions.withCrossFade())
                //                .placeholder(R.drawable.ic_place_img)
                //.transform(new FaceCenterCrop())
                .listener(requestListener)

                .into(imageView)
    }

    //居中加载url网络图片
    fun loadImageUrlCenter(mContext: Context, url: String, imageView: ImageView,
                           requestListener: RequestListener<Drawable>) {
        Glide.with(mContext).load(url)
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                //                .placeholder(R.drawable.pictures_no)
                .listener(requestListener)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }

    //预加载
    fun preload(mContext: Context, url: String, listener: RequestListener<Drawable>) {
        Glide.with(mContext).load(url)
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .listener(listener)
                //                .placeholder(R.drawable.pictures_no)
                .preload()
    }

    fun loadImagePath(mContext: Context, path: String, imageView: ImageView,
                      requestListener: RequestListener<Drawable>) {
        Glide.with(mContext).load(File(path)).listener(requestListener).transition(DrawableTransitionOptions.withCrossFade()).into(imageView)
    }

    fun loadImagePath(mContext: Context, path: String, target: SimpleTarget<Bitmap>) {
        Glide.with(mContext).asBitmap().load(File(path)).apply(RequestOptions().centerCrop()).into(target)
    }

    fun loadImagePath(mContext: Context, path: String, imageView: ImageView) {
        //        if (MainActivity.isDestory) return;
        Glide.with(mContext).load(File(path))
                .apply(RequestOptions().centerCrop())
                .transition(DrawableTransitionOptions().crossFade())
                .into(imageView)
    }

    fun loadCornerImagePath(mContext: Context, path: String, imageView: ImageView) {
        Glide.with(mContext)


                .load(File(path))
                .apply(RequestOptions().placeholder(R.drawable.place_img))
                .transition(DrawableTransitionOptions.withCrossFade())
                //                .transform(new GlideRoundTransform(mContext, 15))
                .into(imageView)
    }

    fun loadImageRes(mContext: Context, res: Int, imageView: ImageView) {
        Glide.with(mContext).load(res)
                .transition(DrawableTransitionOptions().crossFade())
                .into(imageView)
    }

    @JvmOverloads
    fun loadCornerImageUrl(mContext: Context, url: String, imageView: ImageView,
                           requestListener: RequestListener<Bitmap>? = null, radius: Int = 15) {
        Glide.with(mContext)
                .asBitmap()
                .load(url)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(GlideRoundTransform(mContext, radius))
                        .placeholder(R.drawable.place_img)
                )
                .transition(BitmapTransitionOptions.withCrossFade())
                .listener(requestListener)
                //                                .centerCrop()
                .into(imageView)
    }

    @JvmOverloads
    fun loadCircleImageUrl(mContext: Context, url: String, imageView: ImageView,
                           requestListener: RequestListener<Drawable>? = null) {
        Glide.with(mContext)
                .load(url)
                .apply(RequestOptions()
                        .transform(GlideCircleTransform(mContext))
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(requestListener)
                .into(imageView)
    }

    @JvmOverloads
    fun loadCircleImageRes(mContext: Context, @DrawableRes res: Int, imageView: ImageView,
                           requestListener: RequestListener<Drawable>? = null) {
        Glide.with(mContext)
                .load(res)
                .apply(RequestOptions().transform(GlideCircleTransform(mContext)))
                .listener(requestListener)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }

    @JvmOverloads
    fun loadCircleImagePath(mContext: Context, url: String, imageView: ImageView,
                            requestListener: RequestListener<Drawable>? = null) {
        Glide.with(mContext)
                .load(File(url))
                .apply(RequestOptions()
                        .transform(GlideCircleTransform(mContext))
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(requestListener)
                .into(imageView)
    }

    /**
     * 加载圆角图像，自定义圆角半径
     *
     * @param mContext
     * @param url
     * @param imageView
     * @param radius    半径  单位dp
     */
    fun loadCornerImageUrl(mContext: Context, url: String, imageView: ImageView, radius: Int) {
        loadCornerImageUrl(mContext, url, imageView, null, radius)
    }

    fun pause(context: Context) {
        Glide.with(context).pauseRequests()
    }


    //    public void loadBitmap(Context context, byte[] bytes, ImageView imageView) {
    //        Logger.e(TAG, "current thread >>>>" + Thread.currentThread());
    //        Glide.with(context).load(bytes).centerCrop().crossFade().into(imageView);
    //    }

    fun downloadImage(mContext: Context, url: String): String? {
        val fileFutureTarget = Glide.with(mContext).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        try {
            return fileFutureTarget.get().absolutePath
        } catch (e: InterruptedException) {
            e.printStackTrace()
            return null
        } catch (e: ExecutionException) {
            e.printStackTrace()
            return null
        }

    }

    //

    //    /**
    //     * 居中显示照片
    //     */
    //    public void loadSubsamplingScaleImageCenterCrop(Context mContext, String url,
    //                                                    final SubsamplingScaleImageView imageView, final ContentLoadingProgressBar progressBar) {
    //
    //        if (MainActivity.isDestory) return;
    //
    //        GlideFaceDetector.initialize(mContext);
    //
    //        Glide.with(mContext)
    //                .load(url)
    //                .asBitmap()
    //                .transform(new FaceCenterCrop())
    //                .centerCrop()
    //                .diskCacheStrategy(DiskCacheStrategy.ALL)
    //                .into(new SimpleTarget<Bitmap>() {
    //                    @Override
    //                    public void onResourceReady(Bitmap resource,
    //                                                GlideAnimation<? super Bitmap> glideAnimation) {
    //                        progressBar.hide();
    //                        imageView.setImage(ImageSource.bitmap(resource));
    //                    }
    //                });
    //    }

    fun resume(context: Context) {
        Glide.with(context).resumeRequests()
    }

    private object SingletonLoader {
        val INSTANCE = ImageLoader()
    }

    companion object {

        private val TAG = "ImageLoader"

        val instance: ImageLoader
            get() = SingletonLoader.INSTANCE
    }
}//加载url网络图片 人像居中
