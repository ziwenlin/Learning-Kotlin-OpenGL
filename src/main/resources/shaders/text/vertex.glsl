#version 330 core

layout (location = 0) in vec4 Vertex;

out vec2 TextureCoordinates;

uniform mat4 Projection;

void main()
{
    gl_Position = Projection * vec4(Vertex.xy, 0.0f, 1.0f);
    TextureCoordinates = Vertex.zw;
}