#version 330
#extension GL_EXT_texture_array : enable

precision mediump float;

uniform sampler2DArray textures;

flat in int textureIndex;
flat in float visibility;
in vec3 flareColor;
smooth in vec2 texCoord;

layout(location = 0) out vec4 fragColor;

void main(void) {
    fragColor = texture2DArray(textures, vec3(texCoord, textureIndex)) * visibility;
    fragColor.rgb *= flareColor;
    if(fragColor.r < 0.0f || fragColor.g < 0.0f || fragColor.b < 0.0f) discard;
    fragColor.a = 1.0;
}