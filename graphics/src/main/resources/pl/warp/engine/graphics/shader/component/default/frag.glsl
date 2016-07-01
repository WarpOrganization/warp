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

struct DiretionalLightSource {
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
uniform bool lightEnabled = true;
uniform float shininess = 1;


//Basic rendering stuff
uniform vec3 cameraPos;

//From vertex shader
in vec3 vNormal;
in vec3 vPos3;
in vec3 vTexCoord;
in vec3 vEyeDir;

out vec4 fragColor;


void main(void) {
    //Light and texturing
    if(lightEnabled) {
        vec3 sumLight = getSpotLight() + getDirectionaLight();
        fragColor = vec4(sumLight, 1) * texture(texture, vTexCoord);
    } else fragColor = texture(material.mainTexture, vTexCoord);

    //Fog
    float dist = length(cameraPos.xz - vPos3.xz);
    fragColor.a *= clamp(exp(-pow((dist * fog.density), fog.gradient)), 0.0, 1.0);

    //Brightness
    fragColor.rgb *= material.brightness;
}

vec3 getDirectionalLight() {
    return vec3(0.0, 0.0, 0.0); //TODO
}

vec3 getSpotLight(){
    vec3 totalLight = vec3(0);
    for(int i = 0; i < numLights; i++){
        //Attenuation
        float distance = length(lights[i].position - vPos3);
        float att = exp(-pow((distance * lights[i].attenuation), lights[i].gradient));
        if(att < 0.01) continue;

        //Ambient
        vec3 ambient = lights[i].ambientColor;

        //Diffuse
        vec3 lightDir = normalize(lights[i].position - vPos3);
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
        totalLight += (((diff + specular) * lights[i].color) + ambient) * att;
    }
    return totalLight;
}