#version 330

precision mediump float;

smooth in vec2 coord;
flat in vec4 color;
in float gradient;

layout(location = 0) out vec4 fragColor;

void main(void) {
    float distance = length(coord);
    if(distance > 1.0) discard;
    else if(distance < (1.0 - gradient) || gradient < 0.001) fragColor = color;
    else fragColor = color * ((1.0 - distance) / gradient);
    fragColor = color;
}