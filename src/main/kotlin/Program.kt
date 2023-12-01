import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL30.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun createProgram(): () -> Unit {
    // Create shader program
    val shaderProgram = Shader("shaders/vertex_v1.glsl", "shaders/fragment_v1.glsl")
    val shaderColor = shaderProgram.getUniformLocation("vColor")
    val shaderPosition = shaderProgram.getUniformLocation("vPosition")

    val shaderProgram2 = Shader("shaders/vertex_v2.glsl", "shaders/fragment_v2.glsl")
    val shaderColor2 = shaderProgram2.getUniformLocation("vColor")
    val shaderPosition2 = shaderProgram2.getUniformLocation("vPosition")

    val shaderProgram3 = Shader("shaders/vertex_v3.glsl", "shaders/fragment_v3.glsl")
    val shaderTexture3a = shaderProgram3.getUniformLocation("uniformTexture")
    val shaderTexture3b = shaderProgram3.getUniformLocation("uniformTexture2")
    val shaderPosition3 = shaderProgram3.getUniformLocation("uniformPosition")
    val shaderVisibility3 = shaderProgram3.getUniformLocation("uniformVisibility")
    val textureContainer = Texture("assets/container.jpg")
    val textureAwesome = Texture("assets/awesomeface.png")

    // Create square render object
    val squareRenderObject = RenderObject(boxVertices, boxIndices)
    val squareTexturedRenderObject = TexturedRenderObject(boxVerticesTextured, boxIndices)
    val triangleVerticesCorrected = addValuesToArray(triangleVertices, 3, 0.8f, 3)
    val triangleRenderObject = SimpleRenderObject(triangleVerticesCorrected, 6)
    val triangleRenderObject2 = SimpleRenderObject(triangleVertices2, 6)

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
        val timeRad = timeValue * PI / 2.0f
        val timePhase = 2.0f / 3.0f * PI

        // Mathematical functions values based on time
        val sawState = if (timeValue % 4.0f < 2.0f) 1 else -1
        val sawTooth = ((timeValue % 2.0f - 1.0f) * sawState).toFloat()
        val sawSharp = sawTooth * sawTooth * sawTooth
        val cosWave = cos(timeRad).toFloat()
        val sineWave = sin(timeRad).toFloat()
        val sineWave2 = sin(timeRad - timePhase).toFloat()
        val sineWave3 = sin(timeRad + timePhase).toFloat()

        // Mathematical transition effects
        val sinValue = sineWave / 2.0f + 0.5f
        val sinValue2 = sineWave2 / 2.0f + 0.5f
        val sinValue3 = sineWave3 / 2.0f + 0.5f
        val cosValue = cosWave / 2.0f + 0.5f

        // Render here
        glClear(GL_COLOR_BUFFER_BIT)

        // Render the objects
        shaderProgram.use()
        glUniform3f(shaderPosition, 0.5f * cosWave, 0.5f * sineWave, 0f)
        glUniform3f(shaderColor, 0.8f * sinValue, 0.8f * sinValue2, 0.8f * sinValue3)
        squareRenderObject.draw()

        // Render triangle objects
        shaderProgram2.use()
        glUniform3f(shaderPosition2, 0.0f, 0.5f * -sawSharp, 0.0f)
        glUniform3f(shaderColor2, sinValue3, sinValue2, sinValue)
        triangleRenderObject.draw()
        triangleRenderObject2.draw()

        // Render texture objects
        shaderProgram3.use()
        glUniform3f(shaderPosition3, 0.5f * sineWave, 0.5f * cosWave, 0f)
        glUniform1f(shaderVisibility3, visibilityValue * cosValue)
        glUniform1i(shaderTexture3a, 0)
        glUniform1i(shaderTexture3b, 1)
        textureContainer.bind(0)
        textureAwesome.bind(1)
        squareTexturedRenderObject.draw()
    }
    return program
}
