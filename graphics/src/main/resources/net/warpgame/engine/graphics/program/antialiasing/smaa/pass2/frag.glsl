
uniform sampler2D albedo_tex;
in vec2 texcoord; 
in vec4 offset[3]; 

in vec4 dummy2;
void main() {
  #if SMAA_PREDICATION == 1
    gl_FragColor = SMAAColorEdgeDetectionPS(texcoord, offset, albedo_tex, depthTex); 
  #else
    gl_FragColor = SMAAColorEdgeDetectionPS(texcoord, offset, albedo_tex); 
  #endif
} 