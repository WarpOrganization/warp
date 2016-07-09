#version 330

attribute vec4 inVertex;
uniform mat4 perspMatrix;
uniform mat4 rotMatrix;

out vec3 vTexCoord;

void main(void) {
    vTexCoord = normalize(inVertex.xyz);
    gl_Position = perspMatrix * rotMatrix * inVertex;
}