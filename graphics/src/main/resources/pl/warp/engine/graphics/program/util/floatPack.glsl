int nBitMask(int n) {
    return ((0x01 << n) - 1);
}

int packSignedNorm(float f, int bits) {
    int sign = (f < 0) ? 0 : 1;
    int i = int(round(clamp(f, -1.0, 1.0) * (0x01 << (bits - 2)))) & nBitMask(bits - 1);
    return ((sign == 0) ? nBitMask(bits - 1) ^ i : i) | (sign << (bits - 1));
}

float unpackSignedNorm(int i, int bits) {
    float f = clamp((i & nBitMask(bits - 1)) / ((0x01 << (bits - 2)) * 1.0), -1.0, 1.0);
    int sign = (i >> (bits - 1)) & 0x01;
    return f * (sign * 2 - 1);
}


int v2PackSignedNorm(vec2 v, int bits) {
    return (packSignedNorm(v.x, bits) << bits) | packSignedNorm(v.y, bits);
}

vec2 v2UnpackSignedNorm(int i, int bits) {
    float y = unpackSignedNorm(i, bits);
    float x = unpackSignedNorm(i >> bits, bits);
    return vec2(x,y);
}