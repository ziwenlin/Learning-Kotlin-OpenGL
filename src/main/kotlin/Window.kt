import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.system.MemoryUtil.*
import kotlin.system.exitProcess

class Window {
    private var window: Long = NULL

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
        window = glfwCreateWindow(width, height, "Hello World", NULL, NULL)
        if (window == NULL) {
            println("Window could not be created!")
            exit(-1)
        }

        // Make the window's context current
        glfwMakeContextCurrent(window)
        // Enable v-sync
        glfwSwapInterval(1)
        // Make the window visible
        glfwShowWindow(window)

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities()
    }

    fun exit(status: Int) {
        // Destroy the window abd terminate GLFW
        glfwDestroyWindow(window)
        glfwTerminate()
        exitProcess(status)
    }

    fun loop(program: () -> Unit) {
        // Start the window loop
        while (glfwWindowShouldClose(window) != true) {
            // Run program
            program()
            // Swap front and back buffers
            glfwSwapBuffers(window)
            // Poll for and process events
            glfwPollEvents()
        }
    }
}