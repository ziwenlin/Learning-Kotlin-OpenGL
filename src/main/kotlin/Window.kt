import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil.*

class Window {
    private var window: Long = NULL

    init {
        val glfwInitialize = glfwInit()
        if (glfwInitialize != true) {
            println("GLFW could not be initialized!")
            close(-1)
        }

        // Configure GLFW
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)

        // Create the window
        window = glfwCreateWindow(width, height, "Hello World", NULL, NULL)
        if (window == NULL) {
            println("Window could not be created!")
            close(-1)
        }

        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        @Suppress("UNUSED_ANONYMOUS_PARAMETER")
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

    fun close(status: Int) {
        glfwDestroyWindow(window)
        glfwTerminate()
        exitProcess(status)
    }
}