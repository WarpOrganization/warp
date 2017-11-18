uint nBitMask(int n) {
    return ((0x01 << n) - 1);
}

uint packUnsignedNorm(float f, int bits) {
    return uint(round(clamp(f, 0.0, 1.0) * (0x01 << (bits - 1)))) & nBitMask(bits);
}


float unpackUnsignedNorm(uint i, int bits) {
    return clamp((i & nBitMask(bits)) / ((0x01 << (bits - 1)) * 1.0), 0.0, 1.0);
}

uint packSignedNorm(float f, int bits) {
    uint sign = (f < 0) ? 0 : 1;
    uint i = uint(round(clamp(f, -1.0, 1.0) * (0x01 << (bits - 2)))) & nBitMask(bits - 1);
    return ((sign == 0) ? nBitMask(bits - 1) ^ i : i) | (sign << (bits - 1));
}

float unpackSignedNorm(uint i, int bits) {
    float f = clamp((i & nBitMask(bits - 1)) / ((0x01 << (bits - 2)) * 1.0), -1.0, 1.0);
    uint sign = (i >> (bits - 1)) & 0x01;
    return f * (sign * 2 - 1);
}

uint v2PackUnsignedNorm(vec2 v, int bits) {
    return (packUnsignedNorm(v.x, bits) << bits) | packUnsignedNorm(v.y, bits);
}

uint v2PackSignedNorm(vec2 v, int bits) {
    return (packSignedNorm(v.x, bits) << bits) | packSignedNorm(v.y, bits);
}

vec2 v2UnpackUnsignedNorm(uint i, int bits) {
    float y = unpackUnsignedNorm(i, bits);
    float x = unpackUnsignedNorm(i >> bits, bits);
    return vec2(x,y);
}

vec2 v2UnpackSignedNorm(uint i, int bits) {
    float y = unpackSignedNorm(i, bits);
    float x = unpackSignedNorm(i >> bits, bits);
    return vec2(x,y);
}