#version 330

#extension GL_EXT_geometry_shader4 : enable

precision mediump float;

layout (points) in;
layout (triangle_strip) out;
layout (max_vertices = 4) out;

out int textureIndex;
out float visiblity;

in vData {
    float scale;
    int textureIndex;
    float visiblity;
} pointData[];

void main()
{
    vec4 pos = gl_in[0].gl_Position;
    float scale = pointData[0].scale;
    textureIndex = pointData[0].textureIndex;
    visiblity = pointData[0].visiblity;

     // Vertex 4
    gl_TexCoord[0].st = vec2(1.0, 1.0);
    gl_Position = pos;
    gl_Position.xy += (scale * vec2(1, 1));
    EmitVertex();

    // Vertex 3
    gl_TexCoord[0].st = vec2(0.0, 1.0);
    gl_Position = pos;
    gl_Position.xy += (scale * vec2(-1, 1));
    EmitVertex();

    // Vertex 2
    gl_TexCoord[0].st = vec2(1.0, 0.0);
    gl_Position = pos;
    gl_Position.xy += (scale * vec2(1, -1));
    EmitVertex();

    // Vertex 1
    gl_TexCoord[0].st = vec2(0.0, 0.0);
    gl_Position = pos;
    gl_Position.xy += (scale * vec2(-1, -1));
    EmitVertex();

    EndPrimitive();
}