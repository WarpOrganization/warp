#version 330
precision highp float;

uniform sampler2D sceneDepth;
uniform sampler2D componentDepth;

in vec2 vTexCoord;


layout(location = 0) out vec4 fragColor;

void main(void) {
    if(texture(sceneDepth, vTexCoord).r < texture(componentDepth, vTexCoord).r) {
       fragColor.a = 1.0;
       fragColor.rgb = vec3(0);
    } else discard;
}