package org.example.opengl

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
import java.nio.FloatBuffer

class Program {
    val floatBuffer16: FloatBuffer = BufferUtils.createFloatBuffer(16)

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

    // Create the camera object
    val camera = Camera(width, height)

    init {
        // Set the clear color and the view port
        GL30.glEnable(GL32.GL_PROGRAM_POINT_SIZE)
        GL30.glEnable(GL30.GL_DEPTH_TEST)
        GL30.glClearColor(0.1f, 0.3f, 0.3f, 1.0f)
        // Set camera callbacks
        camera.setMouseCallback(window.getID())
        camera.setScrollCallback(window.getID())
    }

    fun run() {
        // Get time since start
        val timeValue = window.getTime()

        // Process key inputs
        camera.processKeyboardInput(window.getID())

        // Setup shader program with texture objects
        shaderProgram.use()
        GL30.glUniform1i(shaderTexture1, 0)
        GL30.glUniform1i(shaderTexture2, 1)
        textureContainer.bind(0)
        textureAwesome.bind(1)

        // Calculation view matrix
        camera.getViewMatrix().get(floatBuffer16)
        GL30.glUniformMatrix4fv(shaderView, false, floatBuffer16)

        // Calculation projection matrix
        camera.getProjectionMatrix().get(floatBuffer16)
        GL30.glUniformMatrix4fv(shaderProjection, false, floatBuffer16)

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
            GL30.glUniformMatrix4fv(shaderModel, false, floatBuffer16)
            box3DTexturedRenderObject.draw()
        }
    }

    fun destroy() {
        shaderProgram.destroy()

        textureContainer.destroy()
        textureAwesome.destroy()

        box3DTexturedRenderObject.destroy()
    }
}
