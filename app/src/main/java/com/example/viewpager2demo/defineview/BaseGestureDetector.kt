package com.example.viewpager2demo.defineview
import android.content.Context
import android.view.MotionEvent

abstract class BaseGestureDetector(context: Context) {

    protected val mContext = context
    protected var mGestureInProgress = false

    protected var mPreMotionEvent : MotionEvent? = null
    protected var mCurrentMotionEvent : MotionEvent? = null

    fun onTouchEvent(motionEvent: MotionEvent) : Boolean {
        if (!mGestureInProgress) {

            handStartProgressEvent(motionEvent)
        } else {

            handInProgressEvent(motionEvent)
        }
        return true
    }

    protected fun resetState() {

        mPreMotionEvent?.let {
            it.recycle()
        }
        mPreMotionEvent = null

        mCurrentMotionEvent?.let {
            it.recycle()
        }
        mCurrentMotionEvent = null

        mGestureInProgress = false
    }

    protected abstract fun handInProgressEvent(motionEvent: MotionEvent)
    protected abstract fun handStartProgressEvent(motionEvent: MotionEvent)
    protected abstract fun updateStateByEvent(motionEvent: MotionEvent)
}