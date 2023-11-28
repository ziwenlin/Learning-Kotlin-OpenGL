/*
* Main
*
* */

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil.NULL

fun main(args: Array<String>) {
    println("Hello World!")
    println("Program arguments: ${args.joinToString()}")

    val glfwInitialize = glfwInit()
    if (glfwInitialize != true) {
        throw Error("GLFW could not be initialized!")
    }

    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
    val window: Long = glfwCreateWindow(1280, 720, "Hello World", NULL, NULL)
    if (window == NULL) {
        glfwTerminate()
        throw Error("Window could not be created!")
    }

    @Suppress("NAME_SHADOWING")
    val keyCallback = { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
        if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
            glfwSetWindowShouldClose(window, true)
        }
    }

    // Make the window's context current
    glfwMakeContextCurrent(window)
    glfwSwapInterval(1)
    glfwSetKeyCallback(window, keyCallback)

    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities()

    // Set the clear color
    glClearColor(1.0f, 0.3f, 0.3f, 0.0f)

    while (glfwWindowShouldClose(window) == false) {
        // Render here
        glClear(GL_COLOR_BUFFER_BIT)

        // Swap front and back buffers
        glfwSwapBuffers(window)

        // Poll for and process events
        glfwPollEvents()
    }

    glfwDestroyWindow(window)
    glfwTerminate()

}
