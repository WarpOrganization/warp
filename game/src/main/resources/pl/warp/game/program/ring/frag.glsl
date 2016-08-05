#version 400
precision highp float;

uniform sampler1D ringColors;
uniform float ringStart;
uniform float ringEnd;

smooth in vec3 onRingPos;

layout(location = 0) out vec4 fragColor;

void main() {
    float distance = length(onRingPos);
    if(distance < ringStart || distance > ringEnd) discard;
    float texturePos = distance - ringStart;
    fragColor = texture1D(ringColors, texturePos);
}
