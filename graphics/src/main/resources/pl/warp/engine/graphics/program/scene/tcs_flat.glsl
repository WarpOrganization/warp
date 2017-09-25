#version 410 core

layout (vertices = 3) out;

uniform vec3 cameraPos;

//tesselation level = a - b * log(distance)
uniform float a = 28.3177;
uniform float b = 6.27222;

in vec3 tcsWorldPos[];
in vec2 tcsTexCoord[];
in vec3 tcsNormal[];

out vec3 tesWorldPos[];
out vec2 tesTexCoord[];
out vec3 tesNormal[];

void passPatchData();
void calcTesselationLevels();

void main() {
    passPatchData();
    calcTesselationLevels();
}

void passPatchData() {
    tesWorldPos[gl_InvocationID] = tcsWorldPos[gl_InvocationID];
    tesTexCoord[gl_InvocationID] = tcsTexCoord[gl_InvocationID];
    tesNormal[gl_InvocationID] = tcsNormal[gl_InvocationID];
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