#version 330

#extension GL_EXT_geometry_shader4 : enable

precision mediump float;

uniform mat4 projectionMatrix;

uniform float size;

layout (points) in;
layout (triangle_strip) out;
layout (max_vertices = 4) out;

smooth out vec2 coord;


void main()
{
    vec4 pos = gl_in[0].gl_Position;

     // Vertex 4
    coord = vec2(1.0, 1.0);
    gl_Position = pos;
    gl_Position.xy += (vec2(1, 1) * size);
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 3
    coord = vec2(-1.0, 1.0);
    gl_Position = pos;
    gl_Position.xy += vec2(-1, 1) * size;
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 2
    coord = vec2(1.0, -1.0);
    gl_Position = pos;
    gl_Position.xy += vec2(1, -1) * size;
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 1
    coord = vec2(-1.0, -1.0);
    gl_Position = pos;
    gl_Position.xy += vec2(-1, -1) * size;
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    EndPrimitive();
}