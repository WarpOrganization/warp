#version 330
precision highp float;

uniform sampler2D sceneTex;
uniform sampler2D bloomTex;
uniform float bloomLevel;
uniform float exposure;

in vec2 vTexCoord;


layout(location = 0) out vec4 fragColor;

void main(void) {
    vec4 bloom = texture(bloomTex, vTexCoord) * bloomLevel;
    vec4 scene = texture(sceneTex, vTexCoord);
    vec3 color = scene.rgb + bloom.rgb;
    fragColor.rgb = 1 - exp2(-color * exposure);
    fragColor.a = 1.0;
}