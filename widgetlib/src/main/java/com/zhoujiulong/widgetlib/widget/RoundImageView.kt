package com.zhoujiulong.widgetlib.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import com.zhoujiulong.widgetlib.R

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Day : 2018/12/24
 * 描述 : 自定义圆角 ImageView，
 * 圆角规则：如果定义了 allRadius 又定义了单个的圆角取单个的圆角，没单独设置的取 allRadius
 */
class RoundImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : android.support.v7.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private val rids = floatArrayOf(50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f)//默认圆角半径为50：轮播图使用
    private val mPath: Path = Path()

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
            val allRadius = ta.getDimensionPixelOffset(R.styleable.RoundImageView_all_radius, 0)

            var topLeftRadius =
                ta.getDimensionPixelOffset(R.styleable.RoundImageView_top_left_radius, 0)
            topLeftRadius = if (topLeftRadius > 0) topLeftRadius else allRadius
            rids[0] = topLeftRadius.toFloat()
            rids[1] = topLeftRadius.toFloat()

            var topRightRadius =
                ta.getDimensionPixelOffset(R.styleable.RoundImageView_top_right_radius, 0)
            topRightRadius = if (topRightRadius > 0) topRightRadius else allRadius
            rids[2] = topRightRadius.toFloat()
            rids[3] = topRightRadius.toFloat()

            var bottomRightRadius =
                ta.getDimensionPixelOffset(R.styleable.RoundImageView_bottom_right_radius, 0)
            bottomRightRadius = if (bottomRightRadius > 0) bottomRightRadius else allRadius
            rids[4] = bottomRightRadius.toFloat()
            rids[5] = bottomRightRadius.toFloat()

            var bottomLeftRadius =
                ta.getDimensionPixelOffset(R.styleable.RoundImageView_bottom_left_radius, 0)
            bottomLeftRadius = if (bottomLeftRadius > 0) bottomLeftRadius else allRadius
            rids[6] = bottomLeftRadius.toFloat()
            rids[7] = bottomLeftRadius.toFloat()

            ta.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        val w = this.width
        val h = this.height
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        mPath.addRoundRect(RectF(0f, 0f, w.toFloat(), h.toFloat()), rids, Path.Direction.CW)
        canvas.clipPath(mPath)
        super.onDraw(canvas)
    }

    fun setAllRadius(radius: Int) {
        for (i in rids.indices) {
            rids[i] = radius.toFloat()
        }
        postInvalidate()
    }

}
