#version 400
precision highp float;

uniform sampler1D ringColors;
uniform float ringStart;
uniform float ringEnd;

smooth in vec3 onRingPos;
smooth in vec3 worldPos;

#include "util/noise3d"

layout(location = 0) out vec4 fragColor;

void main() {
    float distance = length(onRingPos);
    if(distance < ringStart || distance > ringEnd) discard;
    float texturePos = distance - ringStart;
    fragColor = texture(ringColors, texturePos);
    if(fragColor.a < 0.05f) discard;
}
