/*
* Main
*
* */

import org.lwjgl.glfw.GLFW.glfwGetTime
import org.lwjgl.opengl.GL30.*
import kotlin.math.*

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

        // Mathematical functions values based on time
        val sawState = if (timeValue % 4.0f < 2.0f) 1 else -1
        val sawTooth = ((timeValue % 2.0f - 1.0f) * sawState).toFloat()
        val sawSharp = sawTooth * sawTooth * sawTooth
        val sineWave = sin(timeValue / PI * 2.0f).toFloat()
        val cosWave = cos(timeValue / PI * 2.0f).toFloat()

        // Mathematical transition effects
        val sawValue = sawSharp / 2.0f + 0.5f
        val sinValue = sineWave / 2.0f + 0.5f
        val cosValue = cosWave / 2.0f + 0.5f

        // Render here
        glClear(GL_COLOR_BUFFER_BIT)

        // Render the objects
        glUseProgram(shaderProgram)
        glUniform3f(shaderPositionLocation, 0.5f * cosWave, 0.5f * sineWave, 0f)
        glUniform3f(shaderColorLocation, 0.8f * cosValue, 0.8f * cosValue * sinValue, 0.8f * sinValue)
        squareRenderObject.draw()

        // Render triangle objects
        glUseProgram(shaderProgram2)
        glUniform3f(shaderPositionLocation2, 0.0f, 0.5f * sawSharp, 0.0f)
        glUniform3f(shaderColorLocation2, 0.2f * sawValue, 1.0f * sawValue, 0.2f * sawValue)
        triangleRenderObject.draw()
    }
    return program
}
