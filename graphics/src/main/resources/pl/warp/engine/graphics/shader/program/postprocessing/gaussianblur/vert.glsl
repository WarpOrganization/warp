#version 330
precision highp float;

layout(location = 0) in vec4 inVertex;

uniform int displaySize;

flat out float pixelSize;
smooth out vec2 texCoord;

void main(void) {
    gl_Position = inVertex;
    texCoord = gl_Position.xy * 0.5 + 0.5;
    pixelSize = 1.0 / displaySize;
}