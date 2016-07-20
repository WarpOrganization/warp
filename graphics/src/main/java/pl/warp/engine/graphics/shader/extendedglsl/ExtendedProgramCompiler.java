package pl.warp.engine.graphics.shader.extendedglsl;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import pl.warp.engine.graphics.shader.GLSLShaderCompiler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 13
 */
public class ExtendedProgramCompiler {

    private ConstantField constants;
    private String vertexShaderCode;
    private String geometryShaderCode;
    private String fragmentShaderCode;

    private int vertexShader;
    private int geometryShader;
    private int fragmentShader;
    private int glslProgram;


    public ExtendedProgramCompiler(String vertexShaderCode, String fragmentShaderCode, ConstantField constants) {
        this.constants = constants;
        this.vertexShaderCode = vertexShaderCode;
        this.fragmentShaderCode = fragmentShaderCode;
    }

    public void useGeometryShader(String code) {
        this.geometryShaderCode = code;
    }

    public void compile() {
        if(isComplete())
            throw new IllegalStateException("Program is not ready for compilation.");
        processConstants();
        compileGLSL();
    }

    private void processConstants() {
        vertexShaderCode = processConstants(vertexShaderCode);
        if (hasGeometryShader())
            geometryShaderCode = processConstants(geometryShaderCode);
        fragmentShaderCode = processConstants(fragmentShaderCode);
    }

    private static final Pattern CONSTANT_EXPR_PATTERN = Pattern.compile("%(\\w*)%");

    private String processConstants(String code) {
        String newCode = code;
        Matcher matcher = CONSTANT_EXPR_PATTERN.matcher(code);
        while(matcher.find()) {
            String constName = matcher.group(1);
            if(!constants.isSet(constName))
                throw new ProgramCompilationException("Unknown constant " + constName);
            newCode = newCode.replace(matcher.group(), constants.get(constName));
        }
        return newCode;
    }

    private int compileGLSL() {
        this.vertexShader = GLSLShaderCompiler.compileShader(GL20.GL_VERTEX_SHADER, vertexShaderCode);
        this.fragmentShader = GLSLShaderCompiler.compileShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        String[] outNames = getOutput(fragmentShaderCode);
        if (hasGeometryShader()) {
            this.geometryShader = GLSLShaderCompiler.compileShader(GL32.GL_GEOMETRY_SHADER, geometryShaderCode);
            this.glslProgram = GLSLShaderCompiler.createProgram(new int[]{this.geometryShader, this.vertexShader, this.fragmentShader}, outNames);
        }
        this.glslProgram = GLSLShaderCompiler.createProgram(new int[]{this.vertexShader, this.fragmentShader}, outNames);
        return glslProgram;
    }

    private static final Pattern OUTPUT_VAR_PATTERN = Pattern.compile("layout\\(location = (\\d*)\\) out \\w* \\w*;");

    private String[] getOutput(String fragmentShaderCode) {
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

    public boolean isCompiled() {
        return glslProgram > 0;
    }

    public boolean hasGeometryShader() {
        return geometryShaderCode != null;
    }

    public int getGeometryShader() {
        if (isCompiled()) return geometryShader;
        else throw new IllegalStateException("Program has not been compiled yet.");
    }

    public int getVertexShader() {
        if (isCompiled()) return vertexShader;
        else throw new IllegalStateException("Program has not been compiled yet.");
    }

    public int getFragmentShader() {
        if (isCompiled()) return fragmentShader;
        else throw new IllegalStateException("Program has not been compiled yet.");
    }

    public int getGlslProgram() {
        if (isCompiled()) return glslProgram;
        else throw new IllegalStateException("Program has not been compiled yet.");
    }
}
