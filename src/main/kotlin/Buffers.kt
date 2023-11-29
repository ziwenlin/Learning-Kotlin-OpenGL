@file:Suppress("unused")

import org.lwjgl.opengl.GL30.*

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
}

fun createElementVertexArray(vertices: FloatArray, indices: IntArray): Int {
    val vaoID = glGenVertexArrays()
    val vboID = glGenBuffers()
    val eboID = glGenBuffers()

    glBindVertexArray(vaoID)

    glBindBuffer(GL_ARRAY_BUFFER, vboID)
    glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID)
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)

    glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.SIZE_BYTES, 0)
    glEnableVertexAttribArray(0)

    return vaoID
}

fun createVertexBufferArray(vertices: FloatArray): Int {
    val vaoID = glGenVertexArrays()
    val vboID = glGenBuffers()

    glBindVertexArray(vaoID)

    glBindBuffer(GL_ARRAY_BUFFER, vboID)
    glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

    return vaoID
}