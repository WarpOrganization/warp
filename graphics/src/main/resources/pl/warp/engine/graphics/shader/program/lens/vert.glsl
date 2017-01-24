#version 330

precision highp float;

uniform vec2 sourcePos;

layout(location = 0) in float offset;
layout(location = 1) in float scale;
layout(location = 2) in int textureIndex;
layout(location = 3) in vec3 flareColor;

out float vScale;
out int vTextureIndex;
out float vVisibility;
out vec3 vFlareColor;

float getVisibility();

void main(void) {
    vVisibility = getVisibility();
    vScale = scale;
    vTextureIndex = textureIndex;
    vFlareColor = flareColor;
    gl_Position.xy = sourcePos * offset;
    gl_Position.w = 1;
}

float getVisibility() {
    float distance = length(sourcePos);
    return (0.5 - (distance / 2)) * 0.3;
}