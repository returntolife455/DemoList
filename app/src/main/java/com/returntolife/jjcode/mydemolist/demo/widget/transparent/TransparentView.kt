package com.returntolife.jjcode.mydemolist.demo.widget.transparent

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/**
 *@author: hejiajun02@lizhi.fm
 *@date: 3/10/23
 *des:
 */
class TransparentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)


    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
    }



    private val mGradientColors = intArrayOf(-0x1, 0x00000000, -0x1)
    private val mGradientPosition = floatArrayOf(0f, 0.5f, 1f)

    init {
        mPaint.style = Paint.Style.FILL
        mPaint.xfermode =
            PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    }




    override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {

        if (mPaint.shader == null) {
            mPaint.shader = LinearGradient(
                0f,
                0f,
                0f,
                height.toFloat(),
                mGradientColors,
                mGradientPosition,
                Shader.TileMode.CLAMP
            )


        }

        val layerSave = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        val drawChild = super.drawChild(canvas, child, drawingTime)

        val save = canvas.save()

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
        canvas.restoreToCount(save)
        canvas.restoreToCount(layerSave)
        return drawChild
    }


}