#version 330

layout(location = 0) in vec4 inVertex;

smooth out vec2 blurCoords[15];

uniform vec2 blurDirection;

uniform int displaySize;

void main(void) {
    gl_Position = inVertex;
    vec2 centerTexCoord = gl_Position.xy * 0.5 + 0.5;
    float pixelSize = 1.0 / displaySize;
    for(int i = -7; i <= 7; i++) {
        vec2 coord = centerTexCoord + blurDirection * pixelSize * i;
        coord.x = clamp(coord.x, 0.001, 0.999);
        coord.y = clamp(coord.y, 0.001, 0.999);
        blurCoords[i + 7] = coord;
    }
}