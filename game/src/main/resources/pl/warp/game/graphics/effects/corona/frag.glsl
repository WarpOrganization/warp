#version 400
precision highp float;

uniform sampler1D colors;
uniform vec3 brightness;
uniform float temperature;

uniform int time = 1;

smooth in vec2 coord;

layout(location = 0) out vec4 fragColor;

#include "util/noise4d"

void main() {
    float dist = length(coord) * 3.0;
    float brightness = (1.0 / (dist * dist) - 0.1) * 0.7;
    fragColor.rgba = vec4(1.0);
}
