package net.warpgame.engine.graphics.resource.texture.dds;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

import static org.lwjgl.opengl.EXTTextureCompressionS3TC.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * <p>DDSLoader.java Bubble Engine</p>
 * <p><strong>Copyright (c) 2004 - 2005 Benjamin "Evil-Devil" Behrendt<br>
 * All rights reserved</strong><br>
 * Website: <a href="http://www.evil-devil.com" target="_blank">http://www.evil-devil.com</a></p><hr>
 *
 * @author Benjamin "Evil-Devil" Behrendt
 * @version 1.0, 02.09.2005
 * <p>This class provides an example view about how to load a texture that uses<br>
 * the DDS (Direct Draw Surface) file format.<br>
 * This is not a common engine Bubble Engine class nor should it be used as it, there are
 * other classes for loading dds and other texture format files :D
 */
public class DDSLoader implements DDSurface {

    private final String DDS_IDENTIFIER = "DDS ";
    private final int DDS_HEADER_SIZE = 128;        // size of the dds header
    private final int DDS_DESC2_RESERVED_1 = 44;    // bytesize of DWORD[11]
    private final int DDS_DESC2_RESERVED_2 = 4;     // bytesize of DWORD
    private final int DDS_CAPS2_RESERVED = 8;       // bytesize of DWORD[2]
    private final int DEFAULT_DXT_BLOCKSIZE = 16;
    private final int DXT1_BLOCKSIZE = 8;

    private DDSurfaceDesc2 ddsDesc2 = null;
    private ByteBuffer ddsHeader = null;
    private FileChannel ddsFileChannel = null;

    public DDSLoader() {
    }

