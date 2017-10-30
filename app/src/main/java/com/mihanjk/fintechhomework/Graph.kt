package com.mihanjk.fintechhomework

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View


class Graph(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var mLineColor: Int
    private var mXAxisTitle: String?
    private var mYAxisTitle: String?
    private var mTextPaint: Paint
    private var mLinePaint: Paint
    private var mRect: RectF
    private var mTextSize: Float
    private var mStrokeWidth: Float
    private var mGrid: Boolean
    private var mSteps: Boolean
    private var mCornerSize = 16

    var mValues: List<Pair<Float, Float>>? = null
        set(value) {
            field = value
            val array = mutableListOf<Float>()
            if (value != null) {
                val minX = value.minBy { it.first }?.first
                val minY = value.minBy { it.second }?.second
                val maxX = value.maxBy { it.first }?.first
                val maxY = value.maxBy { it.second }?.second
                val rangeX = maxX - minX
                val rangeY = maxY - minY

                value.reduce { acc, pair ->
                    array.addAll(listOf(acc.first, acc.second, pair.first, pair.second))
                    pair
                }
            }
            mLineDots = array.toTypedArray().toFloatArray()
        }

    lateinit var mLineDots: FloatArray

    init {
        val attributes = context.theme.obtainStyledAttributes(attributeSet, R.styleable.Graph, 0, 0)
        mLineColor = attributes.getColor(R.styleable.Graph_line_color, Color.BLUE)
        mXAxisTitle = attributes.getString(R.styleable.Graph_title_x_axis)
        mYAxisTitle = attributes.getString(R.styleable.Graph_title_x_axis)
        mTextSize = attributes.getDimension(R.styleable.Graph_text_size, 120f)
        mStrokeWidth = attributes.getDimension(R.styleable.Graph_stroke_width, 16f)
        mGrid = attributeSet.getAttributeBooleanValue(R.styleable.Graph_grid, false)
        mSteps = attributeSet.getAttributeBooleanValue(R.styleable.Graph_steps, false)
        attributes.recycle()

        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.color = Color.BLACK
        mTextPaint.textSize = mTextSize

        mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mLinePaint.color = mLineColor
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.strokeWidth = mStrokeWidth
        mRect = RectF(0f, 0f, width.toFloat(), height.toFloat())

    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val minw = (paddingLeft + paddingRight + 2 * mTextPaint.textSize).toInt()
//    val w = resolveSizeAndState(minw, widthMeasureSpec, 1)
//        val minh = ((w - mTextPaint.textSize) + paddingBottom + paddingTop).toInt()
//        val h = resolveSizeAndState(MeasureSpec.getSize(w) - mTextPaint.textSize.toInt(), heightMeasureSpec, 0)
//        setMeasuredDimension(w, h)
//    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            val border = 2 * mTextSize
            val width = canvas.width.toFloat() - border
            val height = canvas.height.toFloat() - border
            val cornerX = border + mCornerSize
            val cornerY = border + mCornerSize
            val cornerWidth = width - mCornerSize
            val cornerHeight = height - mCornerSize

            canvas.drawRect(border, border, width, height, mLinePaint)

//            canvas.drawText("TEST", top.toFloat() / 2, bottom.toFloat() / 2, mTextPaint)

//            canvas.drawLines(mLineDots, mLinePaint)

            mLinePaint.color = Color.BLACK
            IntRange(1, 6).forEach {
                canvas.drawLine(it * (cornerWidth - cornerX) / 6, cornerY,
                        it * (cornerWidth - cornerX) / 6, cornerHeight, mLinePaint)
                canvas.drawLine(cornerX, it * (cornerHeight - cornerY) / 6, cornerWidth,
                        it * (cornerHeight - cornerY) / 6, mLinePaint)
            }
        }
    }
}