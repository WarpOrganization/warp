#version 330

precision mediump float;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

layout(location = 0) in vec3 position;
layout(location = 1) in float rotation;
layout(location = 2) in float textureIndex;

out mat2 vRotation;
out float vTextureIndex;


void main(void) {
    gl_Position = vec4(position, 1);
    vRotation = mat2(
        cos(rotation), -sin(rotation),
        sin(rotation), cos(rotation));
    vTextureIndex = textureIndex;
}