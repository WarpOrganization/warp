#version 330
precision highp float;

uniform sampler2D diffuse;

uniform vec2 center;
uniform float exposure;
uniform float decay;
uniform float density;
uniform float weight;
uniform float clampValue;

in vec2 vTexCoord;

const int iSamples = 96;

void main(){
    vec2 deltaTextCoord = vec2(vTexCoord - (center + 1) / 2);
    deltaTextCoord *= 1.0 / float(iSamples) * density;
    vec2 coord = vTexCoord;
    float illuminationDecay = 1.0;
    vec4 color = vec4(0.0);
    for(int i=0; i < iSamples ; i++){
        coord -= deltaTextCoord;
        vec4 texel = texture(diffuse, coord);
        texel *= illuminationDecay * weight;
        color += texel;
        illuminationDecay *= decay;
    }
    color *= exposure;
    color = clamp(color, 0, clampValue);
    gl_FragColor = color;
}