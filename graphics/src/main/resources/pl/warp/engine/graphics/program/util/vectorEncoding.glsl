vec2 octWrap(vec2 v) {
    return ( 1.0 - abs(v.yx) ) * vec2(sign(v.x), sign(v.y));
}

vec2 encode(vec3 n) {
    n /= (abs(n.x) + abs(n.y) + abs(n.z));
    n.xy = n.z >= 0.0 ? n.xy : octWrap(n.xy);
    n.xy = n.xy * 0.5 + 0.5;
    return n.xy;
}

vec3 decode(vec2 encN ) {
    encN = encN * 2.0 - 1.0;
    vec3 n;
    n.z = 1.0 - abs(encN.x) - abs(encN.y);
    n.xy = n.z >= 0.0 ? encN.xy : octWrap(encN.xy);
    n = normalize(n);
    return n;
}