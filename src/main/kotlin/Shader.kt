import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

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
        println(info)
        println(Error(info))
        close(-1)
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
        println(info)
        println(Error(info))
        close(-1)
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
        println(info)
        println(Error(info))
        close(-1)
    }
    return shaderProgram
}

