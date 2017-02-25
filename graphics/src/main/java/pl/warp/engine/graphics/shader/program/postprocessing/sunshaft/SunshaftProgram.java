package pl.warp.engine.graphics.shader.program.postprocessing.sunshaft;

import org.joml.Vector2f;
import pl.warp.engine.graphics.postprocessing.sunshaft.SunshaftSource;
import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-02-24 at 23
 */
public class SunshaftProgram extends Program {
    private static String PROGRAM_NAME = "postprocessing/sunshaft";

    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;

    private int DIFFUSE_SAMPLER = 0;

    private int unifCenter;
    private int unifExposure;
    private int unifDecay;
    private int unifDensity;
    private int unifWeight;
    private int unifClamp;

    public SunshaftProgram() {
        super(PROGRAM_NAME);
        loadUniforms();
    }

    private void loadUniforms() {
        this.unifCenter = getUniformLocation("center");
        this.unifExposure = getUniformLocation("exposure");
        this.unifDecay = getUniformLocation("decay");
        this.unifDensity = getUniformLocation("density");
        this.unifWeight = getUniformLocation("weight");
        this.unifClamp = getUniformLocation("clampValue");
    }

    public void useDiffuseTexture(Texture2D diffuseTexture){
        useTexture(diffuseTexture, DIFFUSE_SAMPLER);
    }

    public void useSunshaftSource(SunshaftSource source) {
        setUniformf(unifExposure, source.getExposure());
        setUniformf(unifDecay, source.getDecay());
        setUniformf(unifDensity, source.getDensity());
        setUniformf(unifWeight, source.getWeight());
        setUniformf(unifClamp, source.getClamp());
    }

    public void useCenter(Vector2f center){
        setUniformV2(unifCenter, center);
    }

}
