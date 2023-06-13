package com.dokiwei.zshg.tool.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.lang.Float.min
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * @description: 蜘蛛网雷达折线图
 * @author: 三石
 * @date: 2023/3/13 17:30
 *
 * @param dataList 需要绘制的数据列表
 * @param labelList 数据列表对应的标签
 * @param layerNum 绘制蛛网的层数
 * @param maxData 最外层蛛网代表的最大值，为空则取 dataList 中的最大值
 */
@Composable
fun SpiderWebRadarLineDiagram(
    modifier: Modifier,
    dataList: List<Float>,
    labelList: List<String> = listOf("力量", "体质", "感知", "意志", "敏捷", "技巧"),
    layerNum: Int = 3,
    maxData: Float = 1.3f,
    lineRadius: Float = 0f
) {
    //drawText()绘制文本要用到
    val textMeasurer = rememberTextMeasurer()
    //获取协程作用域
    val coroutineScope = rememberCoroutineScope()
    //记录计算的旋转角度
    var rotation by remember { mutableFloatStateOf(-120f) }
    //记录手指每次移动的起始点
    var startPoint by remember { mutableStateOf(Offset.Zero) }
    //记录手指每次移动的终点
    var endPoint by remember { mutableStateOf(Offset.Zero) }
    //记录Canvas在大小确定时的中心点
    var centerPoint by remember { mutableStateOf(Offset.Zero) }
    //drag最后一次的速度，作为fling开始的速度
    var flingStartSpeed by remember { mutableFloatStateOf(0f) }
    //手指松开后的惯性旋转角度
    val flingRotation = remember { Animatable(0f) }
    //定义衰减动画的衰减属性，指数衰减、摩擦力和临界值
    val exponentDecay = exponentialDecay<Float>(0.5f, 1f)
    //记录上一次onDrag的时间，用于计算两次onDrag的间隔时间
    var lastOnDragTime by remember { mutableLongStateOf(0L) }
    val mainColor = MaterialTheme.colorScheme.primary
    val labelColor = MaterialTheme.colorScheme.onSurface

    Canvas(modifier = modifier
        .onSizeChanged {
            //记录Canvas中心点坐标
            centerPoint = Offset(it.width / 2f, it.height / 2f)
        }
        //手指拖动转动
        .pointerInput(Unit) {
            detectDragGestures(onDragStart = { point ->
                startPoint = point
                //新的拖动手势触发时，立刻停止上一次的fling
                coroutineScope.launch {
                    flingRotation.animateDecay(0f, exponentDecay)
                }
            }, onDragEnd = {
                //拖动手势结束时，开始fling
                coroutineScope.launch {
                    flingRotation.animateDecay(flingStartSpeed, exponentDecay)
                }
            }) { _, dragAmount ->
                endPoint = startPoint + dragAmount
                //这里Math.atan2函数对正负做了处理，所以不需要分象限处理
                (atan2(endPoint.y - centerPoint.y, endPoint.x - centerPoint.x) - atan2(
                    startPoint.y - centerPoint.y, startPoint.x - centerPoint.x
                )).let { radian ->
                    //弧度制转换成角度单位
                    Math
                        .toDegrees(radian.toDouble())
                        .toFloat()
                        .let { rota ->
                            rotation += rota
                            System
                                .currentTimeMillis()
                                .let { currentTime ->
                                    //计算每秒钟旋转的速度
                                    flingStartSpeed = rota * 1000 / (currentTime - lastOnDragTime)
                                    lastOnDragTime = currentTime
                                }
                        }
                }
                startPoint = endPoint
            }
        }
        //点击停止fling转动
        .pointerInput(Unit) {
            detectTapGestures {
                coroutineScope.launch {
                    flingRotation.animateDecay(0f, exponentDecay)
                }
            }
        }) {
        //计算多边形相接圆的半径
        val radius = min(size.height, size.width) * 0.38f
        //计算多边形相邻顶点的圆心角
        val roteStep = 360f / 6
        rotate(rotation + flingRotation.value) {
            //画顶点
            drawSpiderWebPoints(roteStep, radius, mainColor)
            //画蛛网
            drawSpiderWeb(layerNum, roteStep, radius, mainColor)
            //画data的线
            drawDataLine(roteStep, dataList, radius, maxData, mainColor,lineRadius)
        }
        //画标签文本
        drawParamLabel(
            roteStep, radius, textMeasurer, labelList, rotation + flingRotation.value, labelColor
        )
    }
}

/**
 * 绘制多边形顶点
 * @param roteStep 相邻顶点的圆心角
 * @param radius 相接圆半径
 */
