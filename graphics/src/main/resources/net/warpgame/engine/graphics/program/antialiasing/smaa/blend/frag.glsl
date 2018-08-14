#version 410

// BLEND PASS

#include "antialiasing/smaa/smaa"

uniform sampler2D edgeTex;
uniform sampler2D areaTex;
uniform sampler2D searchTex;

in vec2 texCoord;
in vec2 pixCoord;
in vec4 offset[3];

out vec4 fragColor;

void main() {
  fragColor = SMAABlendingWeightCalculationPS(texCoord, pixCoord, offset, edgeTex, areaTex, searchTex, ivec4(0));
  fragColor.a = 1;
}