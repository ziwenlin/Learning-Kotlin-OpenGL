package org.example.opengl

import org.example.opengl.constructor.Manager
import org.example.opengl.constructor.SimpleProfiler
import org.example.opengl.controller.Keyboard
import org.example.opengl.physics.Engine
import org.example.opengl.physics.Particle
import org.example.opengl.renderer.Camera
import org.example.opengl.renderer.RenderObject
import org.example.opengl.renderer.Shader
import org.example.opengl.renderer.SimpleTexturedRenderObject
import org.example.opengl.renderer.Texture
import org.example.opengl.renderer.text.TextRenderer
import org.example.opengl.utility.boxVertices3D
import org.example.opengl.utility.circleIndices3D
import org.example.opengl.utility.circleVertices3D
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
    val shaderMain = Shader("/shaders/tutorial/vertex_v5.glsl", "/shaders/tutorial/fragment_v4.glsl")
    val shaderMainTexture1 = shaderMain.getUniformLocation("uTexture1")
    val shaderMainTexture2 = shaderMain.getUniformLocation("uTexture2")
    val shaderMainProjection = shaderMain.getUniformLocation("mProjection")
    val shaderMainView = shaderMain.getUniformLocation("mView")
    val shaderMainModel = shaderMain.getUniformLocation("mModel")

    val shaderParticle = Shader("/shaders/simple2D/vertex.glsl", "/shaders/simple2D/fragment.glsl")
    val shaderParticleProjection = shaderParticle.getUniformLocation("Projection")
    val shaderParticlePosition = shaderParticle.getUniformLocation("vPosition")
    val shaderParticleColor = shaderParticle.getUniformLocation("vColor")
    val shaderParticleSize = shaderParticle.getUniformLocation("vSize")

    val shaderText = Shader("/shaders/text/vertex.glsl", "/shaders/text/fragment.glsl")
    val shaderTextProjection = shaderText.getUniformLocation("Projection")
    val shaderTextColor = shaderText.getUniformLocation("TextColor")
    val shaderTextTexture = shaderText.getUniformLocation("Texture")

    // Create texture
    val textureContainer = Texture("assets/container.jpg")
    val textureAwesome = Texture("assets/awesome_face.png")

    // Create square render object
    val box3DTexturedRenderObject = SimpleTexturedRenderObject(boxVertices3D, 5)
    val circleRenderObject = RenderObject(circleVertices3D(8), circleIndices3D(8))

    // Create the camera object
    val camera = Camera(width, height)
    val keyboard = Keyboard(window.getID())
    val textRenderer = TextRenderer(Font(Font.MONOSPACED, Font.PLAIN, 20))

    // Create resource managers
    val resourceManager = Manager()
    val physicsEngine = Engine(60f)
    val performanceProfiler = SimpleProfiler(window)

    init {
        resourceManager.add(shaderText)
        resourceManager.add(textRenderer)

        resourceManager.add(shaderMain)
        resourceManager.add(textureContainer)
        resourceManager.add(textureAwesome)
        resourceManager.add(box3DTexturedRenderObject)

        resourceManager.add(shaderParticle)
        resourceManager.add(physicsEngine)
        resourceManager.add(circleRenderObject)

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

        shaderParticle.use()
        GL30.glUniformMatrix4fv(shaderParticleProjection, false, floatBuffer16)

    }

    fun run() {
        // Get time since start
        val timeValue = window.getTime()

        // Process key inputs
        camera.processKeyboardInput(window.getID())

        // Processing keyboard inputs
        if (keyboard.press('H')) {
            println("Hello World!")
        }

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

        // Physics engine
        performanceProfiler.start()
        physicsEngine.step()
        val timePhysics = performanceProfiler.stop()
        if (keyboard.press('F')) {
            physicsEngine.create()
        }
        if (keyboard.press('R')) {
            for (index in 0 until 10) {
                physicsEngine.create()
            }
        }
        if (keyboard.press('T')) {
            for (index in 0 until 10) {
                physicsEngine.delete()
            }
        }
        if (keyboard.press('Y')) {
            for (index in 0 until 100) {
                physicsEngine.delete()
            }
        }

        // Particles rendering
        shaderParticle.use()
        performanceProfiler.start()

        // Calculation view matrix
        camera.getViewMatrix().get(floatBuffer16)
        GL30.glUniformMatrix4fv(shaderMainView, false, floatBuffer16)

        // Calculation projection matrix
        camera.getProjectionMatrix().get(floatBuffer16)
        GL30.glUniformMatrix4fv(shaderMainProjection, false, floatBuffer16)


        val particleIterator = physicsEngine.entities.iterator()
        while (particleIterator.hasNext()) {
            val particle = particleIterator.next() as Particle
            val position = particle.positionCurrent
            GL30.glUniform1f(shaderParticleSize, particle.diameter)
            GL30.glUniform3f(shaderParticlePosition, position.x, position.y, position.z)
            GL30.glUniform3f(shaderParticleColor, 1f, 1f, 1f)
            circleRenderObject.draw()
        }
        val timeCircle = performanceProfiler.stop()

        // Gather performance statistics
        val interval = camera.timeDelta
        val statistics = """
            fps: ${1f / interval}
            interval: ${interval}
            particles: ${physicsEngine.entities.stack.size}
            physics: ${timePhysics * 1000}
            circles: ${timeCircle * 1000}
        """.trimIndent()

        shaderText.use()
        GL30.glUniform1i(shaderTextTexture, 0)
        GL30.glUniform3f(shaderTextColor, 0.5f, 1.0f, 0.5f)
        textRenderer.draw(statistics, 40f, 20f, 10, 550)

    }

    fun destroy() {
        resourceManager.destroy()
    }
}
