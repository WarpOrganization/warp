#version 330

precision mediump float;

uniform sampler2DArray textures;
uniform vec3 sourceColor;

flat in int textureIndex;
flat in float visibility;

layout(location = 0) out vec4 fragColor;

void main(void) {
    fragColor = texture2DArray(textures, vec3(gl_TexCoord[0].st, textureIndex)) * visibility;
    fragColor.rgb *= sourceColor;
    fragColor.a = 1.0;
    fragColor = vec4(1);
}