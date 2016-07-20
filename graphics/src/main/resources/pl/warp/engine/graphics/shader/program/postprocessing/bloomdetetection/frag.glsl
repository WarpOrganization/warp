#version 150
precision highp float;

uniform sampler2D tex;
in vec2 vTexCoord;

layout(location = 0) out vec4 fragColor;

in vec2 blurTextureCoords[11];

void main(void){
	fragColor = vec4(0.0);
	fragColor += texture(tex, blurTextureCoords[0]) * 0.0093;
    fragColor += texture(tex, blurTextureCoords[1]) * 0.028002;
    fragColor += texture(tex, blurTextureCoords[2]) * 0.065984;
    fragColor += texture(tex, blurTextureCoords[3]) * 0.121703;
    fragColor += texture(tex, blurTextureCoords[4]) * 0.175713;
    fragColor += texture(tex, blurTextureCoords[5]) * 0.198596;
    fragColor += texture(tex, blurTextureCoords[6]) * 0.175713;
    fragColor += texture(tex, blurTextureCoords[7]) * 0.121703;
    fragColor += texture(tex, blurTextureCoords[8]) * 0.065984;
    fragColor += texture(tex, blurTextureCoords[9]) * 0.028002;
    fragColor += texture(tex, blurTextureCoords[10]) * 0.0093;
}