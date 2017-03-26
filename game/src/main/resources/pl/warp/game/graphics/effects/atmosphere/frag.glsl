#version 400
precision highp float;

layout(location = 0) out vec4 fragColor;

uniform vec3 color;
uniform float radius;

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

uniform SpotLightSource spotLightSources[$MAX_LIGHTS$];
uniform int numSpotLights;

uniform bool lightEnabled;

uniform vec3 cameraPos;

in vec3 normal;
smooth in float fragmentRadius;
smooth in float planetRadius;

#include "util/noise4d"

void main() {
    if(fragmentRadius < planetRadius) discard;
    fragColor.rgb = vec3(1, 1, 2.0) ;
    fragColor.a = pow(1 - ((fragmentRadius / planetRadius - 1) / (radius - 1)), 3);
}
