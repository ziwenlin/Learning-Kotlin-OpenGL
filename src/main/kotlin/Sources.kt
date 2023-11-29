val vertexShaderSource = """
    #version 330 core
    layout (location = 0) in vec3 aPos;
    void main()
    {
    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
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
val vertices = floatArrayOf(
    0.5f, 0.5f, 0.0f, // top right
    0.5f, -0.5f, 0.0f, // bottom right
    -0.5f, -0.5f, 0.0f, // bottom left
    -0.5f, 0.5f, 0.0f // top left
)
val indices = intArrayOf(
    0, 1, 3, // first triangle
    1, 2, 3 // second triangle
)