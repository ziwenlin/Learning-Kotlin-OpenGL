import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.glViewport
import org.lwjgl.system.MemoryUtil.*
import kotlin.system.exitProcess

class Window {
    private var windowID: Long = NULL

    fun getID(): Long {
        return windowID
    }

    fun init() {
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
        @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        val frameSizeCallback = { window: Long, width: Int, height: Int ->
            glViewport(0, 0, width, height)
        }
        glfwSetFramebufferSizeCallback(windowID, frameSizeCallback)
    }

    fun exit(status: Int) {
        // Destroy the window abd terminate GLFW
        glfwDestroyWindow(windowID)
        glfwTerminate()
        if (status != 0) {
            Thread.dumpStack()
        }
        exitProcess(status)
    }

    fun loop(program: () -> Unit) {
        // Start the window loop
        while (glfwWindowShouldClose(windowID) != true) {
            // Run program
            program()
            // Swap front and back buffers
            glfwSwapBuffers(windowID)
            // Poll for and process events
            glfwPollEvents()
        }
    }
}