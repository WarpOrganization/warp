#version 450
precision highp float;

vec3 getLight();


struct SpotLightSource {
    vec3 position;
    vec3 color;
    vec3 ambientColor;
    float attenuation;
    float gradient;
    float specularFactor;
};

struct DirectionalLightSource {
    vec3 position;
    vec3 direction;
    float directionGradient;
    vec3 color;
    vec3 ambientColor;
    float attenuation;
    float gradient;
    float specularFactor;
};

struct Material {
    sampler2D mainTexture;
    float brightness;
};

uniform Material material;

const int MAX_SPOT_LIGHTS = 10;
uniform SpotLightSource spotLightSources[MAX_SPOT_LIGHTS];
uniform int numSpotLights;
const int MAX_DIRECTIONAL_LIGHTS = 25;
uniform DirectionalLightSource directionalLightSources[MAX_DIRECTIONAL_LIGHTS];
uniform int numDirectionalLights;
uniform bool lightEnabled = true;
uniform float shininess = 1;


//Basic rendering stuff
uniform vec3 cameraPos;

//From vertex shader
in vec3 vNormal;
in vec3 vPos3;
in vec2 vTexCoord;
in vec3 vEyeDir;

out vec4 fragColor;

vec3 getSpotLight();
vec3 getDirectionalLight();

void main(void) {
    //Light and texturing
    if(lightEnabled) {
        vec3 sumLight = getSpotLight() + getDirectionalLight();
        fragColor = vec4(sumLight, 1) * texture(material.mainTexture, vTexCoord);
    } else fragColor = texture(material.mainTexture, vTexCoord);

    //Brightness
    fragColor.rgb *= material.brightness;
}

vec3 getDirectionalLight() {
    return vec3(0.0, 0.0, 0.0); //TODO
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
        if(shininess > 0) {
            vec3 reflection = normalize(reflect(-lightDir, vNormal));
            float spec = max(0.0, dot(-vEyeDir, reflection));
            float specVal = pow(spec, shininess);
            specular = diff * specVal;
        }

        //Sum
        totalLight += (((diff + specular) * source.color) + ambient) * att;
    }
    return totalLight;
}