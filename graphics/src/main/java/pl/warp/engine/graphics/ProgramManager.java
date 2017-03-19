package pl.warp.engine.graphics;

import pl.warp.engine.graphics.program.Program;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jaca777
 *         Created 2017-03-19 at 23
 */
public class ProgramManager {
    private Map<Class<? extends Program>, Program> programs = new HashMap<>();

    public boolean isProgramCompiled(Class<? extends Program> programClass){
        return programs.containsKey(programClass);
    }

    public Program getProgram(Class<? extends Program> programClass){
        return programs.get(programClass);
    }
}
