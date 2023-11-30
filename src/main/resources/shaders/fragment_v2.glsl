#version 330 core

in vec3 vertexColor;

out vec4 FragColor;

uniform vec3 vColor;

void main()
{
    FragColor = vec4(vertexColor * vColor, 1.0f);
}