#version 330
precision highp float;

uniform sampler2DArray textures;

out vec4 fragColor;

void main(void) {
    //fragColor = texture2DArray(gl_TexCoord[0].stp, texCoord);
    fragColor = vec4(1);
}