#version 330
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

struct Material {
    sampler2D mainTexture;
    sampler2D brightnessTexture;
    bool hasBrightnessTexture;
    float brightness;
    float shininess;
    float transparency;
};
const float SPECULAR_EXPONENT = 100.0;
const float BRIGHTNESS_TEXTURE_MULTIPLIER = 3.0;

uniform Material material;

uniform SpotLightSource spotLightSources[$MAX_LIGHTS$];
uniform int numSpotLights;

uniform bool lightEnabled;

uniform vec3 cameraPos;

uniform vec2 direction;
uniform float delta;

//From vertex shader
smooth in vec3 vNormal;
in vec3 vPos3;
in vec2 vTexCoord;
in vec3 vEyeDir;

layout(location = 0) out vec4 fragColor;

#include "util/light"

void applyBrightnessTexture();

void main(void) {
    if(lightEnabled) {
        vec3 light = getLight(vNormal, vPos3, vEyeDir, material.shininess, SPECULAR_EXPONENT);
        vec2 animatedTexPos = vTexCoord + direction * delta;
        fragColor = vec4(light, 1) * texture(material.mainTexture, animatedTexPos);
    }
    else fragColor = texture(material.mainTexture, vTexCoord);
    fragColor.rgb *= material.brightness;
    if(material.hasBrightnessTexture) applyBrightnessTexture();
    if(isNan(fragColor.rgb)) discard;
    fragColor.a *= material.transparency;
}

void applyBrightnessTexture() {
    vec3 fragBrightness = texture(material.brightnessTexture, vTexCoord).rgb * BRIGHTNESS_TEXTURE_MULTIPLIER;
    fragColor.rgb += fragBrightness;
}