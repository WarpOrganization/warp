package pl.warp.engine.graphics.program.pool;

import pl.warp.engine.graphics.program.Program;

import java.util.Optional;

/**
 * @author Jaca777
 *         Created 2017-03-22 at 16
 */
public interface ProgramPool {
    boolean isProgramAvailable(Class<? extends Program> programClass);
    <T extends Program> Optional<T>  getProgram(Class<T> programClass);
    void registerProgram(Program program);
}
