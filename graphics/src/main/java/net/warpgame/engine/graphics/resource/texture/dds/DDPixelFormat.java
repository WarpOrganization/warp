package net.warpgame.engine.graphics.resource.texture.dds;

/**
 * <p>DDPixelFormat.java Bubble Engine</p>
 * <p><strong>Copyright (c) 2004 - 2005 Benjamin "Evil-Devil" Behrendt<br>
 * All rights reserved</strong><br>
 * Website: <a href="http://www.evil-devil.com" target="_blank">http://www.evil-devil.com</a></p><hr>
 * @author Benjamin "Evil-Devil" Behrendt
 * @version 1.0, 02.09.2005
 */
final class DDPixelFormat implements DDSurface {

    protected int size = 0;             // this must be set to 32!
    protected int flags = 0;
    protected int fourCC = 0;
    private String fourCCString = "";
    protected int rgbBitCount = 0;
    protected int rBitMask = 0;
    protected int gBitMask = 0;
    protected int bBitMask = 0;
    protected int rgbAlphaBitMask = 0;

    protected boolean isCompressed = true;

    public DDPixelFormat() {
    }

    public void setSize(int size) throws TextureFormatException {
        if (size != 32)
            throw new TextureFormatException("Wrong DDPixelFormat size. DDPixelFormat size must be 32!");
        this.size = size;
    }

    public void setFlags(int flags) {
        this.flags = flags;
        // check for (un)compressed
        if ((flags & DDPF_RGB) == DDPF_RGB)
            this.isCompressed = false;
        else if ((flags & DDPF_FOURCC) == DDPF_FOURCC)
            this.isCompressed = true;
    }

    public void setFourCC(int fourCC) {
        this.fourCC = fourCC;
        if (this.isCompressed)
            createFourCCString();       // create the fourCCString
    }

    private void createFourCCString() {
        byte[] fourCCString = new byte[DDPF_FOURCC];
        fourCCString[0] = (byte)this.fourCC;
        fourCCString[1] = (byte)(this.fourCC >> 8);
        fourCCString[2] = (byte)(this.fourCC >> 16);
        fourCCString[3] = (byte)(this.fourCC >> 24);
        // fourCCString is done :)
        this.fourCCString = new String(fourCCString);
    }

    public String getFourCCString() {
        return this.fourCCString;
    }

    public void setRGBBitCount(int rgbBitCount) {
        this.rgbAlphaBitMask = rgbBitCount;
    }

    public void setRBitMask(int rBitMask) {
        this.rBitMask = rBitMask;
    }

    public void setGBitMask(int gBitMask) {
        this.gBitMask = gBitMask;
    }

    public void setBBitMask(int bBitMask) {
        this.bBitMask = bBitMask;
    }

    public void setRGBAlphaBitMask(int rgbAlphaBitMask) {
        this.rgbAlphaBitMask = rgbAlphaBitMask;
    }
}