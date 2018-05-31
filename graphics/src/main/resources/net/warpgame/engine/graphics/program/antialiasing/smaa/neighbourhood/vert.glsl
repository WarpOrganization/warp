#version 410 compatibility

// NEIGHBOURHOOD PASS

#include "antialiasing/smaa/smaa"

layout(location = 0) in vec4 inVertex;
layout(location = 1) in vec4 inTexCoord;
layout(location = 2) in vec4 inNormal;

out vec2 texCoord;
out vec4 offset;

void main() {
  gl_Position = inVertex;
  texCoord = gl_Position.xy * 0.5 + vec2(0.5);
  SMAANeighborhoodBlendingVS(texCoord, offset);
} 