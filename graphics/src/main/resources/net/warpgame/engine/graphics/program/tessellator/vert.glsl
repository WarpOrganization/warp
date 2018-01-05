#version 330

layout(location = 0) in vec3 inVertex;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;

out vec3 oWorldPos;
out vec2 oTexCoord;
out vec3 oNormal;

void main(void) {
    oWorldPos = inVertex;
    oNormal = inNormal;
    oTexCoord = inTexCoord;
}