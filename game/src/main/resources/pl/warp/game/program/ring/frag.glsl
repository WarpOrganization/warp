#version 400
precision highp float;

uniform float ringStart;
uniform float ringEnd;

smooth in vec3 onRingPos;

layout(location = 0) out vec4 fragColor;

void main() {
    float distance = length(onRingPos);
    if(distance < ringStart || distance > ringEnd) discard;
    fragColor = vec4(1.0);
}
