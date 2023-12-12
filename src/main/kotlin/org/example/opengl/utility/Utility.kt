package org.example.opengl.utility

import java.io.File

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

fun checkExistanceFile(currentFile: File): Boolean {
    val parentFile = currentFile.parentFile
    if (parentFile.exists() == false) {
        parentFile.mkdirs()
        if (parentFile.exists() == false) {
            println("Folder ${parentFile.path} does not exist. Failed to generate new folder.")
            return false
        }
    }
    if (currentFile.exists() == false) {
        currentFile.createNewFile()
        if (currentFile.exists() == false) {
            println("File ${currentFile.path} does not exist. Failed to generate new file.")
            return false
        }
    }
    return true
}
