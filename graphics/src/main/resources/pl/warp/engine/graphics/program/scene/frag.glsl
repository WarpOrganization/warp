#version 330
precision mediump float;

in vec3 vPos3;
in vec2 vTexCoord;
in vec3 vNormal;

layout(location = 0) out vec4 fragColor;


void main(void) {
    fragColor = vec4(vNormal, 1) + vec4(vTexCoord * 0.001, 0, 0);
}
