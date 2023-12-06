package org.example.opengl.utility

fun addValuesToArray(array: FloatArray, stride: Int, value: Float, amount: Int): FloatArray {
    val list = arrayListOf<Float>()
    for ((i, v) in array.withIndex()) {
        list.add(v)
        if (i % stride == stride - 1)
            for (x in 1..amount)
                list.add(value)
    }
    return list.toFloatArray()
}