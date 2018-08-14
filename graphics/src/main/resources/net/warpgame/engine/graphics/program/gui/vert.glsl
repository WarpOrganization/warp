#version 330

layout(location = 0) in vec2 inVertex;

uniform mat4 transformationMatrix;

out vec2 texCoord;

void main() {
    gl_Position = transformationMatrix * vec4(inVertex, 0, 1);
    texCoord = vec2((inVertex.x+1.0)/2.0, 1 - (inVertex.y+1.0)/2.0);
}
