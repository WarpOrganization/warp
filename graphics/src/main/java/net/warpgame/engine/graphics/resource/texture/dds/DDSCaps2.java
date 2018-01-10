package net.warpgame.engine.graphics.resource.texture.dds;

/**
 * <p>DDSCaps2.java Bubble Engine</p>
 * <p><strong>Copyright (c) 2004 - 2005 Benjamin "Evil-Devil" Behrendt<br>
 * All rights reserved</strong><br>
 * Website: <a href="http://www.evil-devil.com" target="_blank">http://www.evil-devil.com</a></p><hr>
 * @author Benjamin "Evil-Devil" Behrendt
 * @version 1.0, 02.09.2005
 */
final class DDSCaps2 implements DDSurface {

    public DDSCaps2() {
    }

    protected int caps1 = 0;
    protected int caps2 = 0;
    protected int reserved = 0;       // unused by documentation

    protected boolean isVolumeTexture = false;

    public void setCaps1(int caps1) throws TextureFormatException {
        this.caps1 = caps1;
        if ((caps1 & DDSCAPS_TEXTURE) != DDSCAPS_TEXTURE)   // check for DDSCAPS_TEXTURE
            throw new TextureFormatException("DDS file does not contain DDSCAPS_TEXTURE, but it must!");
    }

    public void setCaps2(int caps2) {
        this.caps2 = caps2;
        // check for VolumeTexture
        if ((caps2 & DDSCAPS2_VOLUME) == DDSCAPS2_VOLUME)
            this.isVolumeTexture = true;
    }
}