package renderer

import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import window

@Suppress("unused")
class Shader(vertexShaderPath: String, fragmentShaderPath: String) {
    private var programID = 0
    private val uniformMap = mutableMapOf<String, Int>()

    init {
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

    fun destroy() {
        GL30.glDeleteShader(programID)
    }
}

fun createShaderProgram(vertexShaderSource: String, fragmentShaderSource: String): Int {

    val vertexShader = createVertexShader(vertexShaderSource)
    val fragmentShader = createFragmentShader(fragmentShaderSource)
    val shaderProgram = linkShaderProgram(vertexShader, fragmentShader)

    GL30.glDeleteShader(vertexShader)
    GL30.glDeleteShader(fragmentShader)
    return shaderProgram
}

fun createFragmentShader(fragmentShaderSource: String): Int {

    val fragmentShader = GL30.glCreateShader(GL30.GL_FRAGMENT_SHADER)
    GL20.glShaderSource(fragmentShader, fragmentShaderSource)
    GL30.glCompileShader(fragmentShader)

    val success = GL30.glGetShaderi(fragmentShader, GL30.GL_COMPILE_STATUS)
    if (success != GL30.GL_TRUE) {
        val length = GL30.glGetShaderi(fragmentShader, GL30.GL_INFO_LOG_LENGTH)
        val info = GL20.glGetShaderInfoLog(fragmentShader, length)
        println(Exception(info))
        window.exit(-1)
    }
    return fragmentShader
}

fun createVertexShader(vertexShaderSource: String): Int {

    val vertexShader = GL30.glCreateShader(GL30.GL_VERTEX_SHADER)
    GL20.glShaderSource(vertexShader, vertexShaderSource)
    GL30.glCompileShader(vertexShader)

    val success = GL30.glGetShaderi(vertexShader, GL30.GL_COMPILE_STATUS)
    if (success != GL30.GL_TRUE) {
        val length = GL30.glGetShaderi(vertexShader, GL30.GL_INFO_LOG_LENGTH)
        val info = GL20.glGetShaderInfoLog(vertexShader, length)
        println(Exception(info))
        window.exit(-1)
    }
    return vertexShader
}

fun linkShaderProgram(vertexShader: Int, fragmentShader: Int): Int {

    val shaderProgram = GL30.glCreateProgram()
    GL30.glAttachShader(shaderProgram, vertexShader)
    GL30.glAttachShader(shaderProgram, fragmentShader)
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

