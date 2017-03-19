#version 330

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 rotationMatrix;
uniform mat4 cameraMatrix;

smooth out vec3 spherePos;

uniform float radius;

layout(location = 0) in vec3 inVertex;
layout(location = 0) in vec2 inTexCoord;
layout(location = 0) in vec3 inNormal;

out float fragmentRadius;

void main(void) {
    vec4 fragmentPos = projectionMatrix * cameraMatrix * modelMatrix * vec4(inVertex * radius, 1.0f);
    vec4 centerPos = projectionMatrix * cameraMatrix * modelMatrix * vec4(0.0f, 0.0f, 0.0f, 1.0f);
    float scale = 1;
    float realRadius = distance((fragmentPos / fragmentPos.w).xy, (centerPos / centerPos.w).xy);
    fragmentRadius = realRadius;
    gl_Position = fragmentPos;
}