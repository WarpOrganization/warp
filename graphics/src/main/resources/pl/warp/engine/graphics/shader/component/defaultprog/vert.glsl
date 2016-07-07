#version 450

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 rotationMatrix;
uniform mat4 cameraMatrix;

uniform vec3 cameraPos;
uniform bool rmirror;
uniform mat4 mCameraMatrix;

out vec3 vPos3;
out vec3 vEyeDir;

layout(location = 0) in vec4 inVertex;
layout(location = 1) in vec2 inTexCoord; out vec2 vTexCoord;
layout(location = 2) in vec3 inNormal; smooth out vec3 vNormal;

vec3 calculateNormal();

void main(void) {

    vNormal = calculateNormal();

    vec4 vPos = modelMatrix * inVertex;
    vPos3 = vPos.xyz / vPos.w;
    vEyeDir = normalize(vPos3 - cameraPos);

    vTexCoord = inTexCoord;
    gl_Position = projectionMatrix * cameraMatrix * vPos;
}

vec3 calculateNormal() {
    mat3 normalMatrix;
    normalMatrix[0] = rotationMatrix[0].xyz;
    normalMatrix[1] = rotationMatrix[1].xyz;
    normalMatrix[2] = rotationMatrix[2].xyz;
    return normalMatrix * inNormal;
}