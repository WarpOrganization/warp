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

uniform samplerCube surface;
uniform int time = 1;
in vec3 onSpherePos; //position relative to sphere center

in vec3 vPos3; //absolute position on scene
in vec3 vTexCoord;
in vec3 vEyeDir;
smooth in vec3 vNormal;

layout(location = 0) out vec4 fragColor;

#include "util/noise4d"
#include "util/light"

void main() {
    vec3 color = texture(surface, vTexCoord).rgb;
    fragColor.rgb = color; //* getLight(vNormal, vPos3, vEyeDir, SHININESS, SPECULAR_EXPONENT);
    fragColor.a = 1.0;
}
