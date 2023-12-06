package org.example.opengl.renderer

import org.lwjgl.opengl.GL30.*

class TexturedRenderObject(vertices: FloatArray, indices: IntArray) {
    private val vaoID = glGenVertexArrays()
    private val vboID = glGenBuffers()
    private val eboID = glGenBuffers()

    private val count = indices.size

    init {
        glBindVertexArray(vaoID)

        glBindBuffer(GL_ARRAY_BUFFER, vboID)
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)

        val floatSize: Int = Float.SIZE_BYTES

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * floatSize, 0L * floatSize)
        glEnableVertexAttribArray(0)

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * floatSize, 3L * floatSize)
        glEnableVertexAttribArray(1)

        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * floatSize, 6L * floatSize)
        glEnableVertexAttribArray(2)

        glBindVertexArray(0)
    }

    fun draw() {
        glBindVertexArray(vaoID)
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0)
        glBindVertexArray(0)
    }

    fun destroy() {
        glDeleteVertexArrays(vaoID)
        glDeleteBuffers(vboID)
        glDeleteBuffers(eboID)
    }
}

class SimpleTexturedRenderObject(vertices: FloatArray, stride: Int) {
    private val vaoID = glGenVertexArrays()
    private val vboID = glGenBuffers()

    private val vertexCount = vertices.size / stride

    init {
        glBindVertexArray(vaoID)

        glBindBuffer(GL_ARRAY_BUFFER, vboID)
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

        val floatSize: Int = Float.SIZE_BYTES

        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride * floatSize, 0L * floatSize)
        glEnableVertexAttribArray(0)

        glVertexAttribPointer(2, 2, GL_FLOAT, false, stride * floatSize, 3L * floatSize)
        glEnableVertexAttribArray(2)

        glBindVertexArray(0)
    }

    fun draw() {
        glBindVertexArray(vaoID)
        glDrawArrays(GL_TRIANGLES, 0, vertexCount)
        glBindVertexArray(0)
    }

    fun destroy() {
        glDeleteVertexArrays(vaoID)
        glDeleteBuffers(vboID)
    }
}

class RenderObject(vertices: FloatArray, indices: IntArray) {
    private val vaoID = glGenVertexArrays()
    private val vboID = glGenBuffers()
    private val eboID = glGenBuffers()

    private val count = indices.size

    init {
        glBindVertexArray(vaoID)

        glBindBuffer(GL_ARRAY_BUFFER, vboID)
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.SIZE_BYTES, 0)
        glEnableVertexAttribArray(0)

        glBindVertexArray(0)
    }

    fun draw() {
        glBindVertexArray(vaoID)
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0)
        glBindVertexArray(0)
    }

    fun destroy() {
        glDeleteVertexArrays(vaoID)
        glDeleteBuffers(vboID)
        glDeleteBuffers(eboID)
    }
}

class SimpleRenderObject(vertices: FloatArray, stride: Int) {
    private val vaoID = glGenVertexArrays()
    private val vboID = glGenBuffers()

    private val vertexCount = vertices.size / stride

    init {
        glBindVertexArray(vaoID)

        glBindBuffer(GL_ARRAY_BUFFER, vboID)
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

        val floatSize: Int = Float.SIZE_BYTES

        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride * floatSize, 0L * floatSize)
        glEnableVertexAttribArray(0)

        glVertexAttribPointer(1, 3, GL_FLOAT, false, stride * floatSize, 3L * floatSize)
        glEnableVertexAttribArray(1)

        glBindVertexArray(0)
    }

    fun draw() {
        glBindVertexArray(vaoID)
        glDrawArrays(GL_TRIANGLES, 0, vertexCount)
        glBindVertexArray(0)
    }

    fun destroy() {
        glDeleteVertexArrays(vaoID)
        glDeleteBuffers(vboID)
    }
}
