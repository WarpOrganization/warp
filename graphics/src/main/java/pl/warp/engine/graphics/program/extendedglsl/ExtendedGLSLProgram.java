package pl.warp.engine.graphics.program.extendedglsl;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 13
 */
public class ExtendedGLSLProgram {
    private int fragmentShader;
    private int vertexShader;
    private int geometryShader;
    private int program;

    public ExtendedGLSLProgram(int fragmentShader, int vertexShader, int program) {
        this.fragmentShader = fragmentShader;
        this.vertexShader = vertexShader;
        this.program = program;
    }

    public ExtendedGLSLProgram(int fragmentShader, int vertexShader, int geometryShader, int program) {
        this.fragmentShader = fragmentShader;
        this.vertexShader = vertexShader;
        this.geometryShader = geometryShader;
        this.program = program;
    }

    public boolean hasGeometryShader() {
        return geometryShader > 0;
    }

    public int getFragmentShader() {
        return fragmentShader;
    }

    public int getVertexShader() {
        return vertexShader;
    }

    public int getGeometryShader() {
        if (hasGeometryShader()) return geometryShader;
        else throw new IllegalStateException("Unable to get a geometry shader, it's not attached to the program.");
    }

    public int getGLProgram() {
        return program;
    }
}
