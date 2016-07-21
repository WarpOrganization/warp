#version 330
precision highp float;

uniform sampler2D tex;
smooth in vec2 blurCoords[15];

layout(location = 0) out vec4 fragColor;

in vec2 blurTextureCoords[11];

void main(void){ //0.034619	0.044859	0.055857	0.066833	0.076841	0.084894	0.090126	0.09194	0.090126	0.084894	0.076841	0.066833	0.055857	0.044859	0.034619
	fragColor = vec4(0.0);
	fragColor += texture(tex, blurCoords[0]) * 0.034619;
    fragColor += texture(tex, blurCoords[1]) * 0.044859;
    fragColor += texture(tex, blurCoords[2]) * 0.055857;
    fragColor += texture(tex, blurCoords[3]) * 0.066833;
    fragColor += texture(tex, blurCoords[4]) * 0.076841;
    fragColor += texture(tex, blurCoords[5]) * 0.084894;
    fragColor += texture(tex, blurCoords[6]) * 0.09194;
    fragColor += texture(tex, blurCoords[7]) * 0.09194;
    fragColor += texture(tex, blurCoords[8]) * 0.084894;
    fragColor += texture(tex, blurCoords[9]) * 0.076841;
    fragColor += texture(tex, blurCoords[10]) * 0.066833;
    fragColor += texture(tex, blurCoords[11]) * 0.055857;
    fragColor += texture(tex, blurCoords[12]) * 0.175713;
    fragColor += texture(tex, blurCoords[13]) * 0.044859;
    fragColor += texture(tex, blurCoords[14]) * 0.034619;;
}