#version 330
precision mediump float;

#include "util/floatPack"
#include "util/vectorEncoding"
#include "util/flags"

uniform sampler2D diffuseTexture;
uniform sampler2D normalMap;
uniform bool hasNormalMap;
uniform float materialRoughness;
uniform float materialShininess;

in vec3 oWorldPos;
in vec2 oTexCoord;
in vec3 oNormal;

//gbuffer
layout(location = 0) out vec3 comp1; //diffuse R, diffuse G, diffuse B
layout(location = 1) out uint comp2; //packed normal_1, normal_2, flags
layout(location = 2) out vec3 comp3; //roughness, shininess, thread dir

void calculateDiffuse();
void calculateNormal();
void setFlags();
void setProperties();

void main(void) {
    calculateDiffuse();
    calculateNormal();
    setFlags();
    setProperties();
}

void calculateDiffuse() {
    comp1 = texture(diffuseTexture, oTexCoord).rgb;
}

void calculateNormal() {
    vec2 encoded;
    if(hasNormalMap) {
        //TODO bitangent...?
        encoded = encode(oNormal);
    } else {
        encoded = encode(oNormal);
    }
    comp2 |= uint(v2PackSignedNorm(encoded, 11));
}

void setFlags() {
    bool beckmann = true;
    bool heidrichSeidel = false;
    bool emissive = false;
    bool subsurface = false;
    bool metal = false;
    bool orenNayar = true;
    bool render = true; //always
    int s = flag(beckmann,       0)
        |    flag(heidrichSeidel, 1)
        |    flag(emissive,       2)
        |    flag(subsurface,     3)
        |    flag(metal,          4)
        |    flag(orenNayar,      5)
        |    flag(render,         9);
    comp2 |= uint(s << 22);
}

void setProperties() {
    comp3.r = materialRoughness;
    comp3.g = materialShininess;
    comp3.b = 0.5; //threadDir
}