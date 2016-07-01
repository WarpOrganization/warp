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
layout(location = 1) in vec2 inTexCoord; out vec3 vTexCoord;
layout(location = 2) in vec3 inNormal; out vec3 vNormal;

void main(void) {

    vNormal = calculateNormal();

    vec4 vPos = modelMatrix * inVertex;
    vPos3 = vPos.xyz / vPos.w;
    vEyeDir = normalize(vPos3 - cameraPos);

    vTexCoord = vec3(inTexCoord.x, inTexCoord.y, textureOffset);
    gl_Position = projectionMatrix * cameraMatrix * vPos;
}

vec3 calculateNormal() {
    mat3 normalMatrix;
    normalMatrix[0] = rotMatrix[0].xyz;
    normalMatrix[1] = rotMatrix[1].xyz;
    normalMatrix[2] = rotMatrix[2].xyz;
    return normalMatrix * inNormal;
}