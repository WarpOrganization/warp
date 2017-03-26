#version 330

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 rotationMatrix;
uniform mat4 cameraMatrix;
uniform vec3 cameraPos;

smooth out vec3 spherePos;

uniform float radius;

layout(location = 0) in vec3 inVertex;
layout(location = 0) in vec2 inTexCoord;
layout(location = 0) in vec3 inNormal;

smooth out vec3 eyeDir;
smooth out vec3 surfacePos;
smooth out float fragmentRadius;
smooth out float planetRadius;
smooth out vec3 normal;

#include "util/vec3d"

float getPlanetRadius();
float getFragmentRadius(vec3 vertPos);

void main(void) {
    vec4 vertPos = modelMatrix * vec4(radius * inVertex, 1.0f);
    surfacePos = (modelMatrix * vec4(inVertex, 1)).xyz;
    normal = normalize(inVertex);
    eyeDir = normalize(vertPos.xyz - cameraPos);
    vec3 planetVertPos = surfacePos - (modelMatrix * vec4(0, 0, 0, 1)).xyz;
    planetRadius = length(planetVertPos);
    fragmentRadius = getFragmentRadius(vertPos.xyz);
    gl_Position = projectionMatrix * cameraMatrix * vertPos;
}

float getPlanetRadius(){
    vec4 surfacePos = modelMatrix * vec4(1, 0, 0, 1);
    return length(surfacePos.xyz);
}

float getFragmentRadius(vec3 vertPos){
    vec3 planetPos = (modelMatrix * vec4(0,0,0,1)).xyz;
    return lineToPointDistance(planetPos, cameraPos, vertPos);
}
