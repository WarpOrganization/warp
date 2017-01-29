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

//From vertex shader
smooth in vec3 vNormal;
in vec3 vPos3;
in vec2 vTexCoord;
in vec3 vEyeDir;

layout(location = 0) out vec4 fragColor;

vec3 getLight();
float getDirectionCoeff(SpotLightSource source);
float getAttenuation(SpotLightSource source);
float getDiffuse(SpotLightSource source, vec3 lightDir, float directionCoeff);
float getSpecular(SpotLightSource source, vec3 lightDir, float diffuse);
void applyBrightnessTexture();
bool isNan(vec3 vec);

void main(void) {
    if(lightEnabled)
        fragColor = vec4(getLight(), 1) * texture(material.mainTexture, vTexCoord);
    else fragColor = texture(material.mainTexture, vTexCoord);
    fragColor.rgb *= material.brightness;
    if(material.hasBrightnessTexture) applyBrightnessTexture();
    if(isNan(fragColor.rgb)) discard;
    fragColor.a *= material.transparency;
}

vec3 getLight() {
    vec3 totalLight = vec3(0);
    for(int i = 0; i < numSpotLights; i++){
        SpotLightSource source = spotLightSources[i];
        float directionCoeff = getDirectionCoeff(source);
        float attenuation = getAttenuation(source);
        if(attenuation < 0.01) continue;
        vec3 ambient = source.ambientColor;
        vec3 lightDir = normalize(source.position - vPos3);
        float diffuse = getDiffuse(source, lightDir, directionCoeff);
        float specular = getSpecular(source, lightDir, diffuse);
        totalLight += (((diffuse + specular) * source.color) + ambient) * attenuation;
    }
    return totalLight;
}

float getDirectionCoeff(SpotLightSource source) {
    if(source.coneAngle >= 180) return 1.0f; // non-directional

    vec3 direction = normalize(source.position - vPos3);
    float angle = acos(dot(direction, source.coneDirection));
    if(angle < source.coneAngle)
        return 1.0f;
    else if(angle < source.coneAngle + source.coneGradient)
        return 1.0f - (angle - source.coneAngle) / source.coneGradient;
    else return 0.0f;
}

float getAttenuation(SpotLightSource source) {
    float distance = length(source.position - vPos3);
    return exp(-pow((distance * source.attenuation), source.gradient));
}

float getDiffuse(SpotLightSource source, vec3 lightDir, float directionCoeff) {
     return max(0, dot(vNormal, lightDir)) * directionCoeff;
}

float getSpecular(SpotLightSource source, vec3 lightDir, float diffuse) {
    if(material.shininess > 0) {
        vec3 reflection = normalize(reflect(-lightDir, vNormal));
        float spec = max(0.0, dot(-vEyeDir, reflection));
        float specVal = pow(spec, SPECULAR_EXPONENT / material.shininess);
        return diffuse * specVal * material.shininess / 10;
    } else return 0.0;
}

void applyBrightnessTexture() {
    vec3 fragBrightness = texture(material.brightnessTexture, vTexCoord).rgb * BRIGHTNESS_TEXTURE_MULTIPLIER;
    fragColor.rgb += fragBrightness;
}

bool isNan(vec3 vec) {
    return (!(fragColor.r < 1000000.0)) || (!(fragColor.g < 1000000.0)) || (!(fragColor.b < 1000000.0));
}
