#version 330
uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat3 rotationMatrix;

layout(location = 0) in vec3 inVertex;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;

out vec3 vPos3;
out vec2 vTexCoord;
out vec3 vNormal;

void main(void) {
    vNormal = rotationMatrix * inNormal;

    vec4 vPos = modelMatrix * vec4(inVertex, 1.0f);
    vPos3 = vPos.xyz / vPos.w;

    vTexCoord = inTexCoord;
    gl_Position = projectionMatrix * viewMatrix * vPos;
}