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

uniform sampler1D ringColors;
uniform float ringStart;
uniform float ringEnd;

smooth in vec3 onRingPos;

in vec3 vPos3; //absolute position on scene
in vec2 vTexCoord;
in vec3 vEyeDir;
smooth in vec3 vNormal;

const float discardAlpha = 0.05f;

layout(location = 0) out vec4 fragColor;

#include "util/noise3d"
#include "util/light"

void main() {
    float distance = length(onRingPos);
    if(distance < ringStart || distance > ringEnd) discard;
    float texturePos = distance - ringStart;
    vec4 color = texture(ringColors, texturePos);
    //color.rgb *= getLight(vNormal, vPos3, vEyeDir, SHININESS, SPECULAR_EXPONENT); TODO it another way
    if(color.a < discardAlpha) discard;
    fragColor = color;
}
