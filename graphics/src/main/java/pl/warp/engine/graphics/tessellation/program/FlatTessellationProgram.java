package pl.warp.engine.graphics.tessellation.program;

import pl.warp.engine.graphics.program.extendedglsl.loader.ProgramLoader;

/**
 * @author Jaca777
 * Created 2017-09-26 at 15
 */
public class FlatTessellationProgram extends TessellationProgram {
    private static final String LOCATION = "tessellation/flat/";

    public FlatTessellationProgram(ProgramLoader loader) {
        super(loader.loadProgram(LOCATION + "tcs"), loader.loadProgram(LOCATION + "tes"));
    }
}
