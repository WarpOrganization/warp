#version 330
//edited version of https://www.shadertoy.com/view/XssGz8

in vec2 vTexCoord;

uniform float max_distort_px = 20.0;
uniform float resx, resy;
uniform sampler2D tex;
uniform int globalTime = 1;

float remap( float t, float a, float b ) {
	return clamp( (t - a) / (b - a), 0.0, 1.0 );
}
vec2 remap( vec2 t, vec2 a, vec2 b ) {
	return clamp( (t - a) / (b - a), 0.0, 1.0 );
}


vec3 spectrum_offset_rgb( float t ){
    float t0 = 3.0 * t - 1.5;
    return clamp( vec3( -t0, 1.0-abs(t0), t0), 0.0, 1.0);
}

const float gamma = 2.2;
vec3 lin2srgb( vec3 c ){
    return pow( c, vec3(gamma) );
}

vec3 srgb2lin( vec3 c ){
    return pow( c, vec3(1.0/gamma));
}


vec3 yCgCo2rgb(vec3 ycc)
{
    float R = ycc.x - ycc.y + ycc.z;
	float G = ycc.x + ycc.y;
	float B = ycc.x - ycc.y - ycc.z;
    return vec3(R,G,B);
}


vec2 radialdistort(vec2 coord, vec2 amt)
{
	vec2 cc = coord - 0.5;
	return coord + 2.0 * cc * amt;
}


//note: from https://www.shadertoy.com/view/MlSXR3
vec2 brownConradyDistortion(vec2 uv, float dist)
{
    uv = uv * 2.0 - 1.0;
    // positive values of K1 give barrel distortion, negative give pincushion
    float barrelDistortion1 = 0.1 * dist; // K1 in text books
    float barrelDistortion2 = -0.025 * dist; // K2 in text books

    float r2 = dot(uv,uv);
    uv *= 1.0 + barrelDistortion1 * r2 + barrelDistortion2 * r2 * r2;
    //uv *= 1.0 + barrelDistortion1 * r2;

    // tangential distortion (due to off center lens elements)
    // is not modeled in this function, but if it was, the terms would go here
    return uv * 0.5 + 0.5;
}

vec2 distort( vec2 uv, float t, vec2 min_distort, vec2 max_distort )
{
    vec2 dist = mix( min_distort, max_distort, t );
    return brownConradyDistortion( uv, 75.0 * dist.x );
}



vec3 spectrum_offset( float t )
{
  	return spectrum_offset_rgb( t );
}

float nrand( vec2 n ){
	return fract(sin(dot(n.xy, vec2(12.9898, 78.233)))* 43758.5453);
}


void main() {
    ivec2 res = ivec2(resx, resy);
	vec2 max_distort = vec2(max_distort_px) / res;
    vec2 min_distort = 0.5 * max_distort;

    //vec2 oversiz = vec2(1.0);
    vec2 oversiz = distort( vec2(1.0), 1.0, min_distort, max_distort );
    vec2 uv = remap( vTexCoord, 1.0-oversiz, oversiz );



	vec3 sumcol = vec3(0.0);
	vec3 sumw = vec3(0.0);
    float rnd = nrand( uv + fract(globalTime) );
    const int num_iter = 16;
	for ( int i=0; i<num_iter;++i )
	{
		float t = (float(i)+rnd) / float(num_iter-1);
		vec3 w = spectrum_offset( t );
		sumw += w;
        vec2 uvd = distort(uv, t, min_distort, max_distort );
		sumcol += w * srgb2lin(texture( tex, uvd ).rgb );
	}
    sumcol.rgb /= sumw;

    vec3 outcol = sumcol.rgb;
    outcol = lin2srgb( outcol );
    outcol += rnd/255.0;

	gl_FragColor = vec4( outcol, 1.0);
}
