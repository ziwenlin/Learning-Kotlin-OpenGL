val vertexShaderSource = """
    #version 330 core
    layout (location = 0) in vec3 aPos;

    uniform vec3 vPos;

    void main()
    {
        gl_Position = vec4(aPos + vPos, 1.0f);
    }
""".trimIndent()

val fragmentShaderSource = """
    #version 330 core
    out vec4 FragColor;

    uniform vec3 vColor;

    void main()
    {
        FragColor = vec4(vColor, 1.0f);
    }
""".trimIndent()

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