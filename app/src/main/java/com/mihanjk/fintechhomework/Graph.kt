package com.mihanjk.fintechhomework

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View


class Graph(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    var mLineColor: Int
    var mXAxisTitle: String?
    var mYAxisTitle: String?
    private var mTextPaint: Paint
    private var mLinePaint: Paint
    private var mRect: RectF
    var mTextSize: Float
    var mStrokeWidth: Float
    var mGrid: Boolean
    var mSteps: Boolean
    private var mCornerSize = 40
    private var mWidth = 0
    private var mHeight = 0
        set(value) {
            field = value
            val array = mutableListOf<Float>()
            if (mValues.isNotEmpty()) {
                val minX = mValues.minBy { it.first }!!.first
                val minY = mValues.minBy { it.second }!!.second
                val maxX = mValues.maxBy { it.first }!!.first
                val maxY = mValues.maxBy { it.second }!!.second
                mXRange = (maxX - minX)
                mYRange = (maxY - minY)

                val coefX = (mWidth - 2 * mBorder - 2 * mCornerSize) / mXRange
                val coefY = (mHeight - 2 * mBorder - 2 * mCornerSize) / mYRange

                mValues.map { Pair(it.first * coefX, it.second * coefY) }
                        .reduce { acc, pair ->
                            array.addAll(listOf(acc.second, acc.first, pair.second, pair.first))
                            pair
                        }
            }
            mLineDots = array.toTypedArray().toFloatArray()
        }


    private var mBorder = 0f
        get() = 3 * mTextSize
    private var mXRange = 0f
    private var mYRange = 0f

    lateinit var mValues: List<Pair<Float, Float>>

    private lateinit var mLineDots: FloatArray

    init {
        val attributes = context.theme.obtainStyledAttributes(attributeSet, R.styleable.Graph, 0, 0)
        mLineColor = attributes.getColor(R.styleable.Graph_line_color, Color.BLUE)
        mXAxisTitle = attributes.getString(R.styleable.Graph_title_x_axis)
        mYAxisTitle = attributes.getString(R.styleable.Graph_title_y_axis)
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

        mRect = RectF()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth
        mHeight = measuredHeight
        mRect.set(mBorder, mBorder, mWidth - mBorder, mHeight - mBorder)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            mLinePaint.color = Color.BLACK
            canvas.drawRect(mRect, mLinePaint)

            val cornerWidth = mWidth - mCornerSize - mBorder
            val cornerHeight = mHeight - mCornerSize - mBorder
            val corner = mBorder + mCornerSize
            val size = mValues.size
//            if (mGrid) {
                0.rangeTo(size).forEach { canvas.drawLine(corner+ it * (cornerWidth - corner) / size, mBorder,
                        corner+ it * (cornerWidth - corner) / size, mHeight - mBorder, mLinePaint)
                    canvas.drawLine(mBorder,corner+ it * (cornerHeight - corner) / size, mWidth - mBorder,
                            corner+ it * (cornerHeight - corner) / size, mLinePaint)
                }
//            }
            if (mSteps) {
                mTextPaint.textSize = 16f
                (0 until size).forEach { canvas.drawText(mValues[it].first.toString(), corner+ it * (cornerWidth - corner) / size,
                        mHeight - 2*mTextSize, mTextPaint)
//                    canvas.drawLine(mBorder,corner+ it * (cornerHeight - corner) / size, mWidth - mBorder,
//                            corner+ it * (cornerHeight - corner) / size, mLinePaint)
                }
            }
            mLinePaint.color = mLineColor

            if (mXAxisTitle != null) {
                canvas.drawText(mXAxisTitle, mWidth / 2f, mHeight.toFloat() - mTextSize, mTextPaint)
            }

            if (mYAxisTitle != null) {
                canvas.rotate(-90f, mTextSize, mHeight / 2f)
                canvas.drawText(mYAxisTitle, mTextSize, mHeight / 2f, mTextPaint)
                canvas.restore()
            }

            if (mLineDots.isNotEmpty()) {
                canvas.translate(mBorder + mCornerSize, mHeight - mBorder - mCornerSize)
                canvas.rotate(-90f)
                canvas.drawLines(mLineDots, mLinePaint)
                canvas.restore()
            }


            canvas.restore()
        }
    }
}