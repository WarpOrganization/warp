#version 330
precision highp float;

uniform sampler2D tex;
in vec2 vTexCoord;

layout(location = 0) out vec4 fragColor;

void main(void) {
    fragColor = texture(tex, vTexCoord);
}