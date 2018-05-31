        #version 410 core

#include "util/tesselation"

layout(triangles, equal_spacing, ccw) in;

#ifdef SCENE_TESS
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
#endif

uniform sampler2D displacementMap;
uniform bool displacementEnabled;
uniform float displacementFactor;

in vec3 tesWorldPos[];
in vec2 tesTexCoord[];
in vec3 tesNormal[];

out vec3 oWorldPos;
out vec2 oTexCoord;
out vec3 oNormal;

void interpolatePatchData();
void applyDisplacement();

void main() {
    interpolatePatchData();
    if(displacementEnabled) {
        applyDisplacement();
    }
    #ifdef SCENE_TESS
    gl_Position = projectionMatrix * viewMatrix * vec4(oWorldPos, 1.0);
    #else
    gl_Position = vec4(oWorldPos, 1.0);
    #endif
}

void interpolatePatchData() {
    oTexCoord = tessInterpolate2D(tesTexCoord[0], tesTexCoord[1], tesTexCoord[2]);
    oNormal = tessInterpolate3D(tesNormal[0], tesNormal[1], tesNormal[2]);
    oNormal = normalize(oNormal);
    oWorldPos = tessInterpolate3D(tesWorldPos[0], tesWorldPos[1], tesWorldPos[2]);
}

void applyDisplacement() {
    float displacement = texture(displacementMap, oTexCoord).r;
    oWorldPos += oNormal * displacement * displacementFactor;
}