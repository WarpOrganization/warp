#version 330
precision mediump float;

#include "util/floatPack"
#include "util/vectorEncoding"

uniform sampler2D diffuseTexture;
uniform sampler2D materialTexture;
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
    vec2 encoded = encode(oNormal);
    comp2 |= v2PackSignedNorm(encoded, 11);
}

void setFlags() {
    uint beckmann = 1;
    uint heidrichSeidel = 1;
    uint emissive = 0;
    uint subsurface = 0;
    uint metal = 0;
    uint orenNayar = 0;
    uint s = (beckmann       << 0)
        |    (heidrichSeidel << 1)
        |    (emissive       << 2)
        |    (subsurface     << 3)
        |    (metal          << 4)
        |    (orenNayar      << 5);
    comp2 |= s << 22;
}

void setProperties() {
    comp3.r = materialRoughness;
    comp3.g = materialShininess;
    comp3.b = 0.5; //threadDir
}