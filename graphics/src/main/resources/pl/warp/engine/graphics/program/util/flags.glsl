bool isSet(uint flags, int index) {
    return ((flags >> index) & 0x01) == 1;
}

int flag(bool flag, int index){
    return (flag ? 1 : 0) << index;
}