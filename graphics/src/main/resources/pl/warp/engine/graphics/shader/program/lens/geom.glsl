#version 330

#extension GL_EXT_geometry_shader4 : enable

precision mediump float;

uniform vec2 screenSize;

layout (points) in;
layout (triangle_strip) out;
layout (max_vertices = 4) out;

flat out int textureIndex;
flat out float visibility;

in vData {
    float scale;
    int textureIndex;
    float visibility;
} pointData[];

void main()
{
    vec4 pos = gl_in[0].gl_Position;
    float scale = pointData[0].scale;
    textureIndex = pointData[0].textureIndex;
    visibility = pointData[0].visibility;
    vec2 screenRatio = normalize(screenSize);

     // Vertex 4
    gl_TexCoord[0].st = vec2(1.0, 1.0);
    gl_Position = pos;
    gl_Position.xy += (scale * vec2(1, 1)) / screenRatio;
    EmitVertex();

    // Vertex 3
    gl_TexCoord[0].st = vec2(0.0, 1.0);
    gl_Position = pos;
    gl_Position.xy += (scale * vec2(-1, 1)) / screenRatio;
    EmitVertex();

    // Vertex 2
    gl_TexCoord[0].st = vec2(1.0, 0.0);
    gl_Position = pos;
    gl_Position.xy += (scale * vec2(1, -1)) / screenRatio;
    EmitVertex();

    // Vertex 1
    gl_TexCoord[0].st = vec2(0.0, 0.0);
    gl_Position = pos;
    gl_Position.xy += (scale * vec2(-1, -1)) / screenRatio;
    EmitVertex();

    EndPrimitive();
}