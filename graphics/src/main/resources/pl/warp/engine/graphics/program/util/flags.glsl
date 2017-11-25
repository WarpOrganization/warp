bool isSet(uint flags, int index) {
    return ((flags >> index) & 0x01) == 1;
}