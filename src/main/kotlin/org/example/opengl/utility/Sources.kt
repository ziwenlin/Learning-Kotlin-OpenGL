package org.example.opengl.utility

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun circleIndices3D(precision: Int): IntArray {
    val list = mutableListOf<Int>()
    val range = precision * 2
    val bottom = (precision - 2) * range
    for (i in 1..range) {
        list.add(0)
        list.add(1 + i)
        list.add(2 + i % range)
    }
    for (i in 1..range) {
        list.add(1)
        list.add(1 + bottom + i)
        list.add(2 + bottom + i % range)
    }
    for (j in 0..precision - 2) {
        for (i in 1..range) {
            list.add(2 + j * range + i)
            list.add(3 + j * range + i % range)
            list.add(2 + (j + 1) * range + i)

            list.add(3 + j * range + i % range)
            list.add(2 + (j + 1) * range + i)
            list.add(3 + (j + 1) * range + i % range)
        }
    }
    return list.toIntArray()
}

fun circleVertices3D(precision: Int): FloatArray {
    val list = mutableListOf(0.0f, 0.0f, 0.5f, 0.0f, 0.0f, -0.5f)
    val range = precision * 2
    for (j in 1 until precision) {
        val z = cos(PI * j / precision).toFloat()
        for (i in 0 until range) {
            val x = cos(PI * i / precision).toFloat() * sin(PI * j / precision).toFloat()
            val y = sin(PI * i / precision).toFloat() * sin(PI * j / precision).toFloat()
            list.add(x * 0.5f)
            list.add(y * 0.5f)
            list.add(z * 0.5f)
        }
    }
    return list.toFloatArray()
}

fun circleIndices2D(precision: Int): IntArray {
    val list = mutableListOf<Int>()
    val range = precision * 2
    for (i in 1..range) {
        list.add(0)
        list.add(i)
        list.add(1 + i % range)
    }
    return list.toIntArray()
}

fun circleVertices2D(precision: Int): FloatArray {
    val list = mutableListOf<Float>(0.0f, 0.0f, 0.0f)
    for (i in 0..precision * 2) {
        list.add(cos(PI * i / precision).toFloat() * 0.5f)
        list.add(sin(PI * i / precision).toFloat() * 0.5f)
        list.add(0.0f)
    }
    return list.toFloatArray()
}

fun circleVerticesSimple2D(precision: Int): FloatArray {
    val list = mutableListOf<Float>()
    for (i in 0..precision * 2) {
        list.add(0.0f)
        list.add(0.0f)
        list.add(0.0f)
        list.add(sin(PI * i / precision).toFloat() * 0.5f)
        list.add(cos(PI * i / precision).toFloat() * 0.5f)
        list.add(0.0f)
        list.add(sin(PI * (i + 1) / precision).toFloat() * 0.5f)
        list.add(cos(PI * (i + 1) / precision).toFloat() * 0.5f)
        list.add(0.0f)
    }
    return list.toFloatArray()
}

val coordinates3D = arrayOf(
    floatArrayOf(0.0f, 0.0f, 0.0f),
    floatArrayOf(2.0f, 5.0f, -15.0f),
    floatArrayOf(-1.5f, -2.2f, -2.5f),
    floatArrayOf(-3.8f, -2.0f, -12.3f),
    floatArrayOf(2.4f, -0.4f, -3.5f),
    floatArrayOf(-1.7f, 3.0f, -7.5f),
    floatArrayOf(1.3f, -2.0f, -2.5f),
    floatArrayOf(1.5f, 2.0f, -2.5f),
    floatArrayOf(1.5f, 0.2f, -1.5f),
    floatArrayOf(-1.3f, 1.0f, -1.5f)
)

val boxVertices3D = floatArrayOf(
    -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
    0.5f, -0.5f, -0.5f, 1.0f, 0.0f,
    0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
    0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
    -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
    -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
    -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
    0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
    -0.5f, 0.5f, 0.5f, 0.0f, 1.0f,
    -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
    -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
    -0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
    -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
    -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
    -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
    -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
    0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
    0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
    0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
    0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
    -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
    0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
    0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
    0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
    -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
    -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
    -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
    0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
    -0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
    -0.5f, 0.5f, -0.5f, 0.0f, 1.0f

)

val boxVerticesTextured = floatArrayOf(
    // positions    // colors   // texture coords
    0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, // top right
    0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, // bottom right
    -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // bottom left
    -0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f // top left
)

val boxVertices = floatArrayOf(
    0.5f, 0.5f, 0.0f, // top right
    0.5f, -0.5f, 0.0f, // bottom right
    -0.5f, -0.5f, 0.0f, // bottom left
    -0.5f, 0.5f, 0.0f // top left
)

val boxIndices = intArrayOf(
    0, 1, 3, // first triangle
    1, 2, 3 // second triangle
)

val triangleVertices = floatArrayOf(
    -0.5f, -0.5f, 0.0f, // bottom left
    0.0f, -0.5f, 0.0f, // bottom center
    -0.25f, 0.5f, 0.0f, // top left-center
    0.0f, -0.5f, 0.0f, // bottom center
    0.5f, -0.5f, 0.0f, // bottom right
    0.25f, 0.5f, 0.0f, // top right-center
)

val triangleVertices2 = floatArrayOf(
    -0.5f, 0.5f, 0.0f, 1.0f, 0.2f, 0.2f, // bottom left
    0.0f, 0.5f, 0.0f, 0.2f, 1.0f, 0.2f, // bottom center
    -0.25f, -0.5f, 0.0f, 0.2f, 0.2f, 1.0f, // top left-center
    0.0f, 0.5f, 0.0f, 0.2f, 1.0f, 0.2f, // bottom center
    0.5f, 0.5f, 0.0f, 0.2f, 0.2f, 1.0f, // bottom right
    0.25f, -0.5f, 0.0f, 1.0f, 0.2f, 0.2f // top right-center
)