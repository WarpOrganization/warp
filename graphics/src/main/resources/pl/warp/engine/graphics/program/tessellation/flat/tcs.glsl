#version 410 core

layout (vertices = 3) out;

uniform vec3 cameraPos;

#ifdef SCENE_TESS
//tesselation level = a - b * log(distance)
uniform float a = 28.3177;
uniform float b = 6.27222;
#else
uniform float tessLevel;
#endif

in vec3 oWorldPos[];
in vec2 oTexCoord[];
in vec3 oNormal[];

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
    tesWorldPos[gl_InvocationID] = oWorldPos[gl_InvocationID];
    tesTexCoord[gl_InvocationID] = oTexCoord[gl_InvocationID];
    tesNormal[gl_InvocationID] = oNormal[gl_InvocationID];
}

#ifdef SCENE_TESS
float getTesselationLevel(float dist0, float dist1) {
    return 3;
}

void calcTesselationLevels() {
    float eyeToVertex0 = distance(cameraPos, oWorldPos[0]);
    float eyeToVertex1 = distance(cameraPos, oWorldPos[1]);
    float eyeToVertex2 = distance(cameraPos, oWorldPos[2]);

    gl_TessLevelOuter[0] = getTesselationLevel(eyeToVertex1, eyeToVertex2);
    gl_TessLevelOuter[1] = getTesselationLevel(eyeToVertex2, eyeToVertex0);
    gl_TessLevelOuter[2] = getTesselationLevel(eyeToVertex0, eyeToVertex1);
    gl_TessLevelInner[0] = gl_TessLevelOuter[2];
}
#else
void calcTesselationLevels() {
    gl_TessLevelOuter[0] = tessLevel;
    gl_TessLevelOuter[1] = tessLevel;
    gl_TessLevelOuter[2] = tessLevel;
    gl_TessLevelInner[0] = tessLevel;
}
#endif