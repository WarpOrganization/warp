bool isSet(uint flags, int index) {
    return ((flags >> index) & uint(0x01)) == uint(1);
}

int flag(bool flag, int index){
    return (flag ? 1 : 0) << index;
}