#version 330

#extension GL_EXT_geometry_shader4 : enable

precision mediump float;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;
uniform mat4 cameraRotationMatrix;

layout (points) in;
layout (triangle_strip) out;
layout (max_vertices = 4) out;

out float textureIndex;
smooth out vec2 texCoord;

in mat2 vRotation[];
in float vTextureIndex[];

mat2 toZRotation(mat4 rotation3D);

void main()
{
    vec4 pos = gl_in[0].gl_Position;
    mat2 particleRotation = vRotation[0];
    textureIndex = vTextureIndex[0];

     // Vertex 4
    texCoord = vec2(1.0, 1.0);
    gl_Position = modelViewMatrix * pos;
    gl_Position.xy += (particleRotation * vec2(1, 1));
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 3
    texCoord = vec2(0.0, 1.0);
    gl_Position = modelViewMatrix * pos;
    gl_Position.xy += (particleRotation * vec2(-1, 1));
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 2
    texCoord = vec2(1.0, 0.0);
    gl_Position = modelViewMatrix * pos;
    gl_Position.xy += (particleRotation * vec2(1, -1));
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 1
    texCoord = vec2(0.0, 0.0);
    gl_Position = modelViewMatrix * pos;
    gl_Position.xy += (particleRotation * vec2(-1, -1));
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    EndPrimitive();
}