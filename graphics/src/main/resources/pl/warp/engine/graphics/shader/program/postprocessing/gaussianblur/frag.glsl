#version 330
precision highp float;

uniform vec2 blurDirection;
uniform sampler2D tex;

flat in float pixelSize;
smooth in vec2 texCoord;

layout(location = 0) out vec4 fragColor;

const float[8] weights = float[](0.034619, 0.044859, 0.055857, 0.066833, 0.076841, 0.084894, 0.090126, 0.09194);

void main(void){
	fragColor = vec4(0.0);
    for(int i = 0; i < 15; i++) {
        vec2 coord = texCoord + blurDirection * pixelSize * (i - 7);
        coord.x = clamp(coord.x, 0.001, 0.999);
        coord.y = clamp(coord.y, 0.001, 0.999);
        fragColor += texture(tex, coord) * weights[abs(i - 7)];
    }
    fragColor.a = 1.0f;
}