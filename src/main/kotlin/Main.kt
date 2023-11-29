/*
* Main
*
* */

import org.lwjgl.glfw.GLFW.glfwGetTime
import org.lwjgl.opengl.GL30.*
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

    // Create square render object
    val squareRenderObject = RenderObject(boxVertices, boxIndices)

    // Set the clear color
    glClearColor(0.1f, 0.3f, 0.3f, 1.0f)
    glViewport(0, 0, width, height)

    // Create main program
    val program = { ->
        // Get time since start
        val timeValue = glfwGetTime()
        val sineValue: Float = (sin(timeValue) / 2f + 0.5f).toFloat()

        // Render here
        glClear(GL_COLOR_BUFFER_BIT)
        glUseProgram(shaderProgram)
        glUniform3f(shaderColorLocation, 0.2f * sineValue, 1.0f * sineValue, 0.2f * sineValue)

        // Render the objects
        squareRenderObject.draw()
    }
    return program
}
