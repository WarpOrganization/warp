#version 330

#extension GL_EXT_geometry_shader4 : enable

precision mediump float;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;
uniform mat4 cameraRotationMatrix;

layout (points) in;
layout (triangle_strip) out;
layout (max_vertices = 4) out;

smooth out vec2 coord;
flat out vec4 color;
out float gradient;

flat in vData {
    vec4 color;
    float scale;
    float gradient;
} pointData[];

void main()
{
    vec4 pos = gl_in[0].gl_Position;
    gradient = pointData[0].gradient;
    float scale = pointData[0].scale;

     // Vertex 4
    coord = vec2(1.0, 1.0);
    color = pointData[0].color;
    gl_Position = modelViewMatrix * pos;
    gl_Position.xy += (vec2(1, 1) * scale);
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 3
    coord = vec2(-1.0, 1.0);
    color = pointData[0].color;
    gl_Position = modelViewMatrix * pos;
    gl_Position.xy += vec2(-1, 1) * scale;
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 2
    coord = vec2(1.0, -1.0);
    color = pointData[0].color;
    gl_Position = modelViewMatrix * pos;
    gl_Position.xy += vec2(1, -1) * scale;
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 1
    coord = vec2(-1.0, -1.0);
    color = pointData[0].color;
    gl_Position = modelViewMatrix * pos;
    gl_Position.xy += vec2(-1, -1) * scale;
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    EndPrimitive();
}