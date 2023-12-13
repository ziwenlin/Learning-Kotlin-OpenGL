#version 330 core

layout (location = 0) in vec3 aPosition;

uniform mat4 View;
uniform mat4 Projection;
uniform vec3 vPosition;
uniform float vSize;

void main()
{
    gl_Position = Projection * View * vec4(aPosition * vSize + vPosition, 1.0f);
}