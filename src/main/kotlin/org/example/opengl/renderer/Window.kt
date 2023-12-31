package org.example.opengl.renderer

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.glViewport
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryUtil.*
import kotlin.system.exitProcess

class Window {
    private var windowID: Long = NULL
    private var exitCallback = { -> }

    fun getID(): Long {
        return windowID
    }

    fun init(width: Int, height: Int) {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        val glfwInitialize = glfwInit()
        if (glfwInitialize != true) {
            println("GLFW could not be initialized!")
            exit(-1)
        }

        // Configure GLFW
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)

        // Create the window
        windowID = glfwCreateWindow(width, height, "Hello World", NULL, NULL)
        if (windowID == NULL) {
            println("Window could not be created!")
            exit(-1)
        }

        // Make the window's context current
        glfwMakeContextCurrent(windowID)
        // Enable v-sync
        glfwSwapInterval(1)
        // Make the window visible
        glfwShowWindow(windowID)

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities()

        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        val keyCallback = { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(window, true)
            }
        }
        glfwSetKeyCallback(windowID, keyCallback)

        // Assigning a window size change callback
        @Suppress("UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING")
        val frameSizeCallback = { window: Long, width: Int, height: Int ->
            glViewport(0, 0, width, height)
        }
        glfwSetFramebufferSizeCallback(windowID, frameSizeCallback)
    }

    fun setExitCallback(callback: () -> Unit) {
        exitCallback = callback
    }

    fun exit(status: Int) {
        // Run exit callback
        exitCallback()
        // Destroy the window abd terminate GLFW
        glfwDestroyWindow(windowID)
        Callbacks.glfwFreeCallbacks(windowID)
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
        if (status != 0) {
            Thread.dumpStack()
        }
        exitProcess(status)
    }

    fun loop(program: () -> Unit) {
        // Start the window loop
        while (glfwWindowShouldClose(windowID) != true) {
            // Clear screen here
            GL30.glClear(GL30.GL_COLOR_BUFFER_BIT)
            GL30.glClear(GL30.GL_DEPTH_BUFFER_BIT)
            // Run program
            program()
            // Swap front and back buffers
            glfwSwapBuffers(windowID)
            // Poll for and process events
            glfwPollEvents()
        }
    }

    fun getTime(): Float {
        return glfwGetTime().toFloat()
    }
}