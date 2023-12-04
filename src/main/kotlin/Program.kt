import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL30.*
import renderer.*
import utility.*
import java.nio.FloatBuffer

val floatBuffer16: FloatBuffer = BufferUtils.createFloatBuffer(16)

fun createProgram(): Pair<() -> Unit, () -> Unit> {
    // Create shader program
    val shaderProgram4 = Shader("/shaders/vertex_v4.glsl", "/shaders/fragment_v4.glsl")
    val shaderTexture4a = shaderProgram4.getUniformLocation("uTexture1")
    val shaderTexture4b = shaderProgram4.getUniformLocation("uTexture2")
    val shaderTransform4 = shaderProgram4.getUniformLocation("mTransform")

    // Create texture
    val textureContainer = Texture("assets/container.jpg")
    val textureAwesome = Texture("assets/awesomeface.png")

    // Create square render object
    val squareRenderObject = RenderObject(boxVertices, boxIndices)
    val squareTexturedRenderObject = TexturedRenderObject(boxVerticesTextured, boxIndices)

    // Set the clear color and the view port
    glClearColor(0.1f, 0.3f, 0.3f, 1.0f)
    glViewport(0, 0, width, height)

    // Variables used in the program
    var visibilityValue = 0.2f

    // Create main program
    val program = { ->
        if (glfwGetKey(window.getID(), GLFW_KEY_UP) == GLFW_PRESS) {
            visibilityValue += 0.1f
        }
        if (glfwGetKey(window.getID(), GLFW_KEY_DOWN) == GLFW_PRESS) {
            visibilityValue -= 0.1f
        }
        // Get time since start
        val timeValue = glfwGetTime()

        // Render here
        glClear(GL_COLOR_BUFFER_BIT)

        // Render texture objects
        shaderProgram4.use()
        glUniform1i(shaderTexture4a, 0)
        glUniform1i(shaderTexture4b, 1)
        textureContainer.bind(0)
        textureAwesome.bind(1)

        val radians = Math.toRadians(timeValue * 45).toFloat()
        Matrix4f()
            .translate(0.5f * sineWave, -0.5f, 0.0f)
            .rotate(radians, 0f, 0f, 1f)
            .get(floatBuffer16)
        glUniformMatrix4fv(shaderTransform4, false, floatBuffer16)
        squareTexturedRenderObject.draw()

        Matrix4f()
            .translate(-0.5f, 0.5f, 0.0f)
            .scale(sineWave, sineWave, sineWave)
            .get(floatBuffer16)
        glUniformMatrix4fv(shaderTransform4, false, floatBuffer16)
        squareTexturedRenderObject.draw()
    }

    // Create deconstruction program
    val destroy = { ->
        shaderProgram4.destroy()

        textureContainer.destroy()
        textureAwesome.destroy()

        squareTexturedRenderObject.destroy()
    }
    return Pair(program, destroy)
}
