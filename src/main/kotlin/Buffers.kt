@file:Suppress("unused")

import org.lwjgl.opengl.GL30.*

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