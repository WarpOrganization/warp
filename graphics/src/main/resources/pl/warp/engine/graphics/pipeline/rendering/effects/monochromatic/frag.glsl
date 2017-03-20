#version 330

in vec2 vTexCoord;

uniform sampler2D tex;
uniform vec3 color = vec3(1);

void main(){
    vec3 col = texture(tex, vTexCoord).rgb;
    float f = (col.r + col.g + col.b) / 3; //looks better without greyscale
    gl_FragColor = vec4(vec3(f) * color, 1);
}