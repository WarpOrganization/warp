#version 330

#include "util/noise3d"

uniform float fluidLevel = 0.13f;
uniform float seed = 129;

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
    float n1 = noise(pos, 8, 1.6, 0.5);
    float n2 = max(0, ridgedNoise(pos, 8, 2, 0.5) - (1 - mountainsHeight));
    return n1 * 0.5 + n2 * 0.5;
}

vec3 getHeightColor(float height){
    vec3 color;
    if(height < fluidLevel) color = vec3(0, 0, 0.6);
    else {
        color = vec3(0.2 * (1 + max(0, height - 0.2) * 25), 1, 0.2);
    }

    return color;
}