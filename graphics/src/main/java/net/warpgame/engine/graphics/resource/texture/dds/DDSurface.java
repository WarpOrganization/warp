package net.warpgame.engine.graphics.resource.texture.dds;

/**
 * <p>DDSurface.java Bubble Engine</p>
 * <p><strong>Copyright (c) 2004 - 2005 Benjamin "Evil-Devil" Behrendt<br>
 * All rights reserved</strong><br>
 * Website: <a href="http://www.evil-devil.com" target="_blank">http://www.evil-devil.com</a></p><hr>
 * @author Benjamin "Evil-Devil" Behrendt
 * @version 1.0, 02.09.2005
 */
interface DDSurface {

    // dwFags - DDSurfaceDesc2
    int DDSD_CAPS           = 0x00000001;
    int DDSD_HEIGHT         = 0x00000002;
    int DDSD_WIDTH          = 0x00000004;
    int DDSD_PITCH          = 0x00000008;
    int DDSD_PIXELFORMAT    = 0x00001000;
    int DDSD_MIPMAPCOUNT    = 0x00020000;
    int DDSD_LINEARSIZE      = 0x00080000;
    int DDSD_DEPTH          = 0x00800000;

    // ddpfPixeFormat - DDSurfaceDesc2
    int DDPF_APHAPIXES  = 0x00000001;
    int DDPF_FOURCC     = 0x00000004;
    int DDPF_RGB        = 0x00000040;

    // dwCaps1  - DDSCaps2
    int DDSCAPS_COMPLEX    = 0x00000008;
    int DDSCAPS_TEXTURE    = 0x00001000;
    int DDSCAPS_MIPMAP     = 0x00400000;

    // dwCaps2 - DDSCaps2
    int DDSCAPS2_CUBEMAP            = 0x00000200;
    int DDSCAPS2_CUBEMAP_POSITVEX   = 0x00000400;
    int DDSCAPS2_CUBEMAP_NEGATIVEX  = 0x00000800;
    int DDSCAPS2_CUBEMAP_POSITIVEY  = 0x00001000;
    int DDSCAPS2_CUBEMAP_NEGATIVEY  = 0x00002000;
    int DDSCAPS2_CUBEMAP_POSITIVEZ  = 0x00004000;
    int DDSCAPS2_CUBEMAP_NEGATIVEZ  = 0x00008000;
    int DDSCAPS2_VOLUME              = 0x00200000;

}