package com.example.viewpager2demo.defineview
import android.content.Context
import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent

class MoveGestureDetector(
    context: Context,
    moveGestureDetectorListener: MoveGestureDetectorListener
) : BaseGestureDetector(context) {

    private val mMoveGestureDetectorListener = moveGestureDetectorListener

    private var mPrePointF : PointF? = null
    private var mCurrentPointF : PointF? = null

    private var mExternalPointF : PointF = PointF()

    override fun handStartProgressEvent(motionEvent: MotionEvent) {
        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                resetState()
                mPreMotionEvent = MotionEvent.obtain(motionEvent)
                updateStateByEvent(motionEvent)
            }
            MotionEvent.ACTION_MOVE -> {
                mGestureInProgress = mMoveGestureDetectorListener.moveBegin(this)
            }
            else -> {}
        }
    }

    override fun handInProgressEvent(motionEvent: MotionEvent) {
        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mMoveGestureDetectorListener.moveEnd(this)
                resetState()
            }
            MotionEvent.ACTION_MOVE -> {
                updateStateByEvent(motionEvent)
                val update = mMoveGestureDetectorListener.onMove(this)
                if (update) {
                    mPreMotionEvent?.recycle()
                    mPreMotionEvent = MotionEvent.obtain(motionEvent)
                }
            }
            else -> {}
        }
    }

    override fun updateStateByEvent(motionEvent: MotionEvent) {

        val preMotionEvent : MotionEvent? = mPreMotionEvent

        mPrePointF = calculateFocalPointer(preMotionEvent!!)
        mCurrentPointF = calculateFocalPointer(motionEvent)

        val mSkipThisMotionEvent = preMotionEvent.pointerCount != motionEvent.pointerCount
        Log.d("updateStateByEvent", "mSkipThisMotionEvent:$mSkipThisMotionEvent")
        Log.d("updateStateByEvent", "distance->x0:${mPrePointF!!.x}")
        Log.d("updateStateByEvent", "distance->x1:${mCurrentPointF!!.x}")
        Log.d("updateStateByEvent", "distance->y0:${mPrePointF!!.y}")
        Log.d("updateStateByEvent", "distance->y1:${mCurrentPointF!!.y}")
        mExternalPointF.x = if (mSkipThisMotionEvent) 0f else mCurrentPointF!!.x.minus(mPrePointF!!.x)
        mExternalPointF.y = if (mSkipThisMotionEvent) 0f else mCurrentPointF!!.y.minus(mPrePointF!!.y)
    }

    //根据event计算多手指中心
    private fun calculateFocalPointer(motionEvent: MotionEvent) : PointF {

        val pointerCount = motionEvent.pointerCount
        var x = 0f
        var y = 0f
        for (i in 0.until(pointerCount)) {

            x = x.plus(motionEvent.getX(i))
            y = y.plus(motionEvent.getY(i))
        }

        x = x.div(pointerCount)
        y = y.div(pointerCount)
        Log.d("calculateFocalPointer", "calculateFocalPointer:$x")
        Log.d("calculateFocalPointer", "calculateFocalPointer:$y")
        return PointF(x, y)
    }

    fun getMoveX() : Float = mExternalPointF.x
    fun getMoveY() : Float = mExternalPointF.y

    interface MoveGestureDetectorListener {

        fun moveBegin(moveGestureDetector: MoveGestureDetector) : Boolean

        fun onMove(moveGestureDetector: MoveGestureDetector) : Boolean

        fun moveEnd(moveGestureDetector: MoveGestureDetector)
    }

    open class SimpleMoveGestureDetector : MoveGestureDetectorListener {

        override fun moveBegin(moveGestureDetector: MoveGestureDetector) : Boolean {

            return true
        }

        override fun onMove(moveGestureDetector: MoveGestureDetector) : Boolean {

            return false
        }

        override fun moveEnd(moveGestureDetector: MoveGestureDetector) {}
    }
}