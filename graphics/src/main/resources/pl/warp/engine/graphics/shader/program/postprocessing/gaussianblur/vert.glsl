#version 330

layout(location = 0) in vec4 inVertex;

smooth out vec2 blurCoords[11];

uniform vec2 blurDirection;

uniform int displaySize;

void main(void) {
    gl_Position = inVertex;
    vec2 centerTexCoord = gl_Position.xy * 0.5 + 0.5;
    float pixelSize = 1.0 / displaySize;
    for(int i = -5; i < 5; i++) {
        blurCoords[i + 5] = centerTexCoord + blurDirection * pixelSize * i;
    }
}