#version 330

precision mediump float;

uniform sampler2DArray textures;

flat in int textureIndex;
flat in float visibility;
in vec3 flareColor;

layout(location = 0) out vec4 fragColor;

void main(void) {
    fragColor = texture2DArray(textures, vec3(gl_TexCoord[0].st, textureIndex)) * visibility;
    fragColor.rgb *= flareColor;
    fragColor.a = 1.0;
}