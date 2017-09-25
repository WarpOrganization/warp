
struct Patch10 {
    vec3 worldPos_B030;
    vec3 worldPos_B021;
    vec3 worldPos_B012;
    vec3 worldPos_B003;
    vec3 worldPos_B102;
    vec3 worldPos_B201;
    vec3 worldPos_B300;
    vec3 worldPos_B210;
    vec3 worldPos_B120;
    vec3 worldPos_B111;
    vec2 texCoord[3];
    vec3 normal[3];
};

struct Patch {
    vec3 fsWorldPos;
    vec2 fsTexCoord;
    vec3 fsNormal;
};

#ifdef TES
vec2 tessInterpolate2D(vec2 v0, vec2 v1, vec2 v2){
    return vec2(gl_TessCoord.x) * v0 + vec2(gl_TessCoord.y) * v1 + vec2(gl_TessCoord.z) * v2;
}

vec3 tessInterpolate3D(vec3 v0, vec3 v1, vec3 v2){
    return vec3(gl_TessCoord.x) * v0 + vec3(gl_TessCoord.y) * v1 + vec3(gl_TessCoord.z) * v2;
}
#endif