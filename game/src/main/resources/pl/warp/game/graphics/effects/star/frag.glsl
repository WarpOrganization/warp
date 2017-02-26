#version 400
precision highp float;

uniform sampler1D colors;
uniform vec3 brightness;
uniform float temperature;

uniform int time = 1;

in vec3 onSpherePos;
in vec3 normal;

layout(location = 0) out vec4 fragColor;

#include "util/noise4d"

void main() {
    vec4 position = vec4(onSpherePos, time * 0.000005);
    float n = (noise(position , 4, 40.0, 0.7) + 0.8) * 0.5;
    float total = n;
    vec3 color = vec3(3.5, 2.0, 2.8);
    float u = (temperature - 800.0f) / 29200.0f;
    //color *= texture(colors, u).rgb;
    fragColor.rgb = color;
    fragColor.a = 1.0;
}
