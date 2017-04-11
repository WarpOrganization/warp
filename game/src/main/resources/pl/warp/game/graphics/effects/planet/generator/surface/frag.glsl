#version 330

#include "util/noise3d"

struct Biome {
    float height;
    float pos;
    vec3 color;
};

uniform Biome biomes[$BIOME_COUNT$];
uniform int biomeCount;

uniform float seed = 130;

smooth in vec3 spherePos;

float getHeight(vec3 pos);
vec3 getBiome(vec3 pos, float height);
vec4 getClouds(vec3 seedPos);
float getBiomeValue(float pos, float height);
vec3 getBiomeColor(vec3 pos, float height, Biome biome, Biome previousBiome);

void main() {
    vec3 seedPos = spherePos + seed * 7.6;
    vec3 color;
	float height = getHeight(seedPos);
	color = getBiome(seedPos, height);
	vec4 cloudsColor = getClouds(seedPos);
	color = mix(color, cloudsColor.rgb, vec3(cloudsColor.a));
	gl_FragColor = vec4(color, 1);
}

const float terrainExponent = 1.3f;
const float mountainsHeight = 0.7f;

float getHeight(vec3 pos) {
    float n1 = noise(pos, 16, 1, 0.5);
    return 0.5 + n1;
}

vec3 getBiome(vec3 pos, float height){
    Biome previousBiome = biomes[biomeCount - 1];
    for(int i = biomeCount - 1; i > -1; i--){
        Biome biome = biomes[i];
        if(getBiomeValue(pos.y / length(pos), height) > getBiomeValue(biome.pos, biome.height))
            return getBiomeColor(pos, height, biome, previousBiome);
        else previousBiome = biome;
    }
    return previousBiome.color;
}

const float posExp = 2.0;
float getBiomeValue(float pos, float height){
    float lat = cos(pos);

    return pow(lat, posExp) + height;
}

vec3 getBiomeColor(vec3 pos, float height, Biome biome, Biome previousBiome) {
    float r = ridgedNoise(pos, 2, 10, 0.5) * 0.25;
    vec3 biomeColor = biome.color;
    return (biomeColor.b > 0.5) ? biomeColor : biomeColor * (vec3(r + 0.75));
}


vec4 getClouds( vec3 pos ) {
	return vec4(0);
}
