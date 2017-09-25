#version 410 core

#include "util/tesselation"
#include "util/math"

layout (vertices = 1) out;

uniform vec3 cameraPos;

//tesselation level = a - b * log(distance)
uniform float a = 28.3177;
uniform float b = 6.27222;

in vec3 tcsWorldPos[];
in vec2 tcsTexCoord[];
in vec3 tcsNormal[];

out patch Patch10 tesPatch;

void calcPatchData();
void calcTesselationLevels();

void main() {
    calcPatchData();
    calcTesselationLevels();
}

void calcPatchData() {
    for (int i = 0; i < 3 ; i++) {
       tesPatch.normal[i] = tcsNormal[i];
       tesPatch.texCoord[i] = tcsTexCoord[i];
    }

    tesPatch.worldPos_B030 = tcsWorldPos[0];
    tesPatch.worldPos_B003 = tcsWorldPos[1];
    tesPatch.worldPos_B300 = tcsWorldPos[2];

    // edges are names according to the opposing vertex
    vec3 edgeB300 = tesPatch.worldPos_B003 - tesPatch.worldPos_B030;
    vec3 edgeB030 = tesPatch.worldPos_B300 - tesPatch.worldPos_B003;
    vec3 edgeB003 = tesPatch.worldPos_B030 - tesPatch.worldPos_B300;

    // Generate two midpoints on each edge
    tesPatch.worldPos_B021 = tesPatch.worldPos_B030 + edgeB300 / 3.0;
    tesPatch.worldPos_B012 = tesPatch.worldPos_B030 + edgeB300 * 2.0 / 3.0;
    tesPatch.worldPos_B102 = tesPatch.worldPos_B003 + edgeB030 / 3.0;
    tesPatch.worldPos_B201 = tesPatch.worldPos_B003 + edgeB030 * 2.0 / 3.0;
    tesPatch.worldPos_B210 = tesPatch.worldPos_B300 + edgeB003 / 3.0;
    tesPatch.worldPos_B120 = tesPatch.worldPos_B300 + edgeB003 * 2.0 / 3.0;

    // Project each midpoint on the plane defined by the nearest vertex and its normal
    tesPatch.worldPos_B021 = projectToPlane(tesPatch.worldPos_B021, tesPatch.worldPos_B030,
                                          tesPatch.normal[0]);
    tesPatch.worldPos_B012 = projectToPlane(tesPatch.worldPos_B012, tesPatch.worldPos_B003,
                                         tesPatch.normal[1]);
    tesPatch.worldPos_B102 = projectToPlane(tesPatch.worldPos_B102, tesPatch.worldPos_B003,
                                         tesPatch.normal[1]);
    tesPatch.worldPos_B201 = projectToPlane(tesPatch.worldPos_B201, tesPatch.worldPos_B300,
                                         tesPatch.normal[2]);
    tesPatch.worldPos_B210 = projectToPlane(tesPatch.worldPos_B210, tesPatch.worldPos_B300,
                                         tesPatch.normal[2]);
    tesPatch.worldPos_B120 = projectToPlane(tesPatch.worldPos_B120, tesPatch.worldPos_B030,
                                         tesPatch.normal[0]);

    // Handle the center
    vec3 center = (tesPatch.worldPos_B003 + tesPatch.worldPos_B030 + tesPatch.worldPos_B300) / 3.0;
    tesPatch.worldPos_B111 = (tesPatch.worldPos_B021 + tesPatch.worldPos_B012 + tesPatch.worldPos_B102 +
                          tesPatch.worldPos_B201 + tesPatch.worldPos_B210 + tesPatch.worldPos_B120) / 6.0;
    tesPatch.worldPos_B111 += (tesPatch.worldPos_B111 - center) / 2.0;

}

float getTesselationLevel(float dist0, float dist1) {
    float avgDistance = (dist0 + dist1) / 2.0;
    return a - b * log(avgDistance);
}

void calcTesselationLevels() {
    float eyeToVertex0 = distance(cameraPos, tcsWorldPos[0]);
    float eyeToVertex1 = distance(cameraPos, tcsWorldPos[1]);
    float eyeToVertex2 = distance(cameraPos, tcsWorldPos[2]);

    gl_TessLevelOuter[0] = getTesselationLevel(eyeToVertex1, eyeToVertex2);
    gl_TessLevelOuter[1] = getTesselationLevel(eyeToVertex2, eyeToVertex0);
    gl_TessLevelOuter[2] = getTesselationLevel(eyeToVertex0, eyeToVertex1);
    gl_TessLevelInner[0] = gl_TessLevelOuter[2];
}