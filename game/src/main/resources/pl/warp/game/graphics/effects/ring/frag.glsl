#version 400
precision highp float;

struct SpotLightSource {
    vec3 position;
    vec3 coneDirection;
    float coneAngle;
    float coneGradient;
    vec3 color;
    vec3 ambientColor;
    float attenuation;
    float gradient;
};

const float SPECULAR_EXPONENT = 100.0;
const float SHININESS = 5.0;

uniform SpotLightSource spotLightSources[$MAX_LIGHTS$];
uniform int numSpotLights;

uniform sampler1D ringColors;
uniform float ringStart;
uniform float ringEnd;
uniform bool renderShadow;
uniform vec3 planetPos;
uniform float planetRadius;
const float SHADOW_GRADIENT = 0.2f;


smooth in vec3 onRingPos;

in vec3 vPos3; //absolute position on scene
in vec2 vTexCoord;
in vec3 vEyeDir;
smooth in vec3 vNormal;

const float discardAlpha = 0.05f;
const float lightMultiplier = 4.0f;

layout(location = 0) out vec4 fragColor;


vec3 getLight();
float getAttenuation(SpotLightSource source, vec3 pos);
float shadow(SpotLightSource source);

void main() {
    float distance = length(onRingPos);
    if(distance < ringStart || distance > ringEnd) discard;
    float texturePos = distance - ringStart;
    vec4 color = texture(ringColors, texturePos);
    color.rgb *= getLight();
    if(color.a < discardAlpha) discard;
    fragColor = color;
}

vec3 getLight(){
    vec3 light = vec3(0);
    for(int i = 0; i < numSpotLights; i++){
        SpotLightSource source = spotLightSources[i];
        float attenuation = getAttenuation(source, vPos3);
        if(renderShadow) attenuation *= shadow(source);
        light += attenuation * source.color;
        light += source.ambientColor;
    }
    return light;
}

float getAttenuation(SpotLightSource source, vec3 pos) {
    float distance = length(source.position - pos);
    return exp(-pow((distance * source.attenuation), source.gradient));
}

float shadow(SpotLightSource source){
    vec3 sourcePos = source.position;
    float areaTwice = length(cross((planetPos - sourcePos), (planetPos - vPos3)));
    float baseLength = length(vPos3 - sourcePos);
    float planetDistance = distance(planetPos, sourcePos);
    float fragDistance = distance(vPos3, sourcePos);
    if(planetDistance > fragDistance) return 1.0;
    else {
        float distance = areaTwice / baseLength;
        float gradientStart = planetRadius * (1.0 - (SHADOW_GRADIENT * 0.5));
        float value = (distance - gradientStart) / (SHADOW_GRADIENT * planetRadius);
        return clamp(value, 0.0, 1.0);
    }
}
