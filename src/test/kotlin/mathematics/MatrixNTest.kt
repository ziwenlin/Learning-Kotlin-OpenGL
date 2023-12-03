package mathematics

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class MatrixNTest {
    private val matrix4x4Flip = MatrixN(4, 1.0f)

    private val matrix3x3 = MatrixN(
        3, 3, floatArrayOf(
            0.0f, 1.0f, 0.5f,
            0.0f, 1.5f, 0.5f,
            0.3f, 1.0f, 0.5f,
        )
    )

    private val matrix3x1 = MatrixN(
        3, 1, floatArrayOf(
            0.2f,
            1.1f,
            0.5f
        )
    )

    private val matrix1x3 = MatrixN(
        1, 3, floatArrayOf(
            0.2f,
            1.1f,
            0.5f
        )
    )

    @Test
    fun constructor_indexing_matrix(){
        val expected = floatArrayOf(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f,
        )
        Assertions.assertArrayEquals(expected, matrix4x4Flip.getArray())
    }

    @Test
    fun getArray() {
        val expected = floatArrayOf(
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
        )
        val matrix = MatrixN(3, 5)
        Assertions.assertArrayEquals(expected, matrix.getArray())
    }

    @Test
    fun get() {
        Assertions.assertEquals(0.5f, matrix3x3[2, 2])
        Assertions.assertEquals(0.3f, matrix3x3[2, 0])
        Assertions.assertEquals(1.5f, matrix3x3[1, 1])
    }

    @Test
    fun set() {
        val expected = floatArrayOf(
            0.0f, 1.0f, 0.5f,
            0.0f, 1.51f, 0.5f,
            0.33f, 1.0f, 0.55f,
        )
        matrix3x3[2, 2] = 0.55f
        matrix3x3[2, 0] = 0.33f
        matrix3x3[1, 1] = 1.51f
        Assertions.assertEquals(0.55f, matrix3x3[2, 2])
        Assertions.assertEquals(0.33f, matrix3x3[2, 0])
        Assertions.assertEquals(1.51f, matrix3x3[1, 1])
        Assertions.assertArrayEquals(expected, matrix3x3.getArray())
    }

    @Test
    fun times() {
        val expectedMatrix = floatArrayOf(
            0.15f, 2.0f, 0.75f,
            0.15f, 2.75f, 1.0f,
            0.15f, 2.3f, 0.9f,
        )
        val resultMatrix = (matrix3x3 * matrix3x3).getArray()
        Assertions.assertArrayEquals(expectedMatrix, resultMatrix)
    }

    @Test
    fun times_matrix3x1() {
        val expectedMatrix = floatArrayOf(
            1.35f,
            1.90f,
            1.41f
        )
        val resultMatrix = (matrix3x3 * matrix3x1).getArray()
        Assertions.assertArrayEquals(expectedMatrix, resultMatrix, 0.001f)
    }

    @Test
    fun times_matrix3x1_1x3(){
        val expectedMatrix = floatArrayOf(
            0.04f, 0.22f, 0.1f,
            0.22f, 1.21f, 0.55f,
            0.1f, 0.55f, 0.25f
        )
        val resultMatrix = (matrix3x1 * matrix1x3).getArray()
        Assertions.assertArrayEquals(expectedMatrix, resultMatrix, 0.001f)
    }

    @Test
    fun times_matrix4x4_flip() {
        val expectedMatrix = floatArrayOf(
            0.2f,
            1.1f,
            0.5f,
            0.0f
        )
        val resultMatrix = (matrix4x4Flip * matrix3x1).getArray()
        Assertions.assertArrayEquals(expectedMatrix, resultMatrix, 0.001f)
    }

    @Test
    fun getColumns() {
        val expectedArray = arrayOf(
            floatArrayOf(
                0.2f,
                1.1f,
                0.5f
            )
        )
        val resultArray = matrix3x1.getColumns()
        Assertions.assertArrayEquals(expectedArray, resultArray)
    }

    @Test
    fun getRows() {
        val expectedArray = arrayOf(
            floatArrayOf(0.2f),
            floatArrayOf(1.1f),
            floatArrayOf(0.5f)
        )
        val resultArray = matrix3x1.getRows()
        Assertions.assertArrayEquals(expectedArray, resultArray)
    }
}