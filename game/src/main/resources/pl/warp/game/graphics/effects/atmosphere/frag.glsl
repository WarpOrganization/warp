#version 400
precision highp float;

layout(location = 0) out vec4 fragColor;

uniform vec3 color;
uniform float radius;
uniform float innerExp;
uniform float innerMul;
uniform float outerExp;
uniform float outerMul;
uniform float lightMul;

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

vec4 renderInner(vec3 light){
    vec4 fColor;
    fColor.rgb = color;
    fColor.a = pow(fragmentRadius / planetRadius, innerExp) * innerMul * length(light);
    return fColor;
}

vec4 renderOuter(vec3 light){
    vec4 fColor;
    fColor.rgb = color;
    fColor.a = pow(1 - ((fragmentRadius / planetRadius - 1) / (radius - 1)), outerExp) * outerMul * length(light);
    return fColor;
}

void main() {
    vec4 fColor;
    vec3 light = getLight(normal, surfacePos, eyeDir, 3, SPECULAR_EXPONENT) * lightMul;
    if(fragmentRadius < planetRadius) fColor = renderInner(light);
    else fColor = renderOuter(light);
    fragColor = fColor;
}
