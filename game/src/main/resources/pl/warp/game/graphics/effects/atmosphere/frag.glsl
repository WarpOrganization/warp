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
in float fragmentRadius;

#include "util/noise4d"

void main() {
    //if(fragmentRadius < 1.0) discard;
    fragColor.rgb = vec3(fragmentRadius);
    fragColor.a = 1.0f;
}
