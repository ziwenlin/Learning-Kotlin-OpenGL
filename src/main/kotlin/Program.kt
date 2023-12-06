import org.joml.Math.toRadians
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL30.*
import renderer.*
import utility.*
import java.nio.FloatBuffer

val floatBuffer16: FloatBuffer = BufferUtils.createFloatBuffer(16)

fun createProgram(): Pair<() -> Unit, () -> Unit> {
    // Create shader program
    val shaderProgram = Shader("/shaders/vertex_v5.glsl", "/shaders/fragment_v4.glsl")
    val shaderTexture1 = shaderProgram.getUniformLocation("uTexture1")
    val shaderTexture2 = shaderProgram.getUniformLocation("uTexture2")
    val shaderProjection = shaderProgram.getUniformLocation("mProjection")
    val shaderView = shaderProgram.getUniformLocation("mView")
    val shaderModel = shaderProgram.getUniformLocation("mModel")

    // Create texture
    val textureContainer = Texture("assets/container.jpg")
    val textureAwesome = Texture("assets/awesome_face.png")

    // Create square render object
    val box3DTexturedRenderObject = SimpleTexturedRenderObject(boxVertices3D, 5)

    // Set the clear color and the view port
    glEnable(GL_DEPTH_TEST)
    glClearColor(0.1f, 0.3f, 0.3f, 1.0f)
    glViewport(0, 0, width, height)

    // Variables used in the program
    var fieldOfView = toRadians(45.0f)
    val aspectRatio = width.toFloat() / height.toFloat()

    // Activate shader first before uploading matrix to the shader
    shaderProgram.use()
    var modelMatrix: Matrix4f

    // Calculation view matrix
    var viewMatrix = Matrix4f()
        .translate(0f, 0f, -3f)
    viewMatrix.get(floatBuffer16)
    glUniformMatrix4fv(shaderView, false, floatBuffer16)

    // Calculation projection matrix
    var projectionMatrix = Matrix4f()
        .perspective(fieldOfView, aspectRatio, 0.1f, 100.0f)
    projectionMatrix.get(floatBuffer16)
    glUniformMatrix4fv(shaderProjection, false, floatBuffer16)

    // Callback for zooming in and out with the scroll wheel
    val minimumFoV = toRadians(1.0f)
    val maximumFoV = toRadians(45.0f)
    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    val scrollCallback = { window: Long, scrollX: Double, scrollY: Double ->
        fieldOfView -= toRadians(scrollY).toFloat()
        if (fieldOfView < minimumFoV)
            fieldOfView = minimumFoV
        if (fieldOfView > maximumFoV)
            fieldOfView = maximumFoV
    }
    glfwSetScrollCallback(window.getID(), scrollCallback)

    // Create main program
    val program = { ->
        // Get time since start
        val timeValue = glfwGetTime().toFloat()

        // Setup shader program with texture objects
        shaderProgram.use()
        glUniform1i(shaderTexture1, 0)
        glUniform1i(shaderTexture2, 1)
        textureContainer.bind(0)
        textureAwesome.bind(1)

        // Calculation projection matrix
        projectionMatrix = Matrix4f()
            .perspective(fieldOfView, aspectRatio, 0.1f, 100.0f)
        projectionMatrix.get(floatBuffer16)
        glUniformMatrix4fv(shaderProjection, false, floatBuffer16)

        // Calculation model matrix for every box
        for (index in coordinates3D.indices) {
            // Setup object matrix
            var rotationAngle = Math.toRadians(50.0 * index).toFloat()
            if (index % 3 == 0) rotationAngle *= timeValue / 10
            val rotationAxis = Vector3f(1f, 0.3f, 0.5f).normalize()
            val coordinate = Vector3f(coordinates3D[index])
            modelMatrix = Matrix4f()
                .translate(coordinate)
                .rotate(rotationAngle, rotationAxis)
            // Upload matrix to shader
            modelMatrix.get(floatBuffer16)
            glUniformMatrix4fv(shaderModel, false, floatBuffer16)
            box3DTexturedRenderObject.draw()
        }
    }

    // Create deconstruction program
    val destroy = { ->
        shaderProgram.destroy()

        textureContainer.destroy()
        textureAwesome.destroy()

        box3DTexturedRenderObject.destroy()
    }
    return Pair(program, destroy)
}
