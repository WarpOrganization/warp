#version 330

layout(location = 0) in vec2 inVertex;

uniform mat3x2 transformationMatrix;
uniform mat4 projectionMatrix;
uniform float v[4];

out vec2 texCoord;

void main() {
    gl_Position = projectionMatrix * vec4(transformationMatrix * vec3(inVertex, 1), 0, 1);
    texCoord = vec2(inVertex.x, -inVertex.y);
}
