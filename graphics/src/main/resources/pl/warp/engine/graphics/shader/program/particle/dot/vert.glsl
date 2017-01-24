#version 330

precision mediump float;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

layout(location = 0) in vec3 position;
layout(location = 1) in vec4 color;
layout(location = 2) in float gradient;
layout(location = 3) in float scale;

out vec4 vColor;
out float vScale;
out float vGradient;


void main(void) {
    gl_Position = vec4(position, 1);
    vColor = color;
    vScale = gradient;
    vGradient = scale;
}