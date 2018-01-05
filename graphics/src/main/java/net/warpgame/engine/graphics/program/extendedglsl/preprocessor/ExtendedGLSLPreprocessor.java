package net.warpgame.engine.graphics.program.extendedglsl.preprocessor;

import net.warpgame.engine.graphics.program.ProgramAssemblyInfo;
import net.warpgame.engine.graphics.program.extendedglsl.ProgramCompilationException;
import net.warpgame.engine.graphics.program.extendedglsl.loader.ProgramLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jaca777
 * Created 2017-09-22 at 19
 */
public class ExtendedGLSLPreprocessor {

    public enum ShaderType {
        NONE(null), VERTEX("VERT"), TCS("TCS"), TES("TES"), GEOMETRY("GEOM"), FRAGMENT("FRAG"), COMPUTE("COMP");
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

    public String preprocess(String code, ShaderType shaderType, ProgramAssemblyInfo assemblyInfo) {
        String withIncludes = processIncludes(code);
        String withConstants = processConstants(withIncludes);
        return processDefines(withConstants, shaderType, assemblyInfo);
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

    protected String processDefines(String code, ShaderType type, ProgramAssemblyInfo assemblyInfo) {
      List<String> defines = new ArrayList<>();
      if(type != ShaderType.NONE) {
          defines.add(type.getId());
      }
      if(assemblyInfo.getTcsShaderLocation() != null && assemblyInfo.getFragmentShaderLocation() != null){
          defines.add("SCENE_TESS");
      }
      if(assemblyInfo.getGeometryShaderLocation() != null) {
          defines.add("GEOM_ENABLED");
      }
      return insertDefines(code, defines);
    }

    protected String insertDefines(String code, List<String> defines) {
        StringBuilder defineStatements = new StringBuilder();
        for(String define : defines) {
            defineStatements.append("#define ").append(define).append("\n");
        }
        if(code.contains("#version")) {
            return code.replaceFirst("(#version .*)", "$1 \r\n" + defineStatements.toString());
        } else {
            return defineStatements.toString() + code;
        }
    }

}
