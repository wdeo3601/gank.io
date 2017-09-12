package com.wdeo3601.gankio.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.*

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

import java.security.MessageDigest

/**
 * Created by Weidongjian on 2015/7/29.
 */
class GlideRoundTransform @JvmOverloads constructor(context: Context, dp: Int = 4) : BitmapTransformation(context) {

    private var radius = 0f

    init {
        this.radius = Resources.getSystem().displayMetrics.density * dp
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        return roundCrop(pool, toTransform)
    }

    private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
        if (source == null) return null

        var result: Bitmap? = pool.get(source.width, source.height, Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(result!!)
        val paint = Paint()
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rectF, radius, radius, paint)
        return result
    }

    //    @Override
    //    public String getId() {
    //        return getClass().getName() + Math.round(radius);
    //    }


    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        //        messageDigest.update(Byte.parseByte(getClass().getName() + Math.round(radius)));
    }
}
