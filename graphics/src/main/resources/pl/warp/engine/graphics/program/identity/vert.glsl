#version 330

layout(location = 0) in vec4 inVertex;
layout(location = 1) in vec4 inTexCoord;
layout(location = 2) in vec4 inNormal;
out vec2 vTexCoord;

void main(void) {
    gl_Position = inVertex;
    vTexCoord = gl_Position.xy * 0.5 + vec2(0.5);
}