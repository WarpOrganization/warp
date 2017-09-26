package pl.warp.engine.graphics.tessellation.program;

/**
 * @author Jaca777
 * Created 2017-09-26 at 15
 */
public class TessellationProgram {
    private String tcsShader;
    private String tesShader;

    public TessellationProgram(String tcsShader, String tesShader) {
        this.tcsShader = tcsShader;
        this.tesShader = tesShader;
    }

    public String getTcsShader() {
        return tcsShader;
    }

    public TessellationProgram setTcsShader(String tcsShader) {
        this.tcsShader = tcsShader;
        return this;
    }

    public String getTesShader() {
        return tesShader;
    }

    public TessellationProgram setTesShader(String tesShader) {
        this.tesShader = tesShader;
        return this;
    }
}
