#version 330 core

layout (location = 0) in vec3 aPosition;
out float color;

uniform mat4 Projection;
uniform vec3 vPosition;
uniform float vSize;

void main()
{
    gl_Position = Projection * vec4(aPosition * vSize + vPosition, 1.0f);
    color = 1 + vPosition.z / 2.0f;
}