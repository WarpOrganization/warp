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

in patch Patch10 tesPatch;

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
    oTexCoord = tessInterpolate2D(tesPatch.texCoord[0], tesPatch.texCoord[1], tesPatch.texCoord[2]);
    oNormal = tessInterpolate3D(tesPatch.normal[0], tesPatch.normal[1], tesPatch.normal[2]);
    oNormal = normalize(oNormal);

    float u = gl_TessCoord.x;
    float v = gl_TessCoord.y;
    float w = gl_TessCoord.z;

    float uPow3 = pow(u, 3);
    float vPow3 = pow(v, 3);
    float wPow3 = pow(w, 3);
    float uPow2 = pow(u, 2);
    float vPow2 = pow(v, 2);
    float wPow2 = pow(w, 2);

    oWorldPos = tesPatch.worldPos_B300 * wPow3 +
                 tesPatch.worldPos_B030 * uPow3 +
                 tesPatch.worldPos_B003 * vPow3 +
                 tesPatch.worldPos_B210 * 3.0 * wPow2 * u +
                 tesPatch.worldPos_B120 * 3.0 * w * uPow2 +
                 tesPatch.worldPos_B201 * 3.0 * wPow2 * v +
                 tesPatch.worldPos_B021 * 3.0 * uPow2 * v +
                 tesPatch.worldPos_B102 * 3.0 * w * vPow2 +
                 tesPatch.worldPos_B012 * 3.0 * u * vPow2 +
                 tesPatch.worldPos_B111 * 6.0 * w * u * v;
}

void applyDisplacement() {
    float displacement = texture(displacementMap, oTexCoord).r;
    oWorldPos += oNormal * displacement * displacementFactor;
}