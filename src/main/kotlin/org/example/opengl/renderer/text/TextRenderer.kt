package org.example.opengl.renderer.text

import org.example.opengl.constructor.Destroyable
import org.lwjgl.opengl.GL30
import java.awt.Font

class TextRenderer(font: Font) : Destroyable {
    private val textLoader = TextLoader(font)

    private val vaoID = GL30.glGenVertexArrays()
    private val vboID = GL30.glGenBuffers()

    init {
        GL30.glBindVertexArray(vaoID)

        val floatSize: Int = Float.SIZE_BYTES
        val bufferSize = (floatSize * 6 * 4).toLong()

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, bufferSize, GL30.GL_DYNAMIC_DRAW)

        GL30.glVertexAttribPointer(0, 4, GL30.GL_FLOAT, false, 4 * floatSize, 0L * floatSize)
        GL30.glEnableVertexAttribArray(0)

        GL30.glBindVertexArray(0)
    }

    fun draw(text: String, height: Float, width: Float, x: Int, y: Int) {
        val textVertices = textLoader.createListVertices(text, x, y, width, height)
        textLoader.bind(0)
        GL30.glBindVertexArray(vaoID)
        for (vertexData in textVertices) {
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID)
            GL30.glBufferSubData(GL30.GL_ARRAY_BUFFER, 0, vertexData)
            GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, 6)
        }
        textLoader.unbind()
        GL30.glBindVertexArray(0)
    }

    override fun destroy() {
        textLoader.destroy()
        GL30.glDeleteVertexArrays(vaoID)
        GL30.glDeleteBuffers(vboID)
    }

}