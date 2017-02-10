
vec3 getLight(vec3 normal, vec3 pos, vec3 eyeDir, float shininess, float specularExponent);
float getDirectionCoeff(SpotLightSource source, vec3 pos);
float getAttenuation(SpotLightSource source, vec3 pos);
float getDiffuse(SpotLightSource source, vec3 lightDir, float directionCoeff, vec3 normal);
float getSpecular(SpotLightSource source, vec3 lightDir, float diffuse, vec3 normal, vec3 eyeDir, float shininess, float specularExponent);
bool isNan(vec3 vec);

vec3 getLight(vec3 normal, vec3 pos, vec3 eyeDir, float shininess, float specularExponent) {
    vec3 totalLight = vec3(0);
    for(int i = 0; i < numSpotLights; i++){
        SpotLightSource source = spotLightSources[i];
        float directionCoeff = getDirectionCoeff(source, pos);
        float attenuation = getAttenuation(source, pos);
        if(attenuation < 0.01) continue;
        vec3 ambient = source.ambientColor;
        vec3 lightDir = normalize(source.position - pos);
        float diffuse = getDiffuse(source, lightDir, directionCoeff, normal);
        float specular = getSpecular(source, lightDir, diffuse, normal, eyeDir, shininess, specularExponent);
        totalLight += (((diffuse + specular) * source.color) + ambient) * attenuation;
    }
    return totalLight;
}

float getDirectionCoeff(SpotLightSource source, vec3 pos) {
    if(source.coneAngle >= 180) return 1.0f; // non-directional

    vec3 direction = normalize(source.position - pos);
    float angle = acos(dot(direction, source.coneDirection));
    if(angle < source.coneAngle)
        return 1.0f;
    else if(angle < source.coneAngle + source.coneGradient)
        return 1.0f - (angle - source.coneAngle) / source.coneGradient;
    else return 0.0f;
}

float getAttenuation(SpotLightSource source, vec3 pos) {
    float distance = length(source.position - pos);
    return exp(-pow((distance * source.attenuation), source.gradient));
}

float getDiffuse(SpotLightSource source, vec3 lightDir, float directionCoeff, vec3 normal) {
     return max(0, dot(normal, lightDir)) * directionCoeff;
}

float getSpecular(SpotLightSource source, vec3 lightDir, float diffuse, vec3 normal, vec3 eyeDir, float shininess, float specularExponent) {
    vec3 reflection = normalize(reflect(-lightDir, normal));
    float spec = max(0.0, dot(-eyeDir, reflection));
    float specVal = pow(spec, specularExponent / shininess);
    return diffuse * specVal * shininess / 10;
}


bool isNan(vec3 vec) {
    return (!(fragColor.r < 1000000.0)) || (!(fragColor.g < 1000000.0)) || (!(fragColor.b < 1000000.0));
}
