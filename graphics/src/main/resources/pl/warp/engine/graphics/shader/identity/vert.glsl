#version 430

layout(location = 0) in vec4 inVertex;
layout(location = 1) in vec2 inTexCoord; out vec2 vTexCoord;

void main(void) {
    vTexCoord = inTexCoord;
    gl_Position = inVertex;
}