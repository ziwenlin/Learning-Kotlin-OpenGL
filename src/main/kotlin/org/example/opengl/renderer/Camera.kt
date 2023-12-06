package org.example.opengl.renderer

import org.joml.Math.*
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*

class Camera(width: Int, height: Int) {

    // Variables used in the program
    val aspectRatio = width.toFloat() / height.toFloat()
    var fieldOfView = toRadians(45.0f)
    var timeDelta = 0.0f
    var timeLast = 0.0f

    // Variables used for the camera
    val Position = Vector3f(0f, 0f, 3f)
    var Front = Vector3f(0f, 0f, -1f)
    val Up = Vector3f(0f, 1f, 0f)
    var Pitch = 0.0f
    var Yaw = 180.0f

    fun processKeyboardInput(window: Long) {
        val timeValue = glfwGetTime().toFloat()
        timeDelta = timeValue - timeLast
        timeLast = timeValue

        val cameraSpeed = 2.5f * timeDelta
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            Position.sub(
                Vector3f(Front)
                    .cross(Up).normalize()
                    .mul(cameraSpeed)
            )
        }
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            Position.add(
                Vector3f(Front)
                    .cross(Up).normalize()
                    .mul(cameraSpeed)
            )
        }
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            Position.add(Vector3f(Front).mul(cameraSpeed))
        }
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            Position.sub(Vector3f(Front).mul(cameraSpeed))
        }
    }

    fun setMouseCallback(window: Long) {
        var mouseDetected = false
        var lastMouseX = 0.0
        var lastMouseY = 0.0

        val processMouseMovement = { _: Long, mouseX: Double, mouseY: Double ->
            if (mouseDetected == false) {
                mouseDetected = true
                lastMouseX = mouseX
                lastMouseY = mouseY
            }
            val offsetX = lastMouseX - mouseX
            val offsetY = lastMouseY - mouseY
            lastMouseX = mouseX
            lastMouseY = mouseY
            Yaw += offsetX.toFloat() * 0.1f * fieldOfView
            Pitch += offsetY.toFloat() * 0.1f * fieldOfView

            // Calculate camera angles
            if (Pitch > 89f) Pitch = 89f
            if (Pitch < -89f) Pitch = -89f
            val pitch = toRadians(Pitch)
            val yaw = toRadians(Yaw)

            val cameraDirection = Vector3f()
            cameraDirection.x = sin(yaw) * cos(pitch)
            cameraDirection.y = sin(pitch)
            cameraDirection.z = cos(yaw) * cos(pitch)
            Front = cameraDirection.normalize()
        }
        glfwSetCursorPosCallback(window, processMouseMovement)
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED)
    }

    fun setScrollCallback(window: Long) {
        // Callback for zooming in and out with the scroll wheel
        val minimumFieldOfView = toRadians(1.0f)
        val maximumFieldOfView = toRadians(45.0f)

        @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        val processScrollInput = { _: Long, scrollX: Double, scrollY: Double ->
            fieldOfView -= toRadians(scrollY).toFloat() * 2
            if (fieldOfView < minimumFieldOfView)
                fieldOfView = minimumFieldOfView
            if (fieldOfView > maximumFieldOfView)
                fieldOfView = maximumFieldOfView
        }
        glfwSetScrollCallback(window, processScrollInput)
    }

    fun getViewMatrix(): Matrix4f {
        val cameraDirection = Vector3f(Position).add(Front)
        return Matrix4f()
            .lookAt(Position, cameraDirection, Up)
    }

    fun getProjectionMatrix(): Matrix4f {
        return Matrix4f()
            .perspective(fieldOfView, aspectRatio, 0.1f, 100.0f)
    }
}

