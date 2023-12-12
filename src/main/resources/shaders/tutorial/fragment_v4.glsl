#version 330 core

in vec3 vertexColor;
in vec2 vertexTextureCoordinate;

out vec4 fragmentColor;

uniform sampler2D uTexture1;
uniform sampler2D uTexture2;

void main()
{
    fragmentColor = mix(texture(uTexture1, vertexTextureCoordinate),
                        texture(uTexture2, vertexTextureCoordinate),
                        0.2f) * vec4(vertexColor, 1.0f);
}