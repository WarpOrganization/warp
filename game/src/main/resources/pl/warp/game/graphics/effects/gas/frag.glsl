#version 400
precision highp float;

struct SpotLightSource {
    vec3 position;
    vec3 coneDirection;
    float coneAngle;
    float coneGradient;
    vec3 color;
    vec3 ambientColor;
    float attenuation;
    float gradient;
};

const float SPECULAR_EXPONENT = 100.0;
const float SHININESS = 5.0;

uniform SpotLightSource spotLightSources[$MAX_LIGHTS$];
uniform int numSpotLights;

uniform bool lightEnabled;

uniform sampler1D colors;
uniform int time = 1;
in vec3 onSpherePos; //position relative to sphere center

in vec3 vPos3; //absolute position on scene
in vec2 vTexCoord;
in vec3 vEyeDir;
smooth in vec3 vNormal;

layout(location = 0) out vec4 fragColor;

#include "util/noise4d"
#include "util/light"

void main() {

    float texCoord = onSpherePos.y * 0.5 + 0.5;
    vec4 noisePos = vec4(onSpherePos, time * 0.000005);

    float s = 0.4;
    float t1 = snoise(noisePos * 2.0) - s;
    float t2 = snoise((noisePos + 800.0) * 2.0) - s;
    float t3 = snoise((noisePos + 1600.0) * 2.0) - s;
    float threshold = max(t1 * t2 * t3, 0.0);

    float n1 = noise(noisePos, 7, 20.0, 0.8) * 0.01;
    float n2 = ridgedNoise(noisePos, 5,  5.0, 0.75) * 0.015 - 0.01;
    float n3 = snoise(noisePos) * threshold;
    float n = n1 + n2 + n3;
    float newTexCoord = clamp(texCoord + n, 0.1, 0.9);
    vec3 color = texture(colors, newTexCoord).rgb;
    fragColor.rgb = color * getLight(vNormal, vPos3, vEyeDir, SHININESS, SPECULAR_EXPONENT);
    fragColor.a = 1.0;
}
