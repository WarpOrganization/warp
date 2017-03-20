#version 330

in vec2 vTexCoord;

uniform sampler2D tex;
uniform int globalTime = 1;

void main() {

    float time = globalTime * 0.001;

    vec3 color = texture(tex, vec2(vTexCoord.x,vTexCoord.y)).rgb;

    color = clamp(color*0.5+0.5*color*color*1.2,0.0,1.0);

    color *= 0.5 + 0.5*16.0*vTexCoord.x*vTexCoord.y*(1.0-vTexCoord.x)*(1.0-vTexCoord.y);
    color *= vec3(0.95,1.05,0.95);
    color *= 0.9+0.1*sin(10.0*time+vTexCoord.y*1000.0);
    color *= 0.99+0.01*sin(110.0*time);

    gl_FragColor = vec4(color,1.0);
}