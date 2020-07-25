package com.example.viewpager2demo.defineview
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.io.IOException
import java.io.InputStream

class LargeImageView : View {

    private var options : BitmapFactory.Options? = null
    private var moveGestureDetector : MoveGestureDetector? = null
    private var bitmapRegionDecoder : BitmapRegionDecoder? = null

    private var mImageWidth = 0
    private var mImageHeight = 0

    //绘制的区域
    @Volatile
    private var drawRect = Rect()

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context)
    }

    constructor(context: Context,attributeSet: AttributeSet,defaultInt: Int) : super(context,attributeSet,defaultInt) {
        init(context)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val mWidth = mImageWidth
        val mHeight = mImageHeight

        drawRect.left = mWidth / 2 - measuredWidth / 2
        drawRect.top = mHeight / 2 - measuredHeight / 2
        drawRect.right = drawRect.left + measuredWidth
        drawRect.bottom = drawRect.top + measuredHeight
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val mDrawBitmap = bitmapRegionDecoder?.decodeRegion(drawRect, options)
        canvas?.drawBitmap(mDrawBitmap!!, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        moveGestureDetector?.onTouchEvent(event!!)
        Log.d("onTouchEvent", "onTouchEvent:${moveGestureDetector?.onTouchEvent(event!!)}")
        return  true
    }

    open fun setInputStream(inputStream: InputStream) {
        try {
            bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false)
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, options)
            mImageWidth = options.outWidth
            mImageHeight = options.outHeight
            requestLayout()
            invalidate()
        } catch (e: IOException) {
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {}
        }
    }

    private fun checkWidth() {

        val mRect: Rect = drawRect
        val mWidth : Int = mImageWidth

        if (mRect.right > width) {

            mRect.right = width
            mRect.left = mWidth - width
        }

        if (mRect.left < 0) {

            mRect.left = 0
            mRect.right = width
        }
    }

    private fun checkHeight() {

        val mRect : Rect = drawRect
        val mHeight = mImageHeight

        if (mRect.bottom > mHeight) {

            mRect.bottom = height
            mRect.top = mHeight - height
        }

        if (mRect.top < 0) {

            mRect.top = 0
            mRect.bottom = height
        }
    }

    private fun init(context: Context) {

        options = BitmapFactory.Options()
        options?.inPreferredConfig = Bitmap.Config.RGB_565

        moveGestureDetector =
            MoveGestureDetector(context, object : MoveGestureDetector.SimpleMoveGestureDetector() {
                override fun onMove(moveGestureDetector: MoveGestureDetector): Boolean {

                    val moveX = moveGestureDetector.getMoveX().toInt()
                    val moveY = moveGestureDetector.getMoveY().toInt()
                    Log.d("onMove", "moveX:${moveX},moveY:${moveY}")
                    if (mImageWidth > width) {
                        drawRect.offset(-moveX, 0)
                        checkWidth()
                        invalidate()
                    }

                    if (mImageHeight > height) {
                        drawRect.offset(0, -moveY)
                        checkHeight()
                        invalidate()
                    }

                    return true
                }
            })
    }

}