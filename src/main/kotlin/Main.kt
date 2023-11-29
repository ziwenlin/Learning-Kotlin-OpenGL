/*
* Main
*
* */

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*
import org.lwjgl.opengl.GL30.*
import kotlin.math.sin

val window = Window()
val width: Int = 1280
val height: Int = 720

fun main(args: Array<String>) {
    println("Hello World!")
    println("Program arguments: ${args.joinToString()}")

    window.init()
    val program = loop()
    window.loop(program)
    window.close(0)
}

fun loop() {
    // Create shader program
    val shaderProgram = createShaderProgram(vertexShaderSource, fragmentShaderSource)
    val shaderColorLocation = glGetUniformLocation(shaderProgram, "vColor")

    val vaoID = createElementVertexArray(vertices, indices)

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

        glBindVertexArray(vaoID)
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0)
        glBindVertexArray(0)
    }
    return program
}
