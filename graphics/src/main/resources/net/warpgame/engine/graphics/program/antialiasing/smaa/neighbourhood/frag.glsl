#version 410 compatibility

// NEIGHBOURHOOD PASS

#include "antialiasing/smaa/smaa"

uniform sampler2D albedoTex;
uniform sampler2D blendTex;

in vec2 texCoord;
in vec4 offset;

out vec4 fragColor;

void main() {
  fragColor = SMAANeighborhoodBlendingPS(texCoord, offset, albedoTex, blendTex);
}