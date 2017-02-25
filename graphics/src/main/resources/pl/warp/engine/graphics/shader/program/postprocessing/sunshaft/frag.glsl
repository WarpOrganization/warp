#version 330
precision highp float;

varying vec2 vTexCoord;
uniform sampler2D diffuse;

uniform float centerX; //TODO pass as an attribute
uniform float centerY; //TODO pass as an attribute
uniform float exposure;
uniform float decay;
uniform float density;
uniform float weight;
uniform float clamp;

const int iSamples = 20;

void main()
{
    vec2 deltaTextCoord = vec2(vTexCoord - vec2(centerX,centerY));
    deltaTextCoord *= 1.0 /  float(iSamples) * density;
    vec2 coord = vTexCoord;
    float illuminationDecay = 1.0;
    vec4 color = vec4(0.0);

    for(int i=0; i < iSamples ; i++)
    {
        coord -= deltaTextCoord;
        vec4 texel = texture(diffuse, coord);
        texel *= illuminationDecay * weight;

        color += texel;

        illuminationDecay *= decay;
    }
    color *= exposure;
    color = clamp(fragColor, 0.0, clamp);
    gl_FragColor = color;
}