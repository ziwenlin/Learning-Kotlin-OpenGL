package mathematics

class MatrixN {

    private val numRows: Int
    private val numColumns: Int
    private val matrixArray: FloatArray

    constructor(numRows: Int, numColumns: Int) : this(numRows, numColumns, FloatArray(numRows * numColumns))

    constructor(vector: Vector3, value: Float) : this(4, 1, vector.toArray(value))
    constructor(vector: Vector3) : this(3, 1, vector.toArray())

    constructor(size: Int, identity: Float) : this(size, size) {
        val range = size - 1
        for (index in 0..range) {
            set(index, index, identity)
        }
    }

    constructor(numRows: Int, numColumns: Int, array: FloatArray) {
        this.numRows = numRows
        this.numColumns = numColumns
        this.matrixArray = array
        if (array.size != numRows * numColumns) {
            throw Exception("Array size (${array.size}) does not match matrix size ${numRows}x${numColumns}")
        }
    }

    fun toVector3() : Vector3 {
        if (numRows != 1 && numColumns != 1)
            throw Exception("Illegal matrix conversion to vector with size ${numRows}x${numColumns}")
        return Vector3(getArray())
    }

    operator fun get(x: Int, y: Int): Float {
        return matrixArray[getIndex(x, y)]
    }

    operator fun set(x: Int, y: Int, value: Float) {
        matrixArray[getIndex(x, y)] = value
    }

    operator fun plus(other: MatrixN): MatrixN {
        val array = matrixArray.zip(other.matrixArray) { xv, yv -> xv + yv }.toFloatArray()
        return MatrixN(numRows, numColumns, array)
    }

    operator fun minus(other: MatrixN): MatrixN {
        val array = matrixArray.zip(other.matrixArray) { xv, yv -> xv - yv }.toFloatArray()
        return MatrixN(numRows, numColumns, array)
    }

    operator fun times(value: Float): MatrixN {
        return MatrixN(numRows, numColumns, matrixArray.map { it * value }.toFloatArray())
    }

    operator fun times(other: MatrixN): MatrixN {
        val matrix = MatrixN(this.numRows, other.numColumns)
        val sortedRows = this.getRows()
        val sortedColumns = other.getColumns()
        for (index in matrix.matrixArray.indices) {
            val (row, column) = matrix.getRowColumn(index)
            val zippedRowColumn = sortedRows[row].zip(sortedColumns[column])
            matrix.matrixArray[index] = zippedRowColumn.map { (x, y) -> x * y }.sum()
        }
        return matrix
    }

    protected fun getIndex(row: Int, column: Int): Int {
        return row * numColumns + column
    }

    protected fun getRowColumn(index: Int): Pair<Int, Int> {
        val row = index / numColumns
        val column = index % numColumns
        return Pair(row, column)
    }

    fun getArray(): FloatArray {
        return matrixArray
    }

    fun getRows(): Array<FloatArray> {
        val array = Array(numRows) { _ -> FloatArray(numColumns) }
        for (row in array.indices) {
            for (column in array[row].indices) {
                array[row][column] = matrixArray[getIndex(row, column)]
            }
        }
        return array
    }

    fun getColumns(): Array<FloatArray> {
        val array = Array(numColumns) { _ -> FloatArray(numRows) }
        for (column in array.indices) {
            for (row in array[column].indices) {
                array[column][row] = matrixArray[getIndex(row, column)]
            }
        }
        return array
    }
}