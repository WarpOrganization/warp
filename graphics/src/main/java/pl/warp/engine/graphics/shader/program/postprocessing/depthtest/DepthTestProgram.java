package pl.warp.engine.graphics.shader.program.postprocessing.depthtest;

import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-02-24 at 22
 */
public class DepthTestProgram extends Program {
    private static String PROGRAM_NAME = "postprocessing/depthtest";
    private static final int SCENE_DEPTH_SAMPLER = 0;
    private static final int COMPONENT_DEPTH_SAMPLER = 1;

    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;

    private int unifComponentDepthSampler;

    public DepthTestProgram() {
        super(PROGRAM_NAME);
        this.unifComponentDepthSampler = getUniformLocation("componentDepth");
        setUniformi(this.unifComponentDepthSampler, 1);
    }

    public void useSceneDepthSampler(MultisampleTexture2D texture){
        useTexture(texture, SCENE_DEPTH_SAMPLER);
    }

    public void useComponentDepthSampler(Texture2D texture){
        useTexture(texture, COMPONENT_DEPTH_SAMPLER);
    }


}
