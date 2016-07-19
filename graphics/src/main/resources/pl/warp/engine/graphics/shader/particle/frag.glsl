#version 330

precision mediump float;

uniform sampler2DArray textures;

in float textureIndex;

out vec4 fragColor;

void main(void) {
    fragColor = texture2DArray(textures, vec3(gl_TexCoord[0].st, textureIndex));
}