/*
* Main
*
* */

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*
import org.lwjgl.opengl.GL30.*
import org.lwjgl.system.MemoryUtil.NULL

var window: Long = NULL
val width: Int = 1280
val height: Int = 720

val vertices = floatArrayOf(
    -0.5f, -0.5f, 0.0f,
    0.5f, -0.5f, 0.0f,
    0.0f, 0.5f, 0.0f
)

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
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)

    // Create the window
    window = glfwCreateWindow(width, height, "Hello World", NULL, NULL)
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

    // Assigning a window size change callback
    val frameSizeCallback = { window: Long, width: Int, height: Int ->
        glViewport(0, 0, width, height)
    }
    glfwSetFramebufferSizeCallback(window, frameSizeCallback)
}

fun loop() {
    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities()

    val shaderProgram = createShaderProgram(vertexShaderSource, fragmentShaderSource)

    val vaoID = glGenVertexArrays()
    val vboID = glGenBuffers()

    glBindVertexArray(vaoID)
    glBindBuffer(GL_ARRAY_BUFFER, vboID)
    glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

    GL20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.SIZE_BYTES, 0)
    glEnableVertexAttribArray(0)

    // Set the clear color
    glClearColor(1.0f, 0.3f, 0.3f, 0.0f)
    glViewport(0, 0, width, height)

    // Main window loop
    while (glfwWindowShouldClose(window) != true) {
        // Render here
        glClear(GL_COLOR_BUFFER_BIT)
        glUseProgram(shaderProgram)
        glBindVertexArray(vaoID)
        glDrawArrays(GL_TRIANGLES, 0, 3)

        // Swap front and back buffers
        glfwSwapBuffers(window)

        // Poll for and process events
        glfwPollEvents()
    }
}
