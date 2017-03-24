#version 330

smooth in vec3 spherePos;

void main() {
	gl_FragColor = vec4(spherePos, 1);
}
