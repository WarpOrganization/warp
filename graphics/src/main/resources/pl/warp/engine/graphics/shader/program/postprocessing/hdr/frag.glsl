#version 330
precision highp float;

uniform sampler2D samplers[$MAX_SAMPLERS$];
uniform float weights[$MAX_SAMPLERS$];
uniform float scales[$MAX_SAMPLERS$];
uniform int samplersNumber;
uniform float exposure;

in vec2 vTexCoord;


layout(location = 0) out vec4 fragColor;

void main(void) {
    vec4 color = vec4(0);
    for(int i = 0; i < samplersNumber; i++) {
        color += texture(samplers[i], vTexCoord * scales[i]) * weights[i];
    }
    fragColor.rgb = 1 - exp2(-color.rgb * exposure);
    fragColor.a = 1.0;
}