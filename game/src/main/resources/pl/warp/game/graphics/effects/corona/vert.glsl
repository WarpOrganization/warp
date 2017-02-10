#version 330

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 rotationMatrix;
uniform mat4 cameraMatrix;

smooth out vec3 spherePos;

layout(location = 0) in vec3 inVertex;
layout(location = 0) in vec2 inTexCoord;
layout(location = 0) in vec3 inNormal;

void main(void) {
    vec4 pos4 = rotationMatrix * vec4(inVertex, 1.0f);
    spherePos = pos4.xyz / pos4.w;
    vec4 pos = modelMatrix * pos4;
    gl_Position = projectionMatrix * cameraMatrix * pos;
}
