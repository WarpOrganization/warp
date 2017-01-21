#version 330

precision mediump float;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

layout(location = 0) in vec3 position;
layout(location = 1) in vec4 color;
layout(location = 2) in float gradient;
layout(location = 3) in float scale;

smooth out vData {
    vec4 color;
    float scale;
    float gradient;
} pointData;


void main(void) {
    gl_Position = vec4(position, 1);
    pointData.color = color;
    pointData.gradient = gradient;
    pointData.scale = scale;
}