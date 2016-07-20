#version 330
precision mediump float;

uniform samplerCube cube;
in vec3 vTexCoord;

layout(location = 0) out vec4 fragColor;

void main(void) {
    fragColor = texture(cube, vTexCoord);
}