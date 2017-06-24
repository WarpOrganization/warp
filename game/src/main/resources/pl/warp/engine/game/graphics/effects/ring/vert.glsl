#version 330

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 rotationMatrix;
uniform mat4 cameraMatrix;

uniform mat4 mCameraMatrix;

uniform vec3 cameraPos;

smooth out vec3 onRingPos;

out vec3 vPos3;
out vec3 vEyeDir;
smooth out vec3 vNormal;

layout(location = 0) in vec3 inVertex;
layout(location = 1) in vec2 inTexCoord; out vec2 vTexCoord;
layout(location = 2) in vec3 inNormal;

vec3 calculateNormal(vec3 normal);

void main(void) {
    onRingPos = inVertex;

    vec4 vPos = modelMatrix * vec4(inVertex, 1.0f);
    vPos3 = vPos.xyz / vPos.w;
    vEyeDir = normalize(vPos3 - cameraPos);

    vNormal = calculateNormal(inNormal);
    gl_Position = projectionMatrix * cameraMatrix * vPos;
}

vec3 calculateNormal(vec3 normal) {
    mat3 normalMatrix;
    normalMatrix[0] = rotationMatrix[0].xyz;
    normalMatrix[1] = rotationMatrix[1].xyz;
    normalMatrix[2] = rotationMatrix[2].xyz;
    return normalMatrix * normal;
}
