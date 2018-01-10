package net.warpgame.engine.graphics.resource.texture.dds;

/**
 * <p>DDSurfaceDesc2.java Bubble Engine</p>
 * <p><strong>Copyright (c) 2004 - 2005 Benjamin "Evil-Devil" Behrendt<br>
 * All rights reserved</strong><br>
 * Website: <a href="http://www.evil-devil.com" target="_bank">http://www.evil-devil.com</a></p><hr>
 * @author Benjamin "Evil-Devil" Behrendt
 * @version 1.0, 02.09.2005
 */
final class DDSurfaceDesc2 implements DDSurface {

    private final String DDS_IDENTIFIER = "DDS ";
    protected int identifier = 0;
    private String identifierString = "";
    protected int size = 0;                     // size of the structure, must be 124
    protected int flags = 0;
    protected int height = 0;
    protected int width = 0;
    protected int pitchOrLinearSize = 0;
    protected int depth = 0;
    protected int mipMapCount = 0;
    protected int reserved = 0;                 // unused by documentation
    private DDPixelFormat pixelFormat = null;   // the surfaces DDPixelFormat
    private DDSCaps2 caps2 = null;              // the surfaces DDSCaps2
    protected int reserved2 = 0;                // unused by documentation

    public DDSurfaceDesc2() {
        pixelFormat = new DDPixelFormat();
        caps2 = new DDSCaps2();
    }

    public void setIdentifier(int identifier) throws TextureFormatException {
        this.identifier = identifier;
        createIdentifierString();
    }

    private void createIdentifierString() throws TextureFormatException {
        byte[] identifierString = new byte[4];
        identifierString[0] = (byte)this.identifier;
        identifierString[1] = (byte)(this.identifier >> 8);
        identifierString[2] = (byte)(this.identifier >> 16);
        identifierString[3] = (byte)(this.identifier >> 24);

        this.identifierString = new String(identifierString);
        // validate
        if (!this.identifierString.equalsIgnoreCase(this.DDS_IDENTIFIER)) {
            throw new TextureFormatException("The DDS Identifier is wrong. Have to be \"DDS \"!");
        }
    }

    public void setSize(int size) throws TextureFormatException{
        if (size != 124)    // TODO: include your own Exception handling here!
            throw new TextureFormatException("Wrong DDSurfaceDesc2 size. DDSurfaceDesc2 size must be 124!");
        this.size = size;
    }

    public void setFlags(int flags) throws TextureFormatException {
        this.flags = flags;
        // check for DDSD_CAPS, DDSD_PIXELFORMAT, DDSD_WIDTH, DDSD_HEIGHT
        if ((flags & DDSD_CAPS) != DDSD_CAPS || (flags & DDSD_PIXELFORMAT) != DDSD_PIXELFORMAT
                || (flags & DDSD_WIDTH) != DDSD_WIDTH || (flags & DDSD_HEIGHT) != DDSD_HEIGHT)
            throw new TextureFormatException("One or more required flag bits are set wrong\n"+
                    "flags have to include \"DDSD_CAPS, DDSD_PIXELFORMAT, DDSD_WIDTH, DDSD_HEIGHT\"");
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setPitchOrLinearSize(int pitchOrLinearSize) {
        this.pitchOrLinearSize = pitchOrLinearSize;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setMipMapCount(int mipMapCount) {
        this.mipMapCount = mipMapCount;
    }

    public void setDDPixelFormat(DDPixelFormat pixelFormat) throws NullPointerException {
        if (pixelFormat == null)
            throw new NullPointerException("DDPixelFormat can't be null. DDSurfaceDesc2 needs a valid DDPixelFormat.");
        this.pixelFormat = pixelFormat;
    }

    public DDPixelFormat getDDPixelformat() {
        return pixelFormat;
    }

    public void setDDSCaps2(DDSCaps2 caps2) throws NullPointerException {
        if (caps2 == null)
            throw new NullPointerException("DDSCaps can't be null. DDSurfaceDesc2 needs a valid DDSCaps2.");
        this.caps2 = caps2;
    }

    public DDSCaps2 getDDSCaps2() {
        return caps2;
    }
}