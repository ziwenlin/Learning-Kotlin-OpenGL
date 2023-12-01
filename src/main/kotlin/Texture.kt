import org.lwjgl.opengl.GL30.*
import org.lwjgl.stb.STBImage

@Suppress("unused")
class Texture(path: String) {
    val textureID = glGenTextures()

    init {
        glBindTexture(GL_TEXTURE_2D, textureID)

        glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glTexParameterIi(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)

        val width = intArrayOf(0)
        val height = intArrayOf(0)
        val channels = intArrayOf(0)
        val alpha = if (path.endsWith(".png")) 1 else 0

        STBImage.stbi_set_flip_vertically_on_load(true)
        val image = STBImage.stbi_load(path, width, height, channels, 0)
        if (image != null) {
            glTexImage2D(
                GL_TEXTURE_2D, 0,
                GL_RGB, width.get(0), height.get(0), 0,
                GL_RGB + alpha, GL_UNSIGNED_BYTE, image
            )
            glGenerateMipmap(GL_TEXTURE_2D)
            STBImage.stbi_image_free(image)
        } else {
            println("Failed to load texture: $path")
        }
    }

    fun bind(unit: Int) {
        glActiveTexture(GL_TEXTURE0 + unit)
        glBindTexture(GL_TEXTURE_2D, textureID)
    }

    fun unbind() {
        glBindTexture(GL_TEXTURE_2D, 0)
    }

    fun destroy() {
        glDeleteTextures(textureID)
    }
}
