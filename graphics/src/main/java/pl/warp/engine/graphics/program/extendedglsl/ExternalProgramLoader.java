package pl.warp.engine.graphics.program.extendedglsl;

/**
 * @author Jaca777
 *         Created 2016-08-07 at 17
 */
public class ExternalProgramLoader extends ComposedProgramLoader {
    public ExternalProgramLoader(String externalPath) {
        super(LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER,
                new LocalProgramLoader(externalPath, LocalProgramLoader.DEFAULT_PROGRAM_EXTENSION));
    }
}
