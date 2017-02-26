#version 330
precision highp float;

uniform sampler2DMS sceneDepth;
uniform sampler2D componentDepth;
uniform sampler2D component;

in vec2 vTexCoord;

const float bias = 0.000001;

layout(location = 0) out vec4 fragColor;

void main(void) {
    ivec2 size = textureSize(sceneDepth);
    ivec2 iTexCoord = ivec2(floor(size * vTexCoord));
    if(texelFetch(sceneDepth, iTexCoord, 0).r + bias > texture(componentDepth, vTexCoord).r) {
       fragColor.rgb = texture(component, vTexCoord).rgb;
       fragColor.a = 1.0;
    }
 }