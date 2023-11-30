
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