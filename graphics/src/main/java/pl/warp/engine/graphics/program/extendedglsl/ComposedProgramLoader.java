package pl.warp.engine.graphics.program.extendedglsl;

/**
 * @author Jaca777
 *         Created 2016-08-07 at 17
 */
public class ComposedProgramLoader implements ProgramLoader {

    private ProgramLoader[] loaders;

    public ComposedProgramLoader(ProgramLoader... loaders) {
        this.loaders = loaders;
    }

    @Override
    public String loadProgram(String programName) {
        String program = null;
        for(int i = 0; (i < loaders.length && program == null); i++)
            program = loaders[i].loadProgram(programName);
        return program;
    }
}
