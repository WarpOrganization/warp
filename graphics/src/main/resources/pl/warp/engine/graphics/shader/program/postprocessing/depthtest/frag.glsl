#version 330
precision highp float;

uniform sampler2DMS sceneDepth;
uniform sampler2D componentDepth;

in vec2 vTexCoord;


layout(location = 0) out vec4 fragColor;

void main(void) {
    ivec2 size = textureSize(sceneDepth);
    ivec2 iTexCoord = ivec2(floor(size * vTexCoord));
    if(texelFetch(sceneDepth, iTexCoord, 0).r < texture(componentDepth, vTexCoord).r) {
       fragColor.rgb = vec3(0);
       fragColor.a = 1.0;
    } else discard;
}