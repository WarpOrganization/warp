package pl.warp.engine.graphics.program.rendering.postprocessing.depthtest;

import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.texture.MultisampleTexture2D;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2017-02-24 at 22
 */
public class DepthTestProgram extends Program {
    private static String PROGRAM_NAME = "postprocessing/depthtest";
    private static final int SCENE_DEPTH_SAMPLER = 2;
    private static final int COMPONENT_DEPTH_SAMPLER = 1;
    private static final int COMPONENT_SAMPLER = 0;

    public static final int ATTR_VERTEX = 0;

    private int unifComponentDepthSampler;
    private int unifSceneDepthSampler;
    private int unifComponentSampler;

    public DepthTestProgram() {
        super(PROGRAM_NAME);
        this.unifSceneDepthSampler = getUniformLocation("sceneDepth");
        this.unifComponentDepthSampler = getUniformLocation("componentDepth");
        this.unifComponentSampler = getUniformLocation("component");
        setUniformi(this.unifComponentSampler, COMPONENT_SAMPLER);
        setUniformi(this.unifComponentDepthSampler, COMPONENT_DEPTH_SAMPLER);
        setUniformi(this.unifSceneDepthSampler, SCENE_DEPTH_SAMPLER);
    }

    public void useSceneDepthSampler(MultisampleTexture2D texture){
        useTexture(texture, SCENE_DEPTH_SAMPLER);
    }

    public void useComponentDepthSampler(Texture2D texture){
        useTexture(texture, COMPONENT_DEPTH_SAMPLER);
    }


    public void useComponentTexture(Texture2D componentTexture) {
        useTexture(componentTexture, COMPONENT_SAMPLER);
    }
}
