#version 330 core

in vec3 vertexColor;
in vec2 vertexTextureCoordinate;

out vec4 fragmentColor;

uniform sampler2D uniformTexture;

void main()
{
    fragmentColor = texture(uniformTexture, vertexTextureCoordinate) * vec4(vertexColor, 1.0f);
}