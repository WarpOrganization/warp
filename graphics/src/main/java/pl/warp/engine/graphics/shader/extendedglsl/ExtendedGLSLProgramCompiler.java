package pl.warp.engine.graphics.shader.extendedglsl;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import pl.warp.engine.graphics.shader.GLSLShaderCompiler;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 13
 */
public class ExtendedGLSLProgramCompiler {

    private ConstantField constants;
    private String vertexShaderCode;
    private String geometryShaderCode;
    private String fragmentShaderCode;

    public ExtendedGLSLProgramCompiler(String vertexShaderCode, String fragmentShaderCode, ConstantField constants) {
        Objects.requireNonNull(vertexShaderCode);
        Objects.requireNonNull(fragmentShaderCode);
        Objects.requireNonNull(constants);
        this.constants = constants;
        this.vertexShaderCode = vertexShaderCode;
        this.fragmentShaderCode = fragmentShaderCode;
    }

    public void useGeometryShader(String code) {
        this.geometryShaderCode = code;
    }

    public ExtendedGLSLProgram compile() {
        if (!isComplete())
            throw new IllegalStateException("Program is not ready for compilation.");
        processConstants();
        return compileGLSL();
    }

    protected void processConstants() {
        vertexShaderCode = processConstants(vertexShaderCode);
        if (hasGeometryShader())
            geometryShaderCode = processConstants(geometryShaderCode);
        fragmentShaderCode = processConstants(fragmentShaderCode);
    }

    private static final Pattern CONSTANT_EXPR_PATTERN = Pattern.compile("\\$(\\w*)\\$");

    protected String processConstants(String code) {
        String newCode = code;
        Matcher matcher = CONSTANT_EXPR_PATTERN.matcher(code);
        while (matcher.find()) {
            String constName = matcher.group(1);
            if (!constants.isSet(constName))
                throw new ProgramCompilationException("Unknown constant " + constName);
            newCode = newCode.replace(matcher.group(), constants.get(constName).toString());
        }
        return newCode;
    }

    private ExtendedGLSLProgram compileGLSL() {
        int vertexShader = GLSLShaderCompiler.compileShader(GL20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = GLSLShaderCompiler.compileShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        int geometryShader = 0;
        int glslProgram;
        String[] outNames = getOutput(fragmentShaderCode);
        if (hasGeometryShader()) {
            geometryShader = GLSLShaderCompiler.compileShader(GL32.GL_GEOMETRY_SHADER, geometryShaderCode);
            glslProgram = GLSLShaderCompiler.createProgram(new int[]{geometryShader, vertexShader, fragmentShader}, outNames);
        } else glslProgram = GLSLShaderCompiler.createProgram(new int[]{vertexShader, fragmentShader}, outNames);
        return new ExtendedGLSLProgram(fragmentShader, vertexShader, geometryShader, glslProgram);
    }

    private static final Pattern OUTPUT_VAR_PATTERN = Pattern.compile("layout\\(location = (\\d*)\\) out \\w* (\\w*);");

    protected String[] getOutput(String fragmentShaderCode) {
        Matcher matcher = OUTPUT_VAR_PATTERN.matcher(fragmentShaderCode);
        int matchesNumber = countMatches(matcher);
        String[] outputNames = new String[matchesNumber];
        while (matcher.find()) {
            int index = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2);
            checkOutputVariable(index, outputNames, matchesNumber);
            outputNames[index] = name;
        }
        return outputNames;
    }

    private void checkOutputVariable(int index, String[] outputNames, int matchesNumber) {
        if (index >= matchesNumber)
            throw new ProgramCompilationException("Fragment shader contains a output variable with an unexpected location.");
        if (outputNames[index] != null)
            throw new ProgramCompilationException("Locations of output variables are not unique in the fragment shader.");
    }

    private int countMatches(Matcher matcher) {
        int count = 0;
        while (matcher.find())
            count++;
        matcher.reset();
        return count;
    }

    public boolean isComplete() {
        return vertexShaderCode != null
                && fragmentShaderCode != null;
    }

    public boolean hasGeometryShader() {
        return geometryShaderCode != null;
    }

    public ConstantField getConstants() {
        return constants;
    }

    public void setConstants(ConstantField constants) {
        this.constants = constants;
    }

    public String getVertexShaderCode() {
        return vertexShaderCode;
    }

    public void setVertexShaderCode(String vertexShaderCode) {
        this.vertexShaderCode = vertexShaderCode;
    }

    public String getGeometryShaderCode() {
        return geometryShaderCode;
    }

    public void setGeometryShaderCode(String geometryShaderCode) {
        this.geometryShaderCode = geometryShaderCode;
    }

    public String getFragmentShaderCode() {
        return fragmentShaderCode;
    }

    public void setFragmentShaderCode(String fragmentShaderCode) {
        this.fragmentShaderCode = fragmentShaderCode;
    }
}
