#version 330

#extension GL_EXT_geometry_shader4 : enable

precision mediump float;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;
uniform mat4 cameraRotationMatrix;

layout (points) in;
layout (triangle_strip) out;
layout (max_vertices = 4) out;

in vData {
    mat2 rotation;
    float textureIndex;
} pointData[];

mat2 toZRotation(mat4 rotation3D);

void main()
{
    vec4 pos = gl_in[0].gl_Position;
    mat2 particleRotation = pointData[0].rotation;
    mat2 cameraZRotation = toZRotation(cameraRotationMatrix);
    mat2 rotation = cameraZRotation * particleRotation;
    float textureIndex = pointData[0].textureIndex;

     // Vertex 4
    gl_TexCoord[0].stp = vec3(1.0, 1.0, textureIndex);
    gl_Position = modelViewMatrix * pos;
    gl_Position.xy += (particleRotation * vec2(1, 1));
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 3
    gl_TexCoord[0].stp = vec3(0.0, 1.0, textureIndex);
    gl_Position = modelViewMatrix * pos;
    gl_Position.xy += (particleRotation * vec2(-1, 1));
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 2
    gl_TexCoord[0].stp = vec3(1.0, 0.0, textureIndex);
    gl_Position = modelViewMatrix * pos;
    gl_Position.xy += (particleRotation * vec2(1, -1));
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    // Vertex 1
    gl_TexCoord[0].stp = vec3(0.0, 0.0, textureIndex);
    gl_Position = modelViewMatrix * pos;
    gl_Position.xy += (particleRotation * vec2(-1, -1));
    gl_Position = projectionMatrix * gl_Position;
    EmitVertex();

    EndPrimitive();
}

mat2 toZRotation(mat4 rotation4D) {
    mat2 rotation;
    rotation[0].xy = rotation4D[0].xy;
    rotation[1].xy = rotation4D[1].xy;
    float xRot = asin(rotation4D[2].y);
    rotation[1].y /= cos(xRot);
    return rotation;
}