bool isSet(int flags, int index) {
    return ((flags >> index) & int(0x01)) == int(1);
}

int flag(bool flag, int index){
    return (flag ? 1 : 0) << index;
}