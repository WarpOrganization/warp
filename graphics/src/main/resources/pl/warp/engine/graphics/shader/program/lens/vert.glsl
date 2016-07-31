#version 330

precision highp float;

uniform vec2 sourcePos;

layout(location = 0) in float offset;
layout(location = 1) in float scale;
layout(location = 2) in int textureIndex;
layout(location = 3) in vec3 flareColor;

out vData {
    float scale;
    int textureIndex;
    float visibility;
    vec3 flareColor;
} pointData;

float getVisibility();

void main(void) {
    pointData.visibility = getVisibility();
    pointData.scale = scale;
    pointData.textureIndex = textureIndex;
    pointData.flareColor = flareColor;
    gl_Position.xy = sourcePos * offset;
    gl_Position.w = 1;
}

float getVisibility() {
    float distance = length(sourcePos);
    return (0.5 - (distance / 2)) * 0.3;
}