val vertexShaderSource = """
    #version 330 core
    layout (location = 0) in vec3 aPosition;

    uniform vec3 vPosition;

    void main()
    {
        gl_Position = vec4(aPosition + vPosition, 1.0f);
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

val vertexShaderSource2 = """
    #version 330 core
    layout (location = 0) in vec3 aPosition;
    layout (location = 1) in vec3 aColor;

    out vec3 vertexColor;

    uniform vec3 vPosition;

    void main()
    {
        gl_Position = vec4(aPosition + vPosition, 1.0f);
        vertexColor = aColor;
    }
""".trimIndent()

val fragmentShaderSource2 = """
    #version 330 core
    in vec3 vertexColor;

    out vec4 FragColor;

    uniform vec3 vColor;

    void main()
    {
        FragColor = vec4(vertexColor * vColor, 1.0f);
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

val triangleVertices2 = floatArrayOf(
    -0.5f, 0.5f, 0.0f, 1.0f, 0.2f, 0.2f, // bottom left
    0.0f, 0.5f, 0.0f, 0.2f, 1.0f, 0.2f, // bottom center
    -0.25f, -0.5f, 0.0f, 0.2f, 0.2f, 1.0f, // top left-center
    0.0f, 0.5f, 0.0f, 0.2f, 1.0f, 0.2f, // bottom center
    0.5f, 0.5f, 0.0f, 0.2f, 0.2f, 1.0f, // bottom right
    0.25f, -0.5f, 0.0f, 1.0f, 0.2f, 0.2f // top right-center
)