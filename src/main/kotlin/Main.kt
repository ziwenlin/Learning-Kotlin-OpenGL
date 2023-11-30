/*
* Main
*
* */

import org.lwjgl.glfw.GLFW.glfwGetTime
import org.lwjgl.opengl.GL30.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

val window = Window()
val width: Int = 1280
val height: Int = 720

fun main(args: Array<String>) {
    println("Hello World!")
    println("Program arguments: ${args.joinToString()}")

    window.init()
    val program = createProgram()
    window.loop(program)
    window.exit(0)
}

fun createProgram(): () -> Unit {
    // Create shader program
    val shaderProgram = createShaderProgram(vertexShaderSource, fragmentShaderSource)
    val shaderColorLocation = glGetUniformLocation(shaderProgram, "vColor")
    val shaderPositionLocation = glGetUniformLocation(shaderProgram, "vPos")

    val shaderProgram2 = createShaderProgram(vertexShaderSource, fragmentShaderSource)
    val shaderColorLocation2 = glGetUniformLocation(shaderProgram, "vColor")
    val shaderPositionLocation2 = glGetUniformLocation(shaderProgram, "vPos")

    // Create square render object
    val squareRenderObject = RenderObject(boxVertices, boxIndices)
    val triangleRenderObject = SimpleRenderObject(triangleVertices, 3)

    // Set the clear color
    glClearColor(0.1f, 0.3f, 0.3f, 1.0f)
    glViewport(0, 0, width, height)

    // Create main program
    val program = { ->
        // Get time since start
        val timeValue = glfwGetTime()
        val timeRad = timeValue * PI / 2.0f
        val timePhase = 2.0f / 3.0f * PI

        // Mathematical functions values based on time
        val sawState = if (timeValue % 4.0f < 2.0f) 1 else -1
        val sawTooth = ((timeValue % 2.0f - 1.0f) * sawState).toFloat()
        val sawSharp = sawTooth * sawTooth * sawTooth
        val cosWave = cos(timeRad).toFloat()
        val sineWave = sin(timeRad).toFloat()
        val sineWave2 = sin(timeRad - timePhase).toFloat()
        val sineWave3 = sin(timeRad + timePhase).toFloat()

        // Mathematical transition effects
        val sinValue = sineWave / 2.0f + 0.5f
        val sinValue2 = sineWave2 / 2.0f + 0.5f
        val sinValue3 = sineWave3 / 2.0f + 0.5f
        val cosValue = cosWave / 2.0f + 0.5f

        // Render here
        glClear(GL_COLOR_BUFFER_BIT)

        // Render the objects
        glUseProgram(shaderProgram)
        glUniform3f(shaderPositionLocation, 0.5f * cosWave, 0.5f * sineWave, 0f)
        glUniform3f(shaderColorLocation, 0.8f * sinValue, 0.8f * sinValue2, 0.8f * sinValue3)
        squareRenderObject.draw()

        // Render triangle objects
        glUseProgram(shaderProgram2)
        glUniform3f(shaderPositionLocation2, 0.0f, 0.5f * -sawSharp, 0.0f)
        glUniform3f(shaderColorLocation2, cosValue, cosValue, cosValue)
        triangleRenderObject.draw()
    }
    return program
}
