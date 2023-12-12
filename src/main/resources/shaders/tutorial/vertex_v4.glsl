#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec3 aColor;
layout (location = 2) in vec2 aTexturePosition;

out vec3 vertexColor;
out vec2 vertexTextureCoordinate;

uniform mat4 mTransform; // Matrix transformation
uniform vec3 gPosition; // Global position

void main()
{
    gl_Position = mTransform * vec4(aPosition + gPosition, 1.0f);
    vertexColor = aColor;
    vertexTextureCoordinate = aTexturePosition;
}