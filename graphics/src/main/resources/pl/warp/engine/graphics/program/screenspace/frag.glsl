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
uniform mat4 inverseCamera;
uniform vec3 cameraPos;

uniform int lightNumber;
uniform LightSource sources[$MAX_LIGHTS$];

in vec2 vTexCoord;

layout(location = 0) out vec4 fragColor;

vec3 decodeNormal();
vec3 calcScenePos();
vec3 getLight(vec3 scenePos, vec3 normal);

void main(void) {
    vec3 scenePos = calcScenePos();
    vec3 normal = decodeNormal();
    vec3 irradiance = getLight(scenePos, normal);
    fragColor.rgb = texture(comp1, vTexCoord).rgb * irradiance;
    fragColor.a = 1;
}

vec3 getLight(vec3 scenePos, vec3 normal) {
    vec3 totalIlluminance = vec3(0);
    for(int i = 0; i < lightNumber; i++) {
        LightSource source = sources[i];
        vec3 lightDirection = normalize(source.pos - scenePos);
        vec3 eyeDir = normalize(cameraPos - scenePos);
        vec3 radiance = source.color * getOrenNayarRadiance(lightDirection, eyeDir, normal, 0.5, 0.8);
        float dist = distance(source.pos, scenePos);
        totalIlluminance += radiance * (1.0/dist);//TODO inverse square law
    }
    return totalIlluminance + 0.2;
}

vec3 decodeNormal() {
    uint packed = texture(comp2, vTexCoord).r;
    vec2 encoded = v2UnpackSignedNorm(packed, 11);
    return decode(encoded);
}

vec3 calcScenePos() {
    float depth = texture(comp4, vTexCoord).r;
    float z = depth * 2.0 - 1.0;
    vec4 clipSpacePosition = vec4(vTexCoord * 2.0 - 1.0, z, 1.0);
    vec4 scenePos = inverseProjection * clipSpacePosition;
    vec4 viewPos = scenePos / scenePos.w;
    vec4 worldPos = inverseCamera * viewPos;
    return worldPos.xyz;
}