    public int loadDDSFile(String fileName) {
        // our DDS file
        File ddsFile = new File(fileName);

        try {
            FileInputStream fis = new FileInputStream(ddsFile);
            // assign the filechannel for reading data from it
            ddsFileChannel = fis.getChannel();
            // check for null
            if (ddsFileChannel == null)
                throw new NullPointerException("ddsFileChannel couldn't be null!");
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        // create a new DDSurfaceDesc2 object to hold all dds file information
        ddsDesc2 = new DDSurfaceDesc2();
        // allocate enough memory for storing the whole header
        ddsHeader = BufferUtils.createByteBuffer(DDS_HEADER_SIZE);
        readFileHeader();
        int glName = readFileData();
        return glName;
    }

    private void readFileHeader() {

        try {
            // read the header
            ddsFileChannel.read(ddsHeader);
            ddsHeader.rewind();

            // read and feed the DDSurfaceDesc2            
            ddsDesc2.setIdentifier(ddsHeader.getInt());
            ddsDesc2.setSize(ddsHeader.getInt());
            ddsDesc2.setFlags(ddsHeader.getInt());
            ddsDesc2.setHeight(ddsHeader.getInt());
            ddsDesc2.setWidth(ddsHeader.getInt());
            ddsDesc2.setPitchOrLinearSize(ddsHeader.getInt());
            ddsDesc2.setDepth(ddsHeader.getInt());
            ddsDesc2.setMipMapCount(ddsHeader.getInt());
            // skip, cause next is unused
            ddsHeader.position(ddsHeader.position() + DDS_DESC2_RESERVED_1);

            // DDPixelFormat of DDSurfaceDesc2            
            DDPixelFormat pixelFormat = ddsDesc2.getDDPixelformat();
            pixelFormat.setSize(ddsHeader.getInt());
            pixelFormat.setFlags(ddsHeader.getInt());
            pixelFormat.setFourCC(ddsHeader.getInt());
            pixelFormat.setRGBBitCount(ddsHeader.getInt());
            pixelFormat.setRBitMask(ddsHeader.getInt());
            pixelFormat.setGBitMask(ddsHeader.getInt());
            pixelFormat.setBBitMask(ddsHeader.getInt());
            pixelFormat.setRGBAlphaBitMask(ddsHeader.getInt());

            // DDSCaps2 of DDSurfaceDesc2
            DDSCaps2 caps2 = ddsDesc2.getDDSCaps2();
            caps2.setCaps1(ddsHeader.getInt());
            caps2.setCaps2(ddsHeader.getInt());

            // skip, cause next is unused
            ddsHeader.position(ddsHeader.position() + DDS_CAPS2_RESERVED);

            // we don't wanna read the last 4 bytes, they are not used anyway,
            // but we skip them. Funny, ain't?
            ddsHeader.position(ddsHeader.position() + DDS_DESC2_RESERVED_2);
            // the last two instuctions might be banned, but thats your decission

        } catch (BufferUnderflowException bue) {
            bue.printStackTrace();
        } catch (TextureFormatException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            ddsHeader = null;   // free the memory
        }
    }

    private int readFileData() {
        final DDPixelFormat ddpf = ddsDesc2.getDDPixelformat();
        int imageSize = 0;
        int dxtFormat = 0;
         
        /* calculate the image size depending on the used blocksize
           and set the used DXT format */
        if (ddpf.isCompressed && ddpf.getFourCCString().equalsIgnoreCase("DXT1")) {
            imageSize = calculateSize(DXT1_BLOCKSIZE);
            // at the moment we treat any DXT1 image as RGBA,
            // maybe this can be switched dynamically in future...
            dxtFormat = GL_COMPRESSED_RGBA_S3TC_DXT1_EXT;
        } else {
            imageSize = calculateSize(DEFAULT_DXT_BLOCKSIZE);
            if (ddpf.getFourCCString().equalsIgnoreCase("DXT3"))
                dxtFormat = GL_COMPRESSED_RGBA_S3TC_DXT3_EXT;
            else if (ddpf.getFourCCString().equals("DXT5"))
                dxtFormat = GL_COMPRESSED_RGBA_S3TC_DXT5_EXT;
        }

        // read the dds file data itself
        ByteBuffer imageData = BufferUtils.createByteBuffer(ddsDesc2.pitchOrLinearSize);

        try {
            ddsFileChannel.read(imageData);
            imageData.rewind();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // create the GL Name
        IntBuffer glName = BufferUtils.createIntBuffer(1);

        // create the texture
        GL11.glGenTextures(glName);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, glName.get(0));
         
        /* Implement the filtering stuff anywhere you want, this is only here to
           have at least one filter applied on the texture */
        // Linear Filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL13.glCompressedTexImage2D(GL11.GL_TEXTURE_2D, 0, dxtFormat, ddsDesc2.width, ddsDesc2.height, 0, imageData);

        return glName.get(0);
    }

    private int calculateSize(int blockSize) {
        double size = Math.ceil(ddsDesc2.width / 4) * Math.ceil(ddsDesc2.height / 4) * blockSize;
        return (int) size;
    }

    public void debugInfo() {
        DDPixelFormat pixelFormat = ddsDesc2.getDDPixelformat();
        DDSCaps2 caps2 = ddsDesc2.getDDSCaps2();

        System.out.println("\nDDSURFACEDESC2:");
        System.out.println("----------------------------------------");
        System.out.println("SIZE: " + ddsDesc2.size);
        System.out.println("FLAGS: " + ddsDesc2.flags);
        System.out.println("HEIGHT: " + ddsDesc2.height);
        System.out.println("WIDTH: " + ddsDesc2.width);
        System.out.println("PITCH_OR_LINEAR_SIZE: " + ddsDesc2.pitchOrLinearSize);
        System.out.println("DEPTH: " + ddsDesc2.depth);
        System.out.println("MIP_MAP_COUNT: " + ddsDesc2.mipMapCount);

        System.out.println("\nDDPIXELFORMAT of DDSURFACEDESC2:");
        System.out.println("----------------------------------------");
        System.out.println("SIZE :" + pixelFormat.size);
        System.out.println("FLAGS: " + pixelFormat.flags);
        System.out.println("FOUR_CC: " + pixelFormat.getFourCCString());
        System.out.println("RGB_BIT_COUNT: " + pixelFormat.rgbBitCount);
        System.out.println("R_BIT_MASK: " + pixelFormat.rBitMask);
        System.out.println("G_BIT_MASK: " + pixelFormat.gBitMask);
        System.out.println("B_BIT_MASK: " + pixelFormat.bBitMask);
        System.out.println("RGB_ALPHA_BIT_MASK: " + pixelFormat.rgbAlphaBitMask);

        System.out.println("\nDDSCAPS of DDSURFACEDESC2");
        System.out.println("----------------------------------------");
        System.out.println("CAPS1: " + caps2.caps1);
        System.out.println("CAPS2: " + caps2.caps2);
    }
}