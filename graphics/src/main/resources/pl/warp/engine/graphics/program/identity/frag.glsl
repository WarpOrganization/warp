#version 330
precision highp float;

#include "util/floatPack"
#include "util/vectorEncoding"

//G-buffer
uniform sampler2D comp1;
uniform usampler2D comp2;
uniform sampler2D comp3;
uniform sampler2D comp4;

in vec2 vTexCoord;

layout(location = 0) out vec4 fragColor;

vec3 decodeNormal();

void main(void) {
    fragColor.rgb = decodeNormal();
    fragColor.a = 1;
}

vec3 decodeNormal() {
    uint packed = texture(comp2, vTexCoord).r;
    vec2 encoded = v2UnpackSignedNorm(packed, 11);
    return decode(encoded);
}

