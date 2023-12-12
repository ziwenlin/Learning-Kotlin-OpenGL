package org.example.opengl.renderer.shaders

import org.example.opengl.window
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL33

fun createShaderProgram(vertexShaderSource: String, fragmentShaderSource: String, geometryShaderSource: String): Int {

    val vertexShader = createVertexShader(vertexShaderSource)
    val fragmentShader = createFragmentShader(fragmentShaderSource)
    val geometryShader = createGeometryShader(geometryShaderSource)
    val shaderProgram = linkShaderProgram(vertexShader, fragmentShader, geometryShader)

    GL30.glDeleteShader(vertexShader)
    GL30.glDeleteShader(fragmentShader)
    GL30.glDeleteShader(geometryShader)
    return shaderProgram
}

fun createGeometryShader(geometryShaderSource: String): Int {

    val geometryShader = GL30.glCreateShader(GL33.GL_GEOMETRY_SHADER)
    GL20.glShaderSource(geometryShader, geometryShaderSource)
    GL30.glCompileShader(geometryShader)

    val success = GL30.glGetShaderi(geometryShader, GL30.GL_COMPILE_STATUS)
    if (success != GL30.GL_TRUE) {
        val length = GL30.glGetShaderi(geometryShader, GL30.GL_INFO_LOG_LENGTH)
        val info = GL20.glGetShaderInfoLog(geometryShader, length)
        println(Exception(info))
        window.exit(-1)
    }
    return geometryShader
}

fun linkShaderProgram(vertexShader: Int, fragmentShader: Int, geometryShader: Int): Int {

    val shaderProgram = GL30.glCreateProgram()
    GL30.glAttachShader(shaderProgram, vertexShader)
    GL30.glAttachShader(shaderProgram, fragmentShader)
    GL30.glAttachShader(shaderProgram, geometryShader)
    GL30.glLinkProgram(shaderProgram)

    val success = GL30.glGetProgrami(shaderProgram, GL30.GL_LINK_STATUS)
    if (success != GL30.GL_TRUE) {
        val length = GL30.glGetProgrami(shaderProgram, GL30.GL_INFO_LOG_LENGTH)
        val info = GL20.glGetProgramInfoLog(shaderProgram, length)
        println(Exception(info))
        window.exit(-1)
    }
    return shaderProgram
}