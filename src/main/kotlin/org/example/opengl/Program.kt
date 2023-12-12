package org.example.opengl

import org.example.opengl.renderer.text.TextRenderer
import org.example.opengl.constructor.Manager
import org.example.opengl.renderer.Camera
import org.example.opengl.renderer.Shader
import org.example.opengl.renderer.SimpleTexturedRenderObject
import org.example.opengl.renderer.Texture
import org.example.opengl.utility.boxVertices3D
import org.example.opengl.utility.coordinates3D
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL32
import java.awt.Font
import java.nio.FloatBuffer

class Program {
    val floatBuffer16: FloatBuffer = BufferUtils.createFloatBuffer(16)

    // Create shader program
    val shaderMain = Shader("/shaders/vertex_v5.glsl", "/shaders/fragment_v4.glsl")
    val shaderMainTexture1 = shaderMain.getUniformLocation("uTexture1")
    val shaderMainTexture2 = shaderMain.getUniformLocation("uTexture2")
    val shaderMainProjection = shaderMain.getUniformLocation("mProjection")
    val shaderMainView = shaderMain.getUniformLocation("mView")
    val shaderMainModel = shaderMain.getUniformLocation("mModel")

    val shaderText = Shader("/shaders/text/vertex.glsl", "/shaders/text/fragment.glsl")
    val shaderTextProjection = shaderText.getUniformLocation("Projection")
    val shaderTextColor = shaderText.getUniformLocation("TextColor")
    val shaderTextTexture = shaderText.getUniformLocation("Texture")

    // Create texture
    val textureContainer = Texture("assets/container.jpg")
    val textureAwesome = Texture("assets/awesome_face.png")

    // Create square render object
    val box3DTexturedRenderObject = SimpleTexturedRenderObject(boxVertices3D, 5)

    // Create the camera object
    val camera = Camera(width, height)
    val textRenderer = TextRenderer(Font(Font.MONOSPACED, Font.PLAIN, 20))

    // Objects used in simulation
    val resourceManager = Manager()

    init {
        resourceManager.add(shaderText)
        resourceManager.add(textRenderer)
        resourceManager.add(shaderMain)
        resourceManager.add(textureContainer)
        resourceManager.add(textureAwesome)
        resourceManager.add(box3DTexturedRenderObject)

        // Set the clear color and the view port
        GL30.glEnable(GL32.GL_PROGRAM_POINT_SIZE)
        GL30.glEnable(GL30.GL_DEPTH_TEST)
        GL30.glEnable(GL30.GL_BLEND)
        GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA)
        GL30.glClearColor(0.1f, 0.3f, 0.3f, 1.0f)
        GL30.glViewport(0, 0, width, height)

        // Set camera callbacks
        camera.setMouseCallback(window.getID())
        camera.setScrollCallback(window.getID())

        // Set projection matrix for text coordinates
        Matrix4f()
            .ortho(0.0f, 800.0f, 0.0f, 600.0f, 0.0f, 10.0f)
            .get(floatBuffer16)
        shaderText.use()
        GL30.glUniformMatrix4fv(shaderTextProjection, false, floatBuffer16)

    }

    fun run() {
        // Get time since start
        val timeValue = window.getTime()

        // Process key inputs
        camera.processKeyboardInput(window.getID())

        // Setup shader program with texture objects
        shaderMain.use()
        GL30.glUniform1i(shaderMainTexture1, 0)
        GL30.glUniform1i(shaderMainTexture2, 1)
        textureContainer.bind(0)
        textureAwesome.bind(1)

        // Calculation view matrix
        camera.getViewMatrix().get(floatBuffer16)
        GL30.glUniformMatrix4fv(shaderMainView, false, floatBuffer16)

        // Calculation projection matrix
        camera.getProjectionMatrix().get(floatBuffer16)
        GL30.glUniformMatrix4fv(shaderMainProjection, false, floatBuffer16)

        // Calculation model matrix for every box
        var modelMatrix: Matrix4f
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
            GL30.glUniformMatrix4fv(shaderMainModel, false, floatBuffer16)
            box3DTexturedRenderObject.draw()
        }

        val interval = camera.timeDelta
        val fps = 1f / interval

        shaderText.use()
        GL30.glUniform1i(shaderTextTexture, 0)
        GL30.glUniform3f(shaderTextColor, 0.5f, 1.0f, 0.5f)
        textRenderer.draw("fps: ${fps}", 40f, 20f, 10, 550)
        textRenderer.draw("interval: ${interval}", 40f, 20f, 10, 500)

    }

    fun destroy() {
        resourceManager.destroy()
    }
}
