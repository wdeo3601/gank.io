package com.wdeo3601.gankio.manager

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.util.LruCache

/**
 * Date:        7/26/2016  17:33
 * Explanation: 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
 */
class LruImageCache private constructor() {

    private object SingletonLoader {
        val INSTANCE = LruImageCache()
    }

    init {
        // 获取应用程序最大可用内存
        val maxMemory = Runtime.getRuntime().maxMemory().toInt()
        val cacheSize = maxMemory / 8
        // 设置图片缓存大小为程序最大可用内存的1/8
        mMemoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, bitmap: Bitmap?): Int {
                return bitmap!!.byteCount
            }
        }
    }

    /**
     * 将一张图片存储到LruCache中。
     *
     * @param key    LruCache的键，这里传入图片的URL地址。
     * @param bitmap LruCache的键，这里传入从网络上下载的Bitmap对象。
     */
    fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap)
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param key LruCache的键，这里传入图片的URL地址。
     * @return 对应传入键的Bitmap对象，或者null。
     */
    fun getBitmapFromMemoryCache(key: String): Bitmap? {
        return mMemoryCache.get(key)
    }

    companion object {
        private lateinit var mMemoryCache: LruCache<String, Bitmap>

        val instance: LruImageCache
            get() = SingletonLoader.INSTANCE

        fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int): Int {
            // 源图片的宽度
            val width = options.outWidth
            var inSampleSize = 1
            if (width > reqWidth) {
                // 计算出实际宽度和目标宽度的比率
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
                inSampleSize = widthRatio
            }
            return inSampleSize
        }

        fun decodeSampledBitmapFromResource(pathName: String, reqWidth: Int): Bitmap {
            // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(pathName, options)
            // 调用上面定义的方法计算inSampleSize值
            options.inSampleSize = calculateInSampleSize(options, reqWidth)
            // 使用获取到的inSampleSize值再次解析图片
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(pathName, options)
        }
    }
}
