package pl.warp.engine.graphics.shader.program.postprocessing.gaussianblur;

import org.joml.Vector2f;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.shader.Program;
import pl.warp.engine.graphics.shader.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.texture.Texture2D;

import java.io.InputStream;
import java.util.concurrent.Callable;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 19
 */
public class GaussianBlurProgram extends Program {

    public enum GaussianBlurStage {
        VERTICAL(new Vector2f(0.0f, 1.0f)), HORIZONTAL(new Vector2f(1.0f, 0.0f));

        private Vector2f direction;
        private Callable<Integer> displaySize;

        GaussianBlurStage(Vector2f direction) {
            this.direction = direction;
        }

        public Vector2f getDirection() {
            return direction;
        }

        public Callable<Integer> getDisplaySize() {
            return displaySize;
        }

        private void setDisplaySize(Callable<Integer> displaySize) {
            this.displaySize = displaySize;
        }
    }

    private static String PROGRAM_NAME = "postprocessing/gaussianblur";
    private static final int TEXTURE_SAMPLER = 0;

    public static final int ATTR_VERTEX = 0;
    public static final int ATTR_TEX_COORD = 1;

    private int unifBlurDirection;
    private int unifDisplaySize;

    public GaussianBlurProgram(RenderingConfig config) {
        super(PROGRAM_NAME);
        loadUniforms();
        GaussianBlurStage.HORIZONTAL.setDisplaySize(() -> config.getDisplay().getHeight());
        GaussianBlurStage.VERTICAL.setDisplaySize(() -> config.getDisplay().getHeight());
    }

    private void loadUniforms() {
        this.unifBlurDirection = getUniformLocation("blurDirection");
        this.unifDisplaySize = getUniformLocation("displaySize");
    }

    public void useTexture(Texture2D texture) {
        useTexture(texture, TEXTURE_SAMPLER);
    }

    public void setStage(GaussianBlurStage stage) {
        try {
            setUniformV2(unifBlurDirection, stage.getDirection().x, stage.getDirection().y);
            setUniformi(unifDisplaySize, stage.getDisplaySize().call());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
