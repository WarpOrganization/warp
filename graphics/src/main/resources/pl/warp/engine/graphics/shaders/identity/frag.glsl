#version 430
precision highp float;

uniform sampler2D tex;
in vec2 vTexCoord;

out vec4 fragColor;

void main(void) {
    fragColor = texture(tex, vTexCoord);
}