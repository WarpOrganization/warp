#version 330

#include "util/noise3d"

smooth in vec3 spherePos;

void main() {
	float n = snoise(spherePos * 2);
	if(n > 0.2)
	    gl_FragColor = vec4(0, 1, 0, 1);
	else gl_FragColor = vec4(0, 0, 1, 0);
}
