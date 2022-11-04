package com.fernando.test.ui.movie

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException
import kotlin.math.round

class DividerItemDecoration(private val context: Context,
                            private val orientation: Int,
                            private val hideLast: Boolean = false): RecyclerView.ItemDecoration(){

    private lateinit var mDivider: Drawable

    private var mOrientation = 0

    private val mBounds = Rect()

    private val attrs = intArrayOf(android.R.attr.listDivider)

    init {
        init()
    }
    @SuppressLint("Recycle")
    private fun init(){
        val a = context.obtainStyledAttributes(attrs)
        mDivider = a.getDrawable(0)!!
        a.recycle()
        setOrientation(orientation)
    }


    private fun setOrientation(orientation: Int){
        when(orientation){
            HORIZONTAL, VERTICAL ->{
                mOrientation = orientation
            }
            else -> throw IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL")
        }
    }

    fun setDrawable(drawable: Drawable){
        mDivider = drawable
    }

    fun getDrawable() = mDivider

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        parent.layoutManager ?: return
        when(mOrientation){
            VERTICAL -> c.drawVertical(parent)
            HORIZONTAL -> c.drawHorizontal(parent)
        }

    }

    private fun Canvas.drawVertical(parent: RecyclerView){
        save()
        var left = 0
        var right = 0

        when(parent.clipToPadding){
            true -> {
                left = parent.paddingLeft
                right = parent.width - parent.paddingRight
                clipRect(left, parent.paddingTop, right,
                    parent.height - parent.paddingBottom)
            }

            false -> {
                left = 0
                right = parent.right
            }
        }

        var drawCount = parent.childCount
        if(hideLast) drawCount--

        (0 until  drawCount).forEach {
            val child = parent.getChildAt(it)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom = mBounds.bottom + round(child.translationY).toInt()
            val top = bottom - mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(this)
        }
        restore()
    }

    private fun Canvas.drawHorizontal(parent: RecyclerView){
        save()
        var top = 0
        var bottom = 0

        when(parent.clipToPadding){
            true -> {
                top = parent.paddingTop
                bottom = parent.height - parent.paddingBottom
                val paint = Paint()
                paint.color = Color.WHITE
                drawCircle(parent.paddingLeft.toFloat()+10, top.toFloat()+10,10f,paint)
                clipRect(parent.paddingLeft, top,
                    parent.width - parent.paddingRight, bottom)
            }

            false -> {
                top = 0
                bottom = parent.height
            }
        }

        var drawCount = parent.childCount

        if(hideLast) drawCount--

        (0 until  drawCount).forEach {
            val child = parent.getChildAt(it)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val right = mBounds.right + round(child.translationX).toInt()
            val left = right - mDivider.intrinsicWidth
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(this)
        }
        restore()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when(mOrientation == VERTICAL){
            true -> {
                outRect.set(0, 0, 0, mDivider.intrinsicHeight)
            }
            false -> {
                outRect.set(0, 0, mDivider.intrinsicWidth, 0)
            }
        }
    }

    companion object{
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL
    }
}