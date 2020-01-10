package com.example.simplegraphview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class SimpleGraphView: View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val axisPaint: Paint
    private val axisTextPaint: Paint
    private val graphPaint: Paint

    private val startPoint: PointF = PointF()
    private val axisYEndPoint: PointF = PointF()
    private val axisXEndPoint: PointF = PointF()

    private val axisYPath = Path()
    private val axisXPath = Path()
    private val graphPath = Path()

    private val graphPoints = mutableListOf<PointF>()

    private var axisYName = ""
    private var axisXName = ""

    private var unitXLength = 1f
    private var unitYLength = 1f


    init {

        axisPaint = Paint().apply {
            color= Color.CYAN
            strokeWidth = 6f
            textSize = 40f
            style = Paint.Style.STROKE
        }

        graphPaint = Paint().apply {
            color= Color.GREEN
            strokeWidth = 1f
            style = Paint.Style.STROKE
        }

        axisTextPaint = Paint().apply {
            color= Color.YELLOW
            strokeWidth = 3f
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        startPoint.x = width * 0.07f
        startPoint.y = height * 0.93f

        axisYEndPoint.x = startPoint.x
        axisYEndPoint.y = height * 0.03f

        axisXEndPoint.x = width * 0.97f
        axisXEndPoint.y = startPoint.y

        if(graphPoints.size >= 2) {
            calculateUnitsLength(graphPoints.maxBy { it.x }!!.x, graphPoints.maxBy { it.y }!!.y)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return

        canvas.drawRGB(40, 40, 40)
        drawAxises(canvas)
        drawGraph(canvas)
    }

    private fun drawAxises(canvas: Canvas) {

        //Drawing Y axis
        axisYPath.reset()
        axisYPath.moveTo(startPoint.x, startPoint.y)
        axisYPath.lineTo(axisYEndPoint.x, axisYEndPoint.y)
        canvas.drawPath(axisYPath, axisPaint)
        canvas.drawLine(axisYEndPoint.x, axisYEndPoint.y, axisYEndPoint.x - 12, axisYEndPoint.y + 40, axisPaint )
        canvas.drawLine(axisYEndPoint.x, axisYEndPoint.y, axisYEndPoint.x + 12, axisYEndPoint.y + 40, axisPaint )
        canvas.drawTextOnPath(axisYName, axisYPath, 0f, -15f, axisTextPaint)

        //drawing X axis
        axisXPath.reset()
        axisXPath.moveTo(startPoint.x, startPoint.y)
        axisXPath.lineTo(axisXEndPoint.x, axisXEndPoint.y)
        canvas.drawPath(axisXPath, axisPaint)
        canvas.drawLine(axisXEndPoint.x, axisXEndPoint.y, axisXEndPoint.x - 40, axisXEndPoint.y - 12, axisPaint)
        canvas.drawLine(axisXEndPoint.x, axisXEndPoint.y, axisXEndPoint.x - 40, axisXEndPoint.y + 12, axisPaint)
        canvas.drawTextOnPath(axisXName, axisXPath, 0f, 45f, axisTextPaint)
    }

    private fun drawGraph(canvas: Canvas) {
        if(graphPoints.size < 2) return

        graphPath.reset()
        graphPath.moveTo(startPoint.x + graphPoints[0].x * unitXLength, startPoint.y - graphPoints[0].y * unitYLength)

        for ( i in 1 until graphPoints.size) {
            graphPath.lineTo(startPoint.x + graphPoints[i].x * unitXLength, startPoint.y - graphPoints[i].y * unitYLength)
        }

        canvas.drawPath(graphPath, graphPaint)
    }

    private fun calculateUnitsLength(xMax: Float, yMax: Float) {
        val lengthX = axisXEndPoint.x - startPoint.x - 20
        val lengthY = startPoint.y - axisYEndPoint.y - 20

        unitXLength = lengthX / xMax
        unitYLength = lengthY / yMax
    }

    fun showGraph(data: Map<Float, Float>, axisXName: String, axisYName: String) {
        if (data.size < 2) return

        calculateUnitsLength(data.keys.max()!!, data.values.max()!!)

        this.axisXName = axisXName
        this.axisYName = axisYName

        graphPoints.clear()
        data.entries.forEach {
            graphPoints.add(PointF(it.key, it.value))
        }
        graphPoints.sortBy { it.x }
    }

}