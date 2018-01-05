#version 330

#ifndef SCENE_TESS
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
#endif
uniform mat4 modelMatrix;
uniform mat3 rotationMatrix;

layout(location = 0) in vec3 inVertex;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;

out vec3 oWorldPos;
out vec2 oTexCoord;
out vec3 oNormal;

void main(void) {
    vec4 vPos = modelMatrix * vec4(inVertex, 1.0f);
    oWorldPos = vPos.xyz / vPos.w;

    oNormal = rotationMatrix * inNormal;

    oTexCoord = inTexCoord;

    #ifndef SCENE_TESS
    gl_Position = projectionMatrix * viewMatrix * vPos;
    #endif
}