#version 330

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 rotationMatrix;
uniform mat4 cameraMatrix;

uniform vec3 cameraPos;
uniform mat4 mCameraMatrix;

out vec3 onSpherePos;

out vec3 vPos3;
out vec3 vEyeDir;
smooth out vec3 vNormal;

layout(location = 0) in vec3 inVertex;
layout(location = 1) in vec2 inTexCoord; out vec3 vTexCoord;
layout(location = 2) in vec3 inNormal;

vec3 calculateNormal(vec3 normal);

void main(void) {

    vec4 vPos = modelMatrix * vec4(inVertex, 1.0f);
    vPos3 = vPos.xyz / vPos.w;
    vEyeDir = normalize(vPos3 - cameraPos);

    onSpherePos = inVertex;
    vNormal = calculateNormal(normalize(onSpherePos));
    gl_Position = projectionMatrix * cameraMatrix * vPos;
    vTexCoord = normalize(onSpherePos);
}

vec3 calculateNormal(vec3 normal) {
    mat3 normalMatrix;
    normalMatrix[0] = rotationMatrix[0].xyz;
    normalMatrix[1] = rotationMatrix[1].xyz;
    normalMatrix[2] = rotationMatrix[2].xyz;
    return normalMatrix * normal;
}