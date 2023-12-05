package utility

val coordinates3D = arrayOf(
    floatArrayOf( 0.0f, 0.0f, 0.0f),
    floatArrayOf( 2.0f, 5.0f, -15.0f),
    floatArrayOf(-1.5f, -2.2f, -2.5f),
    floatArrayOf(-3.8f, -2.0f, -12.3f),
    floatArrayOf( 2.4f, -0.4f, -3.5f),
    floatArrayOf(-1.7f, 3.0f, -7.5f),
    floatArrayOf( 1.3f, -2.0f, -2.5f),
    floatArrayOf( 1.5f, 2.0f, -2.5f),
    floatArrayOf( 1.5f, 0.2f, -1.5f),
    floatArrayOf(-1.3f, 1.0f, -1.5f)
)

val boxVertices3D = floatArrayOf(
    -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
    0.5f, -0.5f, -0.5f, 1.0f, 0.0f,
    0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
    0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
    -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
    -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
    -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
    0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
    -0.5f, 0.5f, 0.5f, 0.0f, 1.0f,
    -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
    -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
    -0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
    -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
    -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
    -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
    -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
    0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
    0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
    0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
    0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
    -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
    0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
    0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
    0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
    -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
    -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
    -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
    0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
    -0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
    -0.5f, 0.5f, -0.5f, 0.0f, 1.0f

)

val boxVerticesTextured = floatArrayOf(
    // positions    // colors   // texture coords
    0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, // top right
    0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, // bottom right
    -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // bottom left
    -0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f // top left
)

val boxVertices = floatArrayOf(
    0.5f, 0.5f, 0.0f, // top right
    0.5f, -0.5f, 0.0f, // bottom right
    -0.5f, -0.5f, 0.0f, // bottom left
    -0.5f, 0.5f, 0.0f // top left
)

val boxIndices = intArrayOf(
    0, 1, 3, // first triangle
    1, 2, 3 // second triangle
)

val triangleVertices = floatArrayOf(
    -0.5f, -0.5f, 0.0f, // bottom left
    0.0f, -0.5f, 0.0f, // bottom center
    -0.25f, 0.5f, 0.0f, // top left-center
    0.0f, -0.5f, 0.0f, // bottom center
    0.5f, -0.5f, 0.0f, // bottom right
    0.25f, 0.5f, 0.0f, // top right-center
)

val triangleVertices2 = floatArrayOf(
    -0.5f, 0.5f, 0.0f, 1.0f, 0.2f, 0.2f, // bottom left
    0.0f, 0.5f, 0.0f, 0.2f, 1.0f, 0.2f, // bottom center
    -0.25f, -0.5f, 0.0f, 0.2f, 0.2f, 1.0f, // top left-center
    0.0f, 0.5f, 0.0f, 0.2f, 1.0f, 0.2f, // bottom center
    0.5f, 0.5f, 0.0f, 0.2f, 0.2f, 1.0f, // bottom right
    0.25f, -0.5f, 0.0f, 1.0f, 0.2f, 0.2f // top right-center
)