#version 400
precision highp float;

uniform sampler1D colors;
uniform vec3 brightness;
uniform float temperature;

uniform float time = 1;

smooth in vec3 spherePos;

layout(location = 0) out vec4 fragColor;

#include "util/noise4d"

void main() {

    float t = (time * 0.02) - length(spherePos);

    // Offset normal with noise
    float frequency = 1.0;
    float ox = snoise(vec4(spherePos, t) * frequency);
    float oy = snoise(vec4((spherePos + 2000.0), t) * frequency);
    float oz = snoise(vec4((spherePos + 4000.0), t) * frequency);

    // Store offsetVec since we want to use it twice.
    vec3 offsetVec = vec3(ox, oy, oz) * 0.1;

    // Get the distance vector from the center
    vec3 nDistVec = normalize(spherePos + offsetVec);

    // Get noise with normalized position to offset the original position

    vec3 offsetPos = spherePos + noise(vec4(nDistVec, t), 5, 2.0, 0.7) * 0.2;

    //explosions

    float dist = length(offsetPos) * 4.0;
    float distSquared = dist * dist;
    float brightness = (1.0 / distSquared - 0.1) * 0.7;

    fragColor.rgba = vec4(1, 1, 1, max(brightness, 0));
    float u = clamp((temperature - dist * 1000) / 29200.0f, 0.001, 0.999);
    fragColor.rgb += (texture(colors, u).rgb + brightness);
}
