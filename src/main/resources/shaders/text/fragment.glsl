#version 330

in vec2 TextureCoordinates;
out vec4 Color;

uniform sampler2D Texture;
uniform vec3 TextColor;

void main() {
    vec4 Sampled = vec4(1.0f, 1.0f, 1.0f, texture(Texture, TextureCoordinates));
    Color = vec4(TextColor, 1.0f) * Sampled;
}