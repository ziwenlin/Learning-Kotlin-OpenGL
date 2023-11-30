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