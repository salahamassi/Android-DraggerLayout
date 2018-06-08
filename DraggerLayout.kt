package com.msa.wallapp.helper.customViews

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.support.v4.widget.ViewDragHelper.INVALID_POINTER
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout


class DraggerLayout : FrameLayout {

    private val sensitivity = 1.0f
    private var activePointerId = INVALID_POINTER
    private var bottomBound = 0
    private val dragLimit = 0.5f

    private lateinit var dragHelper: ViewDragHelper
    private var dragView: View? = null
    private var shadowView: View = View(context)
    private lateinit var dragHelperCallback: ViewDragHelper.Callback

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        initDaggerHelper()
    }

    private fun initDaggerHelper() {
        dragHelperCallback = object : ViewDragHelper.Callback() {
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return child == dragView
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                return Math.min(Math.max(top, paddingTop), bottomBound)
            }

            override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)
                val dragOffset = Math.abs(top)
                shadowView.alpha = 1 - (dragOffset.toFloat() / bottomBound.toFloat())
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                super.onViewReleased(releasedChild, xvel, yvel)
                val dragView = dragView.let { it } ?: return

                val dragViewHeight = dragView.height
                val shouldCloseDragView = dragViewHeight < (dragView.y + (dragViewHeight * dragLimit))

                if (shouldCloseDragView) {
                    closeDragView()
                } else {
                    openDragView()
                }
            }

        }

        dragHelper = ViewDragHelper.create(this, sensitivity, dragHelperCallback)
    }

    private fun openDragView() {
        val dragView = dragView.let { it } ?: return
        val result = dragHelper.smoothSlideViewTo(dragView, 0, 0)
        if (result) {
            // stop animation
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    private fun closeDragView() {
        val dragView = dragView.let { it } ?: return
        val result = dragHelper.smoothSlideViewTo(dragView, 0, (1 * bottomBound))
        if (result) {
            // stop animation
            ViewCompat.postInvalidateOnAnimation(this)
        }
        onDragEnd()
    }

    private fun onDragEnd() {
        finish()
    }

    private fun finish() {
        val context = context
        if (context is Activity) {
            if (!context.isFinishing) {
                context.finish()
            }
        }
    }

    private fun expand() {
        val dragView = dragView.let { it } ?: return
        Handler().postDelayed({
            if (isEnabled) {
                dragView.alpha = 1f
                shadowView.visibility = View.VISIBLE
                openDragView()
            }
        }, 200)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measureWidth = View.MeasureSpec.makeMeasureSpec(
                measuredWidth - paddingLeft - paddingRight,
                View.MeasureSpec.EXACTLY)
        val measureHeight = View.MeasureSpec.makeMeasureSpec(
                measuredHeight - paddingTop - paddingBottom,
                View.MeasureSpec.EXACTLY)
        val dragView = dragView.let { it } ?: return
        dragView.measure(measureWidth, measureHeight)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (!isInEditMode) {
            initSubViews()
            expand()
        }
    }

    private fun initSubViews() {
        if (childCount != 1) {
            throw IllegalArgumentException("You Must add one child as a container!")
        }

        dragView = getChildAt(0)
        removeViewAt(0)

        shadowView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        shadowView.visibility = View.INVISIBLE
        shadowView.setBackgroundColor(Color.DKGRAY)
        addView(shadowView, 0)
        addView(dragView, 1)
        dragView!!.setBackgroundColor(Color.WHITE)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bottomBound = h
    }

    override fun computeScroll() {
        if (!isInEditMode && dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val motionEvent = ev.let { it } ?: return false
        if (!isEnabled) {
            return false
        }
        val action = motionEvent.action

        return when (action) {
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                dragHelper.cancel()
                false
            }
            MotionEvent.ACTION_DOWN -> {
                val index = motionEvent.actionIndex
                activePointerId = motionEvent.getPointerId(index)
                if (activePointerId == INVALID_POINTER) {
                    false
                } else dragHelper.shouldInterceptTouchEvent(motionEvent)
            }
            else -> dragHelper.shouldInterceptTouchEvent(motionEvent)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val dragView = dragView.let { it } ?: return false
        val motionEvent = event.let { it } ?: return false
        val actionMasked = motionEvent.actionMasked

        if ((actionMasked and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            activePointerId = motionEvent.getPointerId(actionMasked)

        }
        if (activePointerId == INVALID_POINTER) {
            return false
        }
        dragHelper.processTouchEvent(motionEvent)
        return isViewTarget(dragView, motionEvent) || isViewTarget(shadowView, motionEvent)
    }

    private fun isViewTarget(view: View, event: MotionEvent): Boolean {
        val viewLocation = IntArray(2)
        view.getLocationOnScreen(viewLocation)
        val parentLocation = IntArray(2)
        getLocationOnScreen(parentLocation)
        val screenX = parentLocation[0] + event.x
        val screenY = parentLocation[1] + event.y
        return screenX >= viewLocation[0]
                && screenX < viewLocation[0] + view.width
                && screenY >= viewLocation[1]
                && screenY < viewLocation[1] + view.height
    }
}
