package org.example.opengl.renderer.text

import org.example.opengl.constructor.Destroyable
import org.lwjgl.opengl.GL30
import org.lwjgl.stb.STBImage
import java.awt.Font

class TextLoader(font: Font) : Destroyable {
    private val textureID = GL30.glGenTextures()

    private val font = FontGenerator(font)

    init {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureID)

        GL30.glTexParameterIi(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_CLAMP_TO_EDGE)
        GL30.glTexParameterIi(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_CLAMP_TO_EDGE)
        GL30.glTexParameterIi(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR)
        GL30.glTexParameterIi(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR)

        val width = intArrayOf(0)
        val height = intArrayOf(0)
        val channels = intArrayOf(0)

        val buffer = this.font.getImageBuffer()
        var image = STBImage.stbi_load_from_memory(buffer, width, height, channels, 0)
        if (image == null) {
            val path = "./build/output/font/${font.name}_${font.size}px.png"
            this.font.saveToFile(path)
            println("Failed to load texture from memory. Saved image to file ($path).")
            image = STBImage.stbi_load(path, width, height, channels, 0)
        }
        if (image != null) {
            GL30.glTexImage2D(
                GL30.GL_TEXTURE_2D, 0,
                GL30.GL_RED, width.get(0), height.get(0), 0,
                GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, image
            )
            STBImage.stbi_image_free(image)
        } else {
            println("Failed loading texture from disk.")
        }
    }

    fun createListVertices(text: String, x: Int, y: Int, width: Float, height: Float): MutableList<FloatArray> {
        val vertexData = mutableListOf<FloatArray>()
        var drawX1 = x.toFloat()
        var drawY1 = y.toFloat()
        for (character in text) {
            if (character == '\n') {
                /* Line feed, set x and y to draw at the next line */
                drawY1 -= height
                drawX1 = x.toFloat()
                continue
            }
            val glyph = font.glyphs[character] ?: continue
            val drawX2 = drawX1 + width
            val drawY2 = drawY1 + height

            vertexData.add(
                floatArrayOf(
                    // First triangle
                    drawX1, drawY1, glyph.x1, glyph.y1,
                    drawX2, drawY1, glyph.x2, glyph.y1,
                    drawX2, drawY2, glyph.x2, glyph.y2,
                    // Second triangle
                    drawX1, drawY1, glyph.x1, glyph.y1,
                    drawX1, drawY2, glyph.x1, glyph.y2,
                    drawX2, drawY2, glyph.x2, glyph.y2,
                )
            )
            drawX1 += width
        }
        return vertexData
    }

    fun bind(unit: Int) {
        GL30.glActiveTexture(GL30.GL_TEXTURE0 + unit)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureID)
    }

    fun unbind() {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0)
    }

    override fun destroy() {
        GL30.glDeleteTextures(textureID)
    }

}