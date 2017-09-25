package pl.warp.engine.graphics.program.extendedglsl.preprocessor;

import pl.warp.engine.graphics.program.extendedglsl.ProgramCompilationException;
import pl.warp.engine.graphics.program.extendedglsl.loader.ProgramLoader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jaca777
 * Created 2017-09-22 at 19
 */
public class ExtendedGLSLPreprocessor {

    public enum ShaderType {
        NONE(null), VERTEX("VERT"), TCS("TCS"), TES("TES"), GEOMETRY("GEOM"), FRAGMENT("FRAG");
        private String id;
        ShaderType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    private ConstantField constantField;
    private ProgramLoader programLoader;

    public ExtendedGLSLPreprocessor(ConstantField constantField, ProgramLoader programLoader) {
        this.constantField = constantField;
        this.programLoader = programLoader;
    }

    public String preprocess(String code, ShaderType shaderType) {
        String withIncludes = processIncludes(code);
        String withConstants = processConstants(withIncludes);
        return processShaderType(withConstants, shaderType);
    }

    private static final Pattern INCLUDE_EXPR_PATTERN = Pattern.compile("#include \"(.*)\"");

    protected String processIncludes(String code) {
        String resultCode = code;
        Matcher matcher = INCLUDE_EXPR_PATTERN.matcher(code);
        while (matcher.find()) {
            String programName = matcher.group(1);
            String programCode = findProgram(programName);
            resultCode = resultCode.replace(matcher.group(), programCode);
        }
        return resultCode;
    }

    private String findProgram(String moduleName) {
        String code = programLoader.loadProgram(moduleName);
        if (code == null)
            throw new ProgramCompilationException("Unable to resolve a program to include: " + moduleName);
        else return code;
    }

    private static final Pattern CONSTANT_EXPR_PATTERN = Pattern.compile("\\$(\\w*)\\$");

    protected String processConstants(String code) {
        String resultCode = code;
        Matcher matcher = CONSTANT_EXPR_PATTERN.matcher(code);
        while (matcher.find()) {
            String constName = matcher.group(1);
            if (!constantField.isSet(constName))
                throw new ProgramCompilationException("Unknown constant " + constName);
            resultCode = resultCode.replace(matcher.group(), constantField.get(constName).toString());
        }
        return resultCode;
    }

    protected String processShaderType(String code, ShaderType type) {
        String typeId = type.getId();
        String typeDef = (typeId != null) ? "#define " + typeId + "\r\n" : "";
        if(code.contains("#version")) {
            return code.replaceFirst("(#version .*)", "$1 \r\n" + typeDef);
        } else {
            return typeDef + code;
        }
    }

}
