#version 400
precision highp float;

uniform int time = 1;
in vec3 onSpherePos;
in vec3 normal;

layout(location = 0) out vec4 fragColor;

#include "util/noise4d"

void main() {
    vec4 position = vec4(onSpherePos, time);
    float n = (noise(position , 4, 40.0, 0.7) + 1.0) * 0.5;
    float total = n;
    fragColor.rgb = vec3(total);
    fragColor.a = 1.0;
}
