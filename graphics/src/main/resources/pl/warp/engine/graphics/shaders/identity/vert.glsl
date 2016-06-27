#version 430

attribute vec4 inVertex;
attribute vec2 inTexCoord; out vec2 vTexCoord;

void main(void) {
    vTexCoord = inTexCoord;
    gl_Position = inVertex;
}