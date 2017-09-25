#version 330

uniform mat4 modelMatrix;
uniform mat3 rotationMatrix;

layout(location = 0) in vec3 inVertex;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;

out vec3 tcsWorldPos;
out vec2 tcsTexCoord;
out vec3 tcsNormal;

void main(void) {
    vec4 vPos = modelMatrix * vec4(inVertex, 1.0f);
    tcsWorldPos = vPos.xyz / vPos.w;

    tcsNormal = rotationMatrix * inNormal;

    tcsTexCoord = inTexCoord;
}