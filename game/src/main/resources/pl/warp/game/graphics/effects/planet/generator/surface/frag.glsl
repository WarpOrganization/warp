#version 330

#include "util/noise3d"

uniform float fluidLevel = 0.5f;
uniform float seed = 130;

smooth in vec3 spherePos;

float getHeight(vec3 pos);
vec3 getHeightColor(float height);

void main() {
    vec3 seedPos = spherePos + seed * 7.6;
    vec3 color;
	float height = getHeight(seedPos);
	color = getHeightColor(height);
	gl_FragColor = vec4(color, 1);
}

const float terrainExponent = 1.3f;
const float mountainsHeight = 0.7f;

float getHeight(vec3 pos) {
    float n1 = noise(pos, 8, 3, 0.5);
    float n2 = max(0,  noise(pos, 8, 2, 0.8) - (1 - mountainsHeight));
    return 0.5 + n1 + n2;
}

vec3 getHeightColor(float height){
    vec3 color;
    if(height < fluidLevel) color = vec3(64.0 / 255, 78.0 / 255, 223.0 / 255);
    else {
        color = vec3(0.2 * (max(0, height - 0.4) * 8), 0.2 + 0.2 * (max(0, height - 0.4) * 8), 0.2 * (max(0, height - 0.4) * 8));
    }
    return color;
}