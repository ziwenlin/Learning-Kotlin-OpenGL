package org.example.opengl.renderer

import org.example.opengl.constructor.Destroyable
import org.example.opengl.renderer.shaders.createShaderProgram
import org.example.opengl.window
import org.lwjgl.opengl.GL30


class Shader : Destroyable {
    private var programID = 0
    private val uniformMap = mutableMapOf<String, Int>()

    constructor(vertexShaderPath: String, fragmentShaderPath: String) {
        val vertexShaderSource = javaClass.getResource(vertexShaderPath)?.readText()
        val fragmentShaderSource = javaClass.getResource(fragmentShaderPath)?.readText()
        if (vertexShaderSource == null || fragmentShaderSource == null) {
            println("Vertex shader or fragment shader not found!")
            println("Vertex shader source error: ${vertexShaderSource == null}")
            println("Fragment shader source error: ${fragmentShaderSource == null}")
            window.exit(-1)
        } else {
            programID = createShaderProgram(vertexShaderSource, fragmentShaderSource)
        }
    }

    constructor(vertexShaderPath: String, fragmentShaderPath: String, geometryShaderPath: String) {
        val vertexShaderSource = javaClass.getResource(vertexShaderPath)?.readText()
        val fragmentShaderSource = javaClass.getResource(fragmentShaderPath)?.readText()
        val geometryShaderSource = javaClass.getResource(geometryShaderPath)?.readText()
        if (vertexShaderSource == null || fragmentShaderSource == null || geometryShaderSource == null) {
            println("Vertex shader or fragment shader not found!")
            println("Vertex shader source error: ${vertexShaderSource == null}")
            println("Fragment shader source error: ${fragmentShaderSource == null}")
            println("Geometry shader source error: ${geometryShaderSource == null}")
            window.exit(-1)
        } else {
            programID = createShaderProgram(vertexShaderSource, fragmentShaderSource, geometryShaderSource)
        }
    }

    private fun getUniformIndex(name: String): Int {
        val index = uniformMap[name]
        if (index == null) {
            println("There is no uniform called $name in shader $this ID: $programID")
            uniformMap[name] = -1
            return -1
        }
        return index
    }

    fun getUniformLocation(name: String): Int {
        val uniformLocationID = GL30.glGetUniformLocation(programID, name)
        uniformMap[name] = uniformLocationID
        return uniformLocationID
    }

    fun setUniform1i(name: String, value: Int) {
        val index = getUniformIndex(name)
        if (index == -1) return
        GL30.glUniform1i(index, value)
    }

    fun setUniform3f(name: String, v1: Float, v2: Float, v3: Float) {
        val index = getUniformIndex(name)
        if (index == -1) return
        GL30.glUniform3f(index, v1, v2, v3)
    }

    fun get(): Int {
        return programID
    }

    fun use() {
        GL30.glUseProgram(programID)
    }

    override fun destroy() {
        GL30.glDeleteShader(programID)
    }
}

