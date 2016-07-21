#version 330

layout(location = 0) in vec4 inVertex;

out vec2 blurCoords[11];

uniform vec2 blurDirection;

uniform int screenSize;

void main(void) {
    vTexCoord = inTexCoord;
    gl_Position = inVertex;
    vec2 centerTexCoord = position * 0.5 + 0.5;
    float pixelSize = 1.0 / screenSize;
    for(int i = -5; i < 5; i++)
        blurCoords[i + 5] = centerTexCoord + blurDirection * pixelSize * i;
}