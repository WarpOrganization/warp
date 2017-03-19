#version 330
precision highp float;

uniform sampler2D source;
uniform int amount = 32;
uniform vec2 start_pos;

in vec2 vTexCoord;

layout(location = 0) out vec4 fragColor;

void main(void) {
    vec2 position = -1.0 + 2.0 * vTexCoord.xy;
    vec2 current_step = position;

    vec2 direction = (start_pos - position) / amount;
    vec3 total = vec3( 0.0 );
    for(int i = 0; i < amount; i++)
    {
        vec3 result = texture(source, current_step * 0.5);
        result = smoothstep(0.0, 1.0, result);
        total += result;
        current_step += direction;
    }

    total /= amount;
	gl_FragColor = vec4(total, 1.0);
}