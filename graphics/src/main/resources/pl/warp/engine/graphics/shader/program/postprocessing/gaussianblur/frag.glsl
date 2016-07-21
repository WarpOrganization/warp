#version 150
precision highp float;

uniform sampler2D tex;
in vec2 vTexCoord;
in vec2 blurCoords[11];

layout(location = 0) out vec4 fragColor;

in vec2 blurTextureCoords[11];

void main(void){
	fragColor = vec4(0.0);
	fragColor += texture(tex, blurCoords[0]) * 0.0093;
    fragColor += texture(tex, blurCoords[1]) * 0.028002;
    fragColor += texture(tex, blurCoords[2]) * 0.065984;
    fragColor += texture(tex, blurCoords[3]) * 0.121703;
    fragColor += texture(tex, blurCoords[4]) * 0.175713;
    fragColor += texture(tex, blurCoords[5]) * 0.198596;
    fragColor += texture(tex, blurCoords[6]) * 0.175713;
    fragColor += texture(tex, blurCoords[7]) * 0.121703;
    fragColor += texture(tex, blurCoords[8]) * 0.065984;
    fragColor += texture(tex, blurCoords[9]) * 0.028002;
    fragColor += texture(tex, blurCoords[10]) * 0.0093;
}