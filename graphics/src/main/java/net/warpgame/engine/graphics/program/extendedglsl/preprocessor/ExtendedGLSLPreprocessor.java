package net.warpgame.engine.graphics.program.extendedglsl.preprocessor;

import net.warpgame.engine.graphics.program.ProgramAssemblyInfo;
import net.warpgame.engine.graphics.program.extendedglsl.ProgramCompilationException;
import net.warpgame.engine.graphics.program.extendedglsl.loader.ProgramLoader;

import java.util.HashMap;
import java.util.Map;
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
        return processDefines(withIncludes, shaderType, assemblyInfo);
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

    protected String processDefines(String code, ShaderType type, ProgramAssemblyInfo assemblyInfo) {
      Map<String, String> defines = new HashMap<>();
      if(type != ShaderType.NONE) {
          defines.put(type.getId(), null);
      }
      if(assemblyInfo.getTcsShaderLocation() != null && assemblyInfo.getFragmentShaderLocation() != null){
          defines.put("SCENE_TESS", null);
      }
      if(assemblyInfo.getGeometryShaderLocation() != null) {
          defines.put("GEOM_ENABLED", null);
      }
      defines.putAll(constantField.getConstants());
      return insertDefines(code, defines);
    }

    protected String insertDefines(String code, Map<String, String> defines) {
        StringBuilder defineStatements = new StringBuilder();
        for(Map.Entry<String, String> define : defines.entrySet()) {
            StringBuilder defineStatement = defineStatements.append("#define ").append(define.getKey());
            if(define.getValue() != null)
                defineStatement.append(" " + define.getValue());
            defineStatement.append("\n");
        }
        if(code.contains("#version")) {
            return code.replaceFirst("(#version .*)", "$1 \r\n" + defineStatements.toString());
        } else {
            return defineStatements.toString() + code;
        }
    }
}
