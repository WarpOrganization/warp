#version 330
precision mediump float;

layout(location = 0) in vec4 inVertex;
out vec2 vTexCoord;

void main(void) {
    gl_Position = inVertex;
    vTexCoord = gl_Position.xy * 0.5 + vec2(0.5);
}