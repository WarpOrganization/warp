package pl.warp.engine.graphics.program.pool;

import pl.warp.engine.graphics.program.Program;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 16
 */
public class ProgramPoolImpl implements ProgramPool {

    private Map<Class<? extends Program>, Program> programs = new HashMap<>();

    @Override
    public boolean isProgramAvailable(Class<? extends Program> programClass) {
        return programs.containsKey(programClass);
    }

    @Override
    public <T extends Program> Optional<T> getProgram(Class<T> programClass) {
        return isProgramAvailable(programClass) ? Optional.of((T) programs.get(programClass)) : Optional.empty();
    }

    @Override
    public void registerProgram(Program program) {
        this.programs.put(program.getClass(), program);
    }
}
