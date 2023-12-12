package org.example.opengl.controller

import org.lwjgl.glfw.GLFW

class Keyboard(val window: Long) {
    fun press(key: Char): Boolean {
        val code = key.code
        if (code < 'A'.code || code > 'Z'.code) {
            println("Invalid input $key with code $code")
            return false
        }
        return GLFW.glfwGetKey(window, code) == GLFW.GLFW_PRESS
    }
}