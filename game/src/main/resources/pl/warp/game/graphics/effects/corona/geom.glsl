#version 330

#extension GL_EXT_geometry_shader4 : enable

precision mediump float;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 rotationMatrix;
uniform mat4 cameraRotationMatrix;

uniform float size;

layout (points) in;
layout (triangle_strip) out;
layout (max_vertices = 4) out;

smooth out vec3 spherePos;

void main()
{
    vec4 pos = gl_in[0].gl_Position;

    mat4 inverseRotation = rotationMatrix;

     // Vertex 4
    vec4 spherePos4 = vec4(1.0, 1.0, 0.0, 1.0);
    spherePos = spherePos4.xyz / spherePos4.w;
    gl_Position = pos;
    gl_Position.xy += (vec2(1, 1) * size);
    gl_Position = projectionMatrix * rotationMatrix * gl_Position;
    EmitVertex();

    // Vertex 3
    spherePos4 = vec4(-1.0, 1.0, 0.0, 1.0);
    spherePos = spherePos4.xyz / spherePos4.w;
    gl_Position = pos;
    gl_Position.xy += vec2(-1, 1) * size;
    gl_Position = projectionMatrix * rotationMatrix * gl_Position;
    EmitVertex();

    // Vertex 2
    spherePos4 = vec4(1.0, -1.0, 0.0, 1.0);
    spherePos = spherePos4.xyz / spherePos4.w;
    gl_Position = pos;
    gl_Position.xy += vec2(1, -1) * size;
    gl_Position = projectionMatrix * rotationMatrix  * gl_Position;
    EmitVertex();

    // Vertex 1
    spherePos4 = vec4(-1.0, -1.0, 0.0, 1.0);
    spherePos = spherePos4.xyz / spherePos4.w;
    gl_Position = pos;
    gl_Position.xy += vec2(-1, -1) * size;
    gl_Position = projectionMatrix * rotationMatrix * gl_Position;
    EmitVertex();

    EndPrimitive();
}