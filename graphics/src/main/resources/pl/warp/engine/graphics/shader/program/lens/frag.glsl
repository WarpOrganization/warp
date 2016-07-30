#version 330

precision mediump float;

uniform sampler2DArray textures;
uniform vec3 sourceColor;

in flat int textureIndex;
in float visiblity;

layout(location = 0) out vec4 fragColor;

void main(void) {
    fragColor = texture2DArray(textures, vec3(gl_TexCoord[0].st, textureIndex)) * visiblity;
    fragColor.rgb *= sourceColor;
    fragColor.a = 1.0;
    fragColor = vec4(1);
}