#version 330
precision highp float;

#define OREN_NAYAR 5
#define HEIDRICH_SEIDEL 1
#define RENDER 9

#include "util/floatPack"
#include "util/vectorEncoding"
#include "util/flags"
#include "screenspace/light/orenNayar"
#include "screenspace/light/heidrichSeidel"
#include "screenspace/light/light"

//G-buffer
uniform sampler2D comp1;
uniform isampler2D comp2;
uniform sampler2D comp3;
uniform sampler2D comp4;
uniform samplerCube cube;

uniform mat4 inverseProjection;
uniform mat4 inverseCamera;
uniform mat4 rotationCamera;
uniform vec3 cameraPos;

uniform int lightNumber;
uniform LightSource sources[$MAX_LIGHTS$];
uniform vec3 ambientLight = vec3(0.05);

in vec2 vTexCoord;

layout(location = 0) out vec4 fragColor;

vec3 decodeNormal();
vec3 calcScenePos();
vec3 getLight(vec3 scenePos, vec3 normal, uint flags);
vec3 getLightFromSource(LightSource source, vec3 scenePos, vec3 normal, float roughness, uint flags);
void processRender(uint flags);
void processBackground();

void main(void) {
    int flags = texture(comp2, vTexCoord).r >> 22;
    if(isSet(flags, RENDER)) {
        processRender(flags);
    } else {
        processBackground();
    }
    fragColor.a = 1;
}

void processRender(uint flags) {
    vec3 scenePos = calcScenePos();
    vec3 normal = decodeNormal();
    float roughness = texture(comp3, vTexCoord).r;
    vec3 irradiance = getLight(scenePos, normal, flags) + ambientLight;
    fragColor.rgb = texture(comp1, vTexCoord).rgb * irradiance;
}

void processBackground() {
    vec4 toMapVec = inverse(rotationCamera) * inverseProjection  * vec4(vTexCoord.x * 2 - 1, vTexCoord.y * 2 - 1, 1, 1);
    vec3 rotated = normalize(toMapVec.xyz / toMapVec.w);
    fragColor.rgb = texture(cube, rotated.xyz).rgb;
}

vec3 getLight(vec3 scenePos, vec3 normal, uint flags) {
    vec3 totalIlluminance = vec3(0);
    float roughness = texture(comp3, vTexCoord).r;
    for(int i = 0; i < lightNumber; i++) {
        LightSource source = sources[i];
        totalIlluminance += getLightFromSource(source, scenePos, normal, roughness, flags);
    }
    return totalIlluminance;
}

vec3 getLightFromSource(LightSource source, vec3 scenePos, vec3 normal, float roughness, uint flags) {
    vec3 lightDirection = normalize(source.pos - scenePos);
    vec3 eyeDirection = normalize(cameraPos - scenePos);
    vec3 radiance = vec3(0);

    if(isSet(flags, OREN_NAYAR)) {
        float onr /*oboz narodowo-renderowy*/ = getOrenNayarRadiance(lightDirection, eyeDirection, normal, roughness, 0.8);
        radiance += source.color * onr;
    }

    if(isSet(flags, HEIDRICH_SEIDEL)) {
        float threadDir = texture(comp3, vTexCoord).b;
        float shininess = texture(comp3, vTexCoord).b;
        float hsr = getHeidrichSeidelRadiance(threadDir, shininess, normal, lightDirection, eyeDirection);
        radiance += source.color * hsr;
    }

    float dist = distance(source.pos, scenePos);
    return radiance * (1.0/dist);//TODO inverse square law, but it looks crappy atm
}

vec3 decodeNormal() {
    int pkd = int(texture(comp2, vTexCoord).r);
    vec2 encoded = v2UnpackSignedNorm(pkd, 11);
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
