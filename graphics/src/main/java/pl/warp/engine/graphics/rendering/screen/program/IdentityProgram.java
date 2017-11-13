package pl.warp.engine.graphics.rendering.screen.program;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.program.ProgramAssemblyInfo;
import pl.warp.engine.graphics.rendering.scene.gbuffer.GBuffer;

/**
 * @author Jaca777
 * Created 2017-11-11 at 16
 */
public class IdentityProgram extends Program {

    public IdentityProgram() {
        super(new ProgramAssemblyInfo("identity"));
    }

    public void init() {
        setTextureLocation("comp0", 0);
        setTextureLocation("comp1", 1);
        setTextureLocation("comp2", 2);
        setTextureLocation("comp3", 3);
    }

    public void useGBuffer(GBuffer gBuffer) {
        for(int i = 0; i < 4; i++){
            useTexture(i, gBuffer.getTextureName(i), GL11.GL_TEXTURE_2D);
        }
    }
}
