#version 330

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 rotationMatrix;
uniform mat4 cameraMatrix;

uniform vec3 cameraPos;
uniform bool rmirror;
uniform mat4 mCameraMatrix;

layout(location = 0) in vec3 inVertex;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;


void main(void) {
    vec4 pos = modelMatrix * vec4(inVertex, 1.0f);
    gl_Position = projectionMatrix * cameraMatrix * pos;
}
