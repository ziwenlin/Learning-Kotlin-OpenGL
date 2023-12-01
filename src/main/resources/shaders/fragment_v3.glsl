#version 330 core

in vec3 vertexColor;
in vec2 vertexTextureCoordinate;

out vec4 fragmentColor;

uniform sampler2D uniformTexture1;
uniform sampler2D uniformTexture2;
uniform float uniformVisibility;

void main()
{
    fragmentColor = mix(texture(uniformTexture1, vertexTextureCoordinate),
                        texture(uniformTexture2, vertexTextureCoordinate),
                        uniformVisibility) * vec4(vertexColor, 1.0f);
}