#version 330

precision mediump float;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

layout(location = 0) in vec3 position;
layout(location = 1) in float rotation;
layout(location = 2) in int textureIndex;

out vData {
    mat2 rotation;
    int textureIndex;
} pointData;


void main(void) {
    gl_Position = modelViewMatrix * vec4(position, 1);
    pointData.rotation = mat2(
        cos(rotation), -sin(rotation),
        sin(rotation), cos(rotation));
    pointData.textureIndex = textureIndex;
}