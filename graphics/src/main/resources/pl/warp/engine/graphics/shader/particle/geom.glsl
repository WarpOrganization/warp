#extension GL_EXT_geometry_shader4 : enable

precision mediump float;

layout (points) in;
layout (quads) out;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;

void main()
{
    vec2 pos = gl_in[0].gl_Position;

     // Vertex 4
    gl_TexCoord[0].st = vec2(1.0,1.0);
    gl_Position = pos + vec2(1.0, 1.0);
    gl_Position = projectionMatrix * modelMatrix * gl_Position;
    EmitVertex();

    // Vertex 3
    gl_TexCoord[0].st = vec2(1.0,-1.0);
    gl_Position = pos + vec2(1.0, -1.0);
    gl_Position = projectionMatrix * modelMatrix * gl_Position;
    EmitVertex();

    // Vertex 2
    gl_TexCoord[0].st = vec2(-1.0,1.0);
    gl_Position = pos + vec2(-1.0, 1.0);
    gl_Position = projectionMatrix * modelMatrix * gl_Position;
    EmitVertex();

    // Vertex 1
    gl_TexCoord[0].st = vec2(-1.0,-1.0);
    gl_Position = pos + vec2(-1.0, -1.0);
    gl_Position = projectionMatrix * modelMatrix * gl_Position;
    EmitVertex();

    EndPrimitive();
}