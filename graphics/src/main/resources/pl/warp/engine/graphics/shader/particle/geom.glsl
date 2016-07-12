#extension GL_EXT_geometry_shader4 : enable

precision mediump float;

uniform mat4 projectionMatrix;

layout (points) in;
layout (quads) out;

flat in float size;
flat in mat2 rotation;
flat in int vTextureIndex;

void main()
{
    vec2 pos = gl_in[0].gl_Position;

     // Vertex 4
    gl_TexCoord[0].stp = vec3(1.0, 1.0, vTextureIndex);
    gl_Position.xy = pos + (rotation * vec2(size, size));
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 3
    gl_TexCoord[0].stp = vec3(1.0, -1.0, vTextureIndex);
    gl_Position.xy = pos + (rotation * vec2(size, -size));
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 2
    gl_TexCoord[0].stp = vec3(-1.0, 1.0, vTextureIndex);
    gl_Position.xy = pos + (rotation * vec2(-size, size));
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 1
    gl_TexCoord[0].stp = vec3(-1.0, -1.0, vTextureIndex);
    gl_Position.xy = pos + (rotation * vec2(-size, -size));
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    EndPrimitive();
}