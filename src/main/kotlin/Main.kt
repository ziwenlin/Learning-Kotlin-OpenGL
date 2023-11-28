/*
* Main
*
* */

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil.NULL

fun main(args: Array<String>) {
    println("Hello World!")
    println("Program arguments: ${args.joinToString()}")

    val glfwInitialize = glfwInit()
    if (glfwInitialize != true) {
        throw Error("GLFW could not be initialized!")
    }

    val window: Long = glfwCreateWindow(1280, 720, "Hello World", NULL, NULL)
    if (window == NULL) {
        glfwTerminate()
        throw Error("Window could not be created!")
    }

    // Make the window's context current
    glfwMakeContextCurrent(window)

    while (glfwWindowShouldClose(window) == false) {
        // Swap front and back buffers
        glfwSwapBuffers(window)

        // Poll for and process events
        glfwPollEvents()
    }

    glfwDestroyWindow(window)
    glfwTerminate()

}
