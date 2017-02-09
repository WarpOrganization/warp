#version 330

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 rotationMatrix;
uniform mat4 cameraMatrix;


layout(location = 0) in vec3 inVertex;

void main(void) {
    gl_Position = projectionMatrix * cameraMatrix * modelMatrix * vec4(inVertex, 1.0f);
}
