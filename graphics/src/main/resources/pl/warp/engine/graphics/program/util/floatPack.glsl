uint nBitMask(int n) {
    return ((0x01 << uint(n)) - 1);
}

uint nBitMask(uint n) {
    return ((0x01 << uint(n)) - 1);
}

uint packUnsignedNorm(float f, int bits) {
    uint ubits = uint(bits);
    return uint(round(clamp(f, 0.0, 1.0) * (0x01 << (ubits - 1)))) & nBitMask(ubits);
}


float unpackUnsignedNorm(uint i, int bits) {
    uint ubits = uint(bits);
    return clamp((i & nBitMask(ubits)) / ((0x01 << (ubits - 1)) * 1.0), 0.0, 1.0);
}

uint packSignedNorm(float f, int bits) {
    uint ubits = uint(bits);
    uint sign = (f < 0) ? 0 : 1;
    uint i = uint(round(clamp(f, -1.0, 1.0) * (0x01 << (ubits - 2)))) & nBitMask(ubits - 1);
    return ((sign == 0) ? nBitMask(ubits - 1) ^ i : i) | (sign << (ubits - 1));
}

float unpackSignedNorm(uint i, int bits) {
    uint ubits = uint(bits);
    float f = clamp((i & nBitMask(ubits - 1)) / ((0x01 << (ubits - 2)) * 1.0), -1.0, 1.0);
    uint sign = (i >> (ubits - 1)) & 0x01;
    return f * (sign * 2 - 1);
}

uint v2PackUnsignedNorm(vec2 v, int bits) {
    return (packUnsignedNorm(v.x, bits) << uint(bits)) | packUnsignedNorm(v.y, bits);
}

uint v2PackSignedNorm(vec2 v, int bits) {
    return (packSignedNorm(v.x, bits) << uint(bits)) | packSignedNorm(v.y, bits);
}

vec2 v2UnpackUnsignedNorm(uint i, int bits) {
    float y = unpackUnsignedNorm(i, bits);
    float x = unpackUnsignedNorm(i >> uint(bits), bits);
    return vec2(x,y);
}

vec2 v2UnpackSignedNorm(uint i, int bits) {
    float y = unpackSignedNorm(i, bits);
    float x = unpackSignedNorm(i >> uint(bits), bits);
    return vec2(x,y);
}