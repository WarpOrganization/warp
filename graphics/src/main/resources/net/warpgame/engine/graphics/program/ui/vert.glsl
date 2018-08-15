#version 330

layout(location = 0) in vec2 inVertex;

uniform mat3x2 transformationMatrix;

out vec2 texCoord;

void main() {
    gl_Position = vec4(transformationMatrix * vec3(inVertex, 1), 0, 1);
    texCoord = vec2((inVertex.x+1.0)/2.0, 1 - (inVertex.y+1.0)/2.0);
}
