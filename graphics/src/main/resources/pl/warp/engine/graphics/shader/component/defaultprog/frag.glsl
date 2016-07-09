#version 330
precision highp float;

struct SpotLightSource { //TODO merge with directional?
    vec3 position;
    vec3 color;
    vec3 ambientColor;
    float attenuation;
    float gradient;
};

struct DirectionalLightSource {
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
    float brightness;
    float shininess;
};
const float SPECULAR_EXPONENT = 100.0;

uniform Material material;

const int MAX_SPOT_LIGHTS = 10;
uniform SpotLightSource spotLightSources[MAX_SPOT_LIGHTS];
uniform int numSpotLights;

const int MAX_DIRECTIONAL_LIGHTS = 25;
uniform DirectionalLightSource directionalLightSources[MAX_DIRECTIONAL_LIGHTS];
uniform int numDirectionalLights;

uniform bool lightEnabled;

uniform vec3 cameraPos;

//From vertex shader
smooth in vec3 vNormal;
in vec3 vPos3;
in vec2 vTexCoord;
in vec3 vEyeDir;

out vec4 fragColor;

vec3 getSpotLight();
vec3 getDirectionalLight();

void main(void) {
    if(lightEnabled) {
        vec3 sumLight = getSpotLight() + getDirectionalLight();
        fragColor = vec4(sumLight, 1) * texture(material.mainTexture, vTexCoord);
    } else fragColor = texture(material.mainTexture, vTexCoord);
    fragColor.rgb *= material.brightness;
}

vec3 getDirectionalLight() {
    vec3 totalLight = vec3(0);
    for(int i = 0; i < numDirectionalLights; i++){
        DirectionalLightSource source = directionalLightSources[i];

        //Attenuation
        float distance = length(source.position - vPos3);
        float att = exp(-pow((distance * source.attenuation), source.gradient));
        if(att < 0.01) continue;

        vec3 direction = normalize(source.position - vPos3);
        float angle = acos(dot(direction, source.coneDirection));
        float directionCoeff;

        if(angle < source.coneAngle)
            directionCoeff = 1.0f;
        else if(angle < source.coneAngle + source.coneGradient)
            directionCoeff = 1.0f - (angle - source.coneAngle) / source.coneGradient;
        else directionCoeff = 0.0f;

        //Ambient
        vec3 ambient = source.ambientColor;

        //Diffuse
        vec3 lightDir = normalize(source.position - vPos3);
        float diff = max(0, dot(vNormal, lightDir)) * directionCoeff;

        //Specular
        float specular = 0;
        if(material.shininess > 0) {
            vec3 reflection = normalize(reflect(-lightDir, vNormal));
            float spec = max(0.0, dot(-vEyeDir, reflection));
            float specVal = pow(spec, SPECULAR_EXPONENT);
            specular = diff * specVal * material.shininess;
        }

        //Sum
        totalLight += (((diff + specular) * source.color) + ambient) * att;
    }
    return totalLight;
}

vec3 getSpotLight(){
    vec3 totalLight = vec3(0);
    for(int i = 0; i < numSpotLights; i++){
        SpotLightSource source = spotLightSources[i];

        //Attenuation
        float distance = length(source.position - vPos3);
        float att = exp(-pow((distance * source.attenuation), source.gradient));
        if(att < 0.01) continue;

        //Ambient
        vec3 ambient = source.ambientColor;

        //Diffuse
        vec3 lightDir = normalize(source.position - vPos3);
        float diff = max(0, dot(vNormal, lightDir));

        //Specular
        float specular = 0;
        if(material.shininess > 0) {
            vec3 reflection = normalize(reflect(-lightDir, vNormal));
            float spec = max(0.0, dot(-vEyeDir, reflection));
            float specVal = pow(spec, SPECULAR_EXPONENT);
            specular = diff * specVal * material.shininess;
        }

        //Sum
        totalLight += (((diff + specular) * source.color) + ambient) * att;
    }
    return totalLight;
}