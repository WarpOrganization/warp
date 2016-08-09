#version 330

precision mediump float;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

layout(location = 0) in vec3 position;
layout(location = 1) in float rotation;
layout(location = 2) in vec3 color;
layout(location = 3) in float gradient;

out vData {
    mat2 rotation;
    float textureIndex;
    vec3 color;
    float gradient;
} pointData;


void main(void) {
    gl_Position = vec4(position, 1);
    pointData.rotation = mat2(
        cos(rotation), -sin(rotation),
        sin(rotation), cos(rotation));
    pointData.textureIndex = textureIndex;
    pointData.color = color;
    pointData.gradient = gradient;
}