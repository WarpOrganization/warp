#version 410 core

#include "util/tesselation"

layout(triangles, equal_spacing, ccw) in;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform sampler2D displacementMap;
uniform bool displacementEnabled;
uniform float displacementFactor;

in vec3 tesWorldPos[];
in vec2 tesTexCoord[];
in vec3 tesNormal[];

out vec3 fsWorldPos;
out vec2 fsTexCoord;
out vec3 fsNormal;

void interpolatePatchData();
void applyDisplacement();

void main() {
    interpolatePatchData();
    if(displacementEnabled) {
        applyDisplacement();
    }
    gl_Position = projectionMatrix * viewMatrix * vec4(fsWorldPos, 1.0);
}

void interpolatePatchData() {
    fsTexCoord = tessInterpolate2D(tesTexCoord[0], tesTexCoord[1], tesTexCoord[2]);
    fsNormal = tessInterpolate3D(tesNormal[0], tesNormal[1], tesNormal[2]);
    fsNormal = normalize(fsNormal);
    fsWorldPos = tessInterpolate3D(tesWorldPos[0], tesWorldPos[1], tesWorldPos[2]);
}

void applyDisplacement() {
    float displacement = texture(displacementMap, fsTexCoord).r;
    fsWorldPos += fsNormal * displacement * displacementFactor;
}