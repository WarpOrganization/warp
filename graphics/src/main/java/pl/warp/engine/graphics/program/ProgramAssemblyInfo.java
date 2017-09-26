package pl.warp.engine.graphics.program;

import pl.warp.engine.graphics.tessellation.program.TessellationProgram;

/**
 * @author Jaca777
 * Created 2017-09-25 at 16
 */
public class ProgramAssemblyInfo {

    private boolean geometryEnabled = false;
    private TessellationProgram tessellationProgram = null;
    private String fragmentProgramLocation = "/frag";
    private String vertexProgramLocation = "/vert";
    private String geometryProgramLocation = "/geom";

    public static ProgramAssemblyInfo minimal() {
        return new ProgramAssemblyInfo();
    }

    public static ProgramAssemblyInfo withTesselation(TessellationProgram program) {
        return new ProgramAssemblyInfo()
                .setTessellationProgram(program);
    }

    public static  ProgramAssemblyInfo withGeometry() {
        return new ProgramAssemblyInfo()
                .setGeometryEnabled(true);
    }

    public static  ProgramAssemblyInfo withTesselationAndGeometry(TessellationProgram program) {
        return new ProgramAssemblyInfo()
                .setGeometryEnabled(true)
                .setTessellationProgram(program);
    }

    public boolean isGeometryEnabled() {
        return geometryEnabled;
    }


    public ProgramAssemblyInfo setGeometryEnabled(boolean geometryEnabled) {
        this.geometryEnabled = geometryEnabled;
        return this;
    }

    public TessellationProgram getTessellationProgram() {
        return tessellationProgram;
    }

    public ProgramAssemblyInfo setTessellationProgram(TessellationProgram tessellationProgram) {
        this.tessellationProgram = tessellationProgram;
        return this;
    }

    public String getFragmentProgramLocation() {
        return fragmentProgramLocation;
    }

    public ProgramAssemblyInfo setFragmentProgramLocation(String fragmentProgramLocation) {
        this.fragmentProgramLocation = fragmentProgramLocation;
        return this;
    }

    public String getVertexProgramLocation() {
        return vertexProgramLocation;
    }

    public ProgramAssemblyInfo setVertexProgramLocation(String vertexProgramLocation) {
        this.vertexProgramLocation = vertexProgramLocation;
        return this;
    }

    public String getGeometryProgramLocation() {
        return geometryProgramLocation;
    }

    public ProgramAssemblyInfo setGeometryProgramLocation(String geometryProgramLocation) {
        this.geometryProgramLocation = geometryProgramLocation;
        return this;
    }
}
