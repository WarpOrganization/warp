#version 410

// EDGE PASS

#include "antialiasing/smaa/smaa"

uniform sampler2D albedoTex;
in vec2 texCoord;
in vec4 offset[3];
in vec4 dummy2;

out vec2 fragColor;

void main() {
  #if SMAA_PREDICATION == 1
    fragColor.rg = SMAAColorEdgeDetectionPS(texCoord, offset, albedoTex, depthTex); //TODO PREDICATION
  #else
    fragColor.rg = SMAAColorEdgeDetectionPS(texCoord, offset, albedoTex);
  #endif
} 