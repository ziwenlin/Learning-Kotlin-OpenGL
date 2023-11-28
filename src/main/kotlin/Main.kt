/*
* Main
*
* */

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil.NULL

var window: Long = NULL

fun main(args: Array<String>) {
    println("Hello World!")
    println("Program arguments: ${args.joinToString()}")

    init()
    loop()

    glfwDestroyWindow(window)
    glfwTerminate()
}

fun init() {
    val glfwInitialize = glfwInit()
    if (glfwInitialize != true) {
        throw Error("GLFW could not be initialized!")
    }

    // Configure GLFW
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)

    // Create the window
    window = glfwCreateWindow(1280, 720, "Hello World", NULL, NULL)
    if (window == NULL) {
        glfwTerminate()
        throw Error("Window could not be created!")
    }

    // Set up a key callback. It will be called every time a key is pressed, repeated or released.
    val keyCallback = { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
        if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
            glfwSetWindowShouldClose(window, true)
        }
    }

    // Make the window's context current
    glfwMakeContextCurrent(window)
    // Enable v-sync
    glfwSwapInterval(1)
    // Make the window visible
    glfwShowWindow(window)
    // Assigning a key callback to the window
    glfwSetKeyCallback(window, keyCallback)
}

fun loop() {
    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities()

    // Set the clear color
    glClearColor(1.0f, 0.3f, 0.3f, 0.0f)

    // Main window loop
    while (glfwWindowShouldClose(window) != true) {
        // Render here
        glClear(GL_COLOR_BUFFER_BIT)

        // Swap front and back buffers
        glfwSwapBuffers(window)

        // Poll for and process events
        glfwPollEvents()
    }
}
