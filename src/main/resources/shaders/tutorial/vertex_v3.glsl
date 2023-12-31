#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec3 aColor;
layout (location = 2) in vec2 aTexturePosition;

out vec3 vertexColor;
out vec2 vertexTextureCoordinate;

uniform vec3 uniformPosition;

void main()
{
    gl_Position = vec4(aPosition + uniformPosition, 1.0f);
    vertexColor = aColor;
    vertexTextureCoordinate = aTexturePosition;
}