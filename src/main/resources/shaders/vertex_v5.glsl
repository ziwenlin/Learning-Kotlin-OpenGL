#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 2) in vec2 aTexturePosition;

out vec3 vertexColor;
out vec2 vertexTextureCoordinate;

uniform mat4 mModel;
uniform mat4 mView;
uniform mat4 mProjection;

void main()
{
    gl_Position = mProjection * mView * mModel * vec4(aPosition, 1.0f);
    vertexColor = vec3(1.0f, 1.0f, 1.0f);
    vertexTextureCoordinate = aTexturePosition;
}