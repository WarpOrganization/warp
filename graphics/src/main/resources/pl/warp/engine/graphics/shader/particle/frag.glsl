#version 330

precision mediump float;

uniform sampler2DArray textures;

out vec4 fragColor;

void main(void) {
    fragColor = texture2DArray(textures, gl_TexCoord[0].stp);
}