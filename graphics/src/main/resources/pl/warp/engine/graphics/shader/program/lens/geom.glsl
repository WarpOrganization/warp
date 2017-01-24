#version 330

#extension GL_EXT_geometry_shader4 : enable

precision mediump float;

uniform vec2 screenSize;

layout (points) in;
layout (triangle_strip) out;
layout (max_vertices = 4) out;

flat out int textureIndex;
flat out float visibility;
out vec3 flareColor;
smooth out vec2 texCoord;

in float vScale[];
in int vTextureIndex[];
in float vVisibility[];
in vec3 vFlareColor[];

void main()
{
    vec4 pos = gl_in[0].gl_Position;
    float scale = vScale[0];
    textureIndex = vTextureIndex[0];
    visibility = vVisibility[0];
    flareColor = vFlareColor[0];
    vec2 screenRatio = normalize(screenSize);

     // Vertex 4
    texCoord = vec2(1.0, 1.0);
    gl_Position = pos;
    gl_Position.xy += (scale * vec2(1, 1)) / screenRatio;
    EmitVertex();

    // Vertex 3
    texCoord = vec2(0.0, 1.0);
    gl_Position = pos;
    gl_Position.xy += (scale * vec2(-1, 1)) / screenRatio;
    EmitVertex();

    // Vertex 2
    texCoord = vec2(1.0, 0.0);
    gl_Position = pos;
    gl_Position.xy += (scale * vec2(1, -1)) / screenRatio;
    EmitVertex();

    // Vertex 1
    texCoord = vec2(0.0, 0.0);
    gl_Position = pos;
    gl_Position.xy += (scale * vec2(-1, -1)) / screenRatio;
    EmitVertex();

    EndPrimitive();
}