private fun DrawScope.drawSpiderWebPoints(
    roteStep: Float, radius: Float, color: Color
) {
    val pointsList = mutableListOf<Offset>()
    (0 until 6).forEach {
        //计算各个顶点坐标
        val (x, y) = calculateXY(roteStep * it, radius)
        pointsList.add(Offset(x, y))
    }
    drawPoints(
        pointsList,
        PointMode.Points,
        color,
        strokeWidth = 0f,
        pathEffect = PathEffect.cornerPathEffect(0f)
    )
}

/**
 * 绘制蛛网
 *
 * @param roteStep 相邻顶点与中心点构成的角度
 * @param radius 最外层顶点所在圆的半径
 * @param layerNum 总层数
 */
private fun DrawScope.drawSpiderWeb(
    layerNum: Int, roteStep: Float, radius: Float, color: Color
) {
    (1..layerNum).forEach {
        //画每一层网络
        drawOneLayerCobweb(roteStep, radius, it, layerNum, color)
    }
}

/**
 * 绘制蛛网的每一层（多边形）
 *
 * @param roteStep 相邻顶点与中心点构成的角度
 * @param radius 最外层顶点所在圆的半径
 * @param currentLayer 当前层数
 * @param layerNum 总层数
 */
private fun DrawScope.drawOneLayerCobweb(
    roteStep: Float, radius: Float, currentLayer: Int, layerNum: Int, color: Color
) {
    val path = Path()
    (0 until 6).forEach {
        //计算各个顶点坐标
        val (x, y) = calculateXY(roteStep * it, radius * currentLayer / layerNum)
        //是最外层时，画顶点与圆心的连线
        if (currentLayer == layerNum) {
            drawLine(color, Offset(x, y), center)
        }

        //相邻顶点连线
        if (it == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
        if (it == 5) {
            path.close()
        }
    }

    drawPath(path, color, style = Stroke())
}

/**
 * 绘制数据的线
 *
 * @param roteStep 相邻顶点与中心点构成的角度
 * @param dataList 需要绘制的数据列表
 * @param radius 最大圆半径
 * @param maxData 数据范围的最大值，即最外层蛛网代表的值
 */
private fun DrawScope.drawDataLine(
    roteStep: Float, dataList: List<Float>, radius: Float, maxData: Float, color: Color, lineRadius:Float
) {
    val dataPath = Path()
    (0 until 6).forEach {
        val (x, y) = calculateXY(roteStep * it, dataList[it] * radius / maxData)
        //画数据的各个点
        drawCircle(color, lineRadius, Offset(x, y))

        if (it == 0) {
            dataPath.moveTo(x, y)
        } else {
            dataPath.lineTo(x, y)
        }
        if (it == 5) {
            dataPath.close()
        }
    }
    drawPath(dataPath, color, 0.7f, style = Fill)
}


/**
 * 绘制标签文本
 *
 * @param roteStep 相邻顶点与中心点构成的角度
 * @param radius 当前层顶点所在圆的半径
 * @param textMeasurer TextMeasure
 * @param labelList 存储标签文本的列表
 * @param rotation 当前蛛网图旋转的角度
 */
private fun DrawScope.drawParamLabel(
    roteStep: Float,
    radius: Float,
    textMeasurer: TextMeasurer,
    labelList: List<String>,
    rotation: Float,
    color: Color
) {
    (0 until 6).forEach {
        //计算文本需要绘制的坐标
        val (x, y) = calculateXYByRadian(
            Math.toRadians(roteStep * it.toDouble() + rotation.toDouble()), radius * 1.15f
        )
        //计算要绘制的文本的TextLayoutResult
        val measuredText = textMeasurer.measure(
            AnnotatedString(labelList[it]), TextStyle(
                color = color, fontSize = (radius / 20).sp
            )
        )
        //绘制文本
        drawText(
            measuredText,
            topLeft = Offset(x - measuredText.size.width / 2, y - measuredText.size.height / 2)
        )
    }
}

/**
 * 根据角度计算坐标
 *
 * @param rotation 角度
 * @param radius 半径
 */
private fun DrawScope.calculateXY(
    rotation: Float, radius: Float
): Pair<Float, Float> {
    //将角度单位转换，如180度转换成Pi
    val radian = Math.toRadians(rotation.toDouble())
    return calculateXYByRadian(radian, radius)
}

/**
 * 根据弧度计算坐标
 *
 * @param radius 半径
 * @param radian 弧度
 */
private fun DrawScope.calculateXYByRadian(
    radian: Double, radius: Float
): Pair<Float, Float> {
    val x = (radius * cos(radian) + center.x).toFloat()
    val y = (radius * sin(radian) + center.y).toFloat()
    return Pair(x, y)
}

@Preview(showBackground = true)
@Composable
fun SpiderWebRadarLineDiagramPreview() {
    SpiderWebRadarLineDiagram(
        modifier = Modifier.size(200.dp, 200.dp),
        dataList = listOf(1f, 0.2f, 0.8f, 0.4f, 1.2f, 0.6f),
        lineRadius = 8f
    )
}
