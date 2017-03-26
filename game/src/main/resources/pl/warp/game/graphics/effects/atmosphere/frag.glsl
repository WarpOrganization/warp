#version 400
precision highp float;

layout(location = 0) out vec4 fragColor;

uniform vec3 atmColor = vec3(1, 1, 2.5);
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

const float SPECULAR_EXPONENT = 10.0;

uniform SpotLightSource spotLightSources[$MAX_LIGHTS$];
uniform int numSpotLights;

uniform bool lightEnabled;

uniform vec3 cameraPos;

smooth in vec3 surfacePos;
smooth in vec3 eyeDir;

smooth in vec3 normal;
smooth in float fragmentRadius;
smooth in float planetRadius;

#include "util/light"

const float innerExp = 3;
const float innerMul = 0.5;
void renderInner(vec3 light){
    fragColor.rgb = atmColor;
    fragColor.a = pow(fragmentRadius / planetRadius, innerExp) * innerMul * length(light);
}

const float outerExp = 3;
const float outerMul = 1;
void renderOuter(vec3 light){
    fragColor.rgb = atmColor;
    fragColor.a = pow(1 - ((fragmentRadius / planetRadius - 1) / (radius - 1)), outerExp) * outerMul * length(light);
}

void main() {
    vec3 light = getLight(normal, surfacePos, eyeDir, 3, SPECULAR_EXPONENT);
    if(fragmentRadius < planetRadius) renderInner(light);
    else renderOuter(light);
}
