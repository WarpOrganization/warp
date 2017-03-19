package pl.warp.engine.graphics.program.extendedglsl;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import pl.warp.engine.graphics.program.GLSLShaderCompiler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 13
 */
public class ExtendedGLSLProgramCompiler {

    private ConstantField constants;
    private ProgramLoader programLoader;

    public ExtendedGLSLProgramCompiler(ConstantField constants, ProgramLoader programLoader) {
        this.constants = constants;
        this.programLoader = programLoader;
    }

    public ExtendedGLSLProgram compile(String vertexShaderName, String fragmentShaderName, String geometryShaderName) {
        String vertexShaderCode = programLoader.loadProgram(vertexShaderName);
        if (vertexShaderCode == null) throw new RuntimeException("Vertex shader not found: " + vertexShaderName);
        String glslVertexCode = process(vertexShaderCode);
        String fragmentShaderCode = programLoader.loadProgram(fragmentShaderName);
        if (fragmentShaderCode == null) throw new RuntimeException("Fragment shader not found: " + fragmentShaderName);
        String glslFragmentCode = process(fragmentShaderCode);
        String geometryShaderCode = programLoader.loadProgram(geometryShaderName);
        String glslGeometryCode = (geometryShaderCode != null) ? process(geometryShaderCode) : null;
        return compileGLSL(glslVertexCode, glslFragmentCode, glslGeometryCode);
    }

    public ExtendedGLSLProgram compile(String vertexShaderCode, String fragmentShaderCode) {
        return compile(vertexShaderCode, fragmentShaderCode, null);
    }

    protected String process(String code) {
        String preprocessed = preprocess(code);
        return processConstants(preprocessed);
    }

    private static final Pattern INCLUDE_EXPR_PATTERN = Pattern.compile("#include \"(.*)\"");

    protected String preprocess(String code) {
        String newCode = code;
        Matcher matcher = INCLUDE_EXPR_PATTERN.matcher(code);
        while (matcher.find()) {
            String programName = matcher.group(1);
            String programCode = findProgram(programName);
            newCode = newCode.replace(matcher.group(), programCode);
        }
        return newCode;
    }

    private String findProgram(String moduleName) {
        String code = programLoader.loadProgram(moduleName);
        if (code == null)
            throw new ProgramCompilationException("Unable to resolve program to include: " + moduleName);
        else return code;
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

    private ExtendedGLSLProgram compileGLSL(String vertexShaderCode, String fragmentShaderCode, String geometryShaderCode) {
        int vertexShader = GLSLShaderCompiler.compileShader(GL20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = GLSLShaderCompiler.compileShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        int geometryShader = 0;
        int glslProgram;
        String[] outNames = getOutput(fragmentShaderCode);
        if (geometryShaderCode != null) {
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

    public <T> ExtendedGLSLProgramCompiler setConstant(String name, T value) {
        constants.set(name, value);
        return this;
    }
}
