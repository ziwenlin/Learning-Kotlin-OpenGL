package org.example.opengl.renderer.text

import org.example.opengl.utility.checkExistanceFile
import org.lwjgl.BufferUtils
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer
import javax.imageio.ImageIO
import kotlin.math.max

class FontGenerator(val font: Font) {
    class Glyph(val x1: Float, val y1: Float, val x2: Float, val y2: Float)

    val glyphs = hashMapOf<Char, Glyph>()
    val image: BufferedImage

    val height: Int
    val width: Int

    init {
        var imageWidth = 0
        var imageHeight = 0
        for (index in 32 until 127) {
            val character = index.toChar()
            val charImage = createImage(character)
            val charWidth = charImage.width
            val charHeight = charImage.height

            imageWidth += charWidth
            imageHeight = max(imageHeight, charHeight)
        }

        image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)
        height = imageHeight
        width = imageWidth
        val graphics = image.createGraphics()
        var charX = 0.0f
        for (index in 32 until 127) {
            val character = index.toChar()
            val charImage = createImage(character)
            val charWidth = charImage.width.toFloat() / imageWidth
            val charHeight = charImage.height.toFloat() / imageHeight

            val charY = 1.0f - charHeight
            val glyph = Glyph(charX, charY, charX + charWidth, 1.0f)
            glyphs.put(character, glyph)
            graphics.drawImage(charImage, (imageWidth * charX).toInt(), 0, null)
            charX += charWidth
        }
        graphics.dispose()
    }

    fun getImageBuffer(): ByteBuffer {
        val byteArrayStream = ByteArrayOutputStream()
        ImageIO.write(image, "png", byteArrayStream)
        byteArrayStream.flush()

        val imageBuffer = BufferUtils.createByteBuffer(width * height)
        val imageArray = byteArrayStream.toByteArray()
        imageBuffer.put(imageArray)
        byteArrayStream.close()

        return imageBuffer
    }

    fun createImage(character: Char): BufferedImage {
        var image = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        var graphics = image.createGraphics()
        graphics.font = this.font
        val metrics = graphics.fontMetrics
        graphics.dispose()
        val charWidth = metrics.charWidth(character)
        val charHeight = metrics.height

        image = BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB)
        graphics = image.createGraphics()
        graphics.font = this.font
        graphics.paint = Color.WHITE
        graphics.drawString(character.toString(), 0, metrics.ascent)
        graphics.dispose()

        return image
    }

    fun saveToFile(name: String): Boolean {
        val imageFile = File(name)
        if (checkExistanceFile(imageFile) == false) return false
        return ImageIO.write(image, "png", imageFile)
    }

}
