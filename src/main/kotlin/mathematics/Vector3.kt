package mathematics

import kotlin.math.sqrt

class Vector3 {

    var x: Float
    var y: Float
    var z: Float

    constructor(vector: Vector3) : this(vector.x, vector.y, vector.z)
    constructor(matrix: MatrixN) : this(matrix.toVector3())

    constructor(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }

    constructor(array: FloatArray) {
        this.x = array[0]
        this.y = array[1]
        this.z = array[2]
    }

    fun toMatrix(value: Float): MatrixN {
        return MatrixN(this, value)
    }

    fun toMatrix(): MatrixN {
        return MatrixN(this)
    }

    fun toArray(): FloatArray {
        return floatArrayOf(x, y, z)
    }

    fun toArray(value: Float): FloatArray {
        return floatArrayOf(x, y, z, value)
    }

    fun getLength(): Float {
        return sqrt(x * x + y * y + z * z)
    }

    fun getNormalized(): Vector3 {
        return this / getLength()
    }

    fun dot(vector: Vector3): Float {
        return (x * vector.x + y * vector.y + z * vector.z) / getLength() / vector.getLength()
    }

    fun cross(vector: Vector3): Vector3 {
        return Vector3(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        )
    }

    operator fun plus(vector: Vector3): Vector3 {
        return Vector3(x + vector.x, y + vector.y, z + vector.z)
    }

    operator fun minus(vector: Vector3): Vector3 {
        return Vector3(x - vector.x, y + vector.y, z - vector.z)
    }

    operator fun plus(value: Float): Vector3 {
        return Vector3(x + value, y + value, z + value)
    }

    operator fun minus(value: Float): Vector3 {
        return Vector3(x + value, y + value, z + value)
    }

    operator fun times(value: Float): Vector3 {
        return Vector3(x * value, y * value, z * value)
    }

    operator fun div(value: Float): Vector3 {
        return Vector3(x / value, y / value, z / value)
    }
}