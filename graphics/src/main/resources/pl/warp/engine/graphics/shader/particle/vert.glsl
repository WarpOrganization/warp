#version 330

uniform projectionMatrix;
uniform modelViewMatrix;

layout(location = 0) in vec4 position;
layout(location = 1) in float rotation;
layout(location = 2) in int textureIndex;

flat out mat2 rotation;
flat out int vTextureIndex;

void main(void) {
    gl_Position = modelViewMatrix * position;
    rotation = mat2(
        cos(rotation), -sin(rotation),
        sin(rotation), cos(rotation));
    vTextureIndex = textureIndex;
}