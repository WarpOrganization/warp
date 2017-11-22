#version 330
precision highp float;

#include "util/floatPack"
#include "util/vectorEncoding"
#include "screenspace/light/orenNayar"
#include "screenspace/light/light"

//G-buffer
uniform sampler2D comp1;
uniform usampler2D comp2;
uniform sampler2D comp3;
uniform sampler2D comp4;
uniform mat4 inverseProjection;

uniform vec3 cameraPos;

uniform int lightNumber;
uniform LightSource sources[%MAX_LIGHTS%];

in vec2 vTexCoord;

layout(location = 0) out vec4 fragColor;

vec3 decodeNormal();
vec3 calcScenePos();

void main(void) {
    vec3 scenePos = calcScenePos();
    vec3 normal = decodeNormal();
    float irradiance = getLight(scenePos, normal);
    fragColor.rgb = texture(comp1, vTexCoord).rgb * irradiance;
    fragColor.a = 1;
}

float getLight(vec3 scenePos, vec3 normal) {
    float totalIrradiance = 0;
    for(int i = 0; i < lightNumber; i++) {
        LightSource source = sources[i];
        vec3 lightDirection = normalize(source.pos - scenePos);
        vec3 eyeDir = normalize(cameraPos - scenePos);
        totalIrradiance += getOrenNayarRadiance(lightDirection, eyeDir, normal, 0.1, 0.5);
    }
    return totalIrradiance;
}

vec3 decodeNormal() {
    uint packed = texture(comp2, vTexCoord).r;
    vec2 encoded = v2UnpackSignedNorm(packed, 11);
    return decode(encoded);
}

vec3 calcScenePos() {
    float depth = texture(comp4, vTexCoord).r;
    vec4 ortPos = vec4(vTexCoord, depth, 1.0);
    vec4 scenePos = inverseProjection * ortPos;
    return scenePos.xyz / scenePos.w;
}
