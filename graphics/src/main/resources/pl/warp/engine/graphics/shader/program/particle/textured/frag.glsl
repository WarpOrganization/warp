#version 330
#extension GL_EXT_texture_array : enable

precision mediump float;

uniform sampler2DArray textures;

in float textureIndex;
smooth in vec2 texCoord;

layout(location = 0) out vec4 fragColor;

void main(void) {
    fragColor = texture2DArray(textures, vec3(texCoord, textureIndex));
}