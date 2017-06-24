#version 330
#extension GL_EXT_geometry_shader : enable

layout (triangles) in;
layout (triangle_strip) out;
layout (max_vertices = 4) out;

flat in vec3 sphereRelativePos[3];
flat in int instanceID[3];

smooth out vec3 spherePos;

uniform mat3 matrices[6];

void main() {
    int instance = instanceID[0];
    gl_Layer = instance;
    for (int i = 0; i < 3; i++) {
        gl_Position = gl_PositionIn[i];
        spherePos = matrices[instance] * sphereRelativePos[i];
        EmitVertex();
    }
    EndPrimitive();
}