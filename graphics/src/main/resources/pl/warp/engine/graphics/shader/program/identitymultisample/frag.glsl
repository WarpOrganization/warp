#version 330
precision highp float;

uniform sampler2DMS texture;
uniform int samples;

in vec2 vTexCoord;

layout(location = 0) out vec4 fragColor;

void main(void) {
    ivec2 size = textureSize(texture);
    ivec2 iTexCoord = ivec2(floor(size * vTexCoord));
    vec4 color = vec4(0,0,0,1);
    for(int i = 0; i < samples; i++){
        fragColor.rgb += texelFetch(texture, iTexCoord, i).rgb;
    }
    fragColor.rgb /= samples;
    fragColor.a = 1;
}