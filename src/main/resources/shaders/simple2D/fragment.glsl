#version 330 core

in float color;
out vec4 FragColor;

uniform vec3 vColor;

void main()
{
    FragColor = vec4(vColor / 2, 1.0f);
}