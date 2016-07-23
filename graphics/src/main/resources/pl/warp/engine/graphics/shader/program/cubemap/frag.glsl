#version 330
precision mediump float;

uniform samplerCube cube;
uniform float brightness = 1.2;
in vec3 vTexCoord;

layout(location = 0) out vec4 fragColor;

void main(void) {
    fragColor = texture(cube, vTexCoord) * brightness;
}