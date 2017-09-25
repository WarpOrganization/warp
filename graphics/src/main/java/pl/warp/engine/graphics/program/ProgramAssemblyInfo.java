package pl.warp.engine.graphics.program;

/**
 * @author Jaca777
 * Created 2017-09-25 at 16
 */
public class ProgramAssemblyInfo {

    private boolean geometryEnabled;
    private boolean tesselationEnabled;
    private String tesProgramLocation = "/tes";
    private String tcsProgramLocation = "/tcs";
    private String fragmentProgramLocation = "/frag";
    private String vertexProgramLocation = "/vert";
    private String geometryProgramLocation = "/geom";
    

    public ProgramAssemblyInfo(boolean geometryEnabled, boolean tesselationEnabled) {
        this.geometryEnabled = geometryEnabled;
        this.tesselationEnabled = tesselationEnabled;
    }


    public static ProgramAssemblyInfo minimal() {
        return new ProgramAssemblyInfo(false, false);
    }

    public static ProgramAssemblyInfo withTesselation() {
        return new ProgramAssemblyInfo(false, true);
    }

    public static  ProgramAssemblyInfo withGeometry() {
        return new ProgramAssemblyInfo(true, false);
    }

    public static  ProgramAssemblyInfo withTesselationAndGeometry() {
        return new ProgramAssemblyInfo(true, true);
    }

    public boolean isGeometryEnabled() {
        return geometryEnabled;
    }

    public boolean isTesselationEnabled() {
        return tesselationEnabled;
    }

    public String getTesProgramLocation() {
        return tesProgramLocation;
    }

    public ProgramAssemblyInfo setTesProgramLocation(String tesProgramLocation) {
        this.tesProgramLocation = tesProgramLocation;
        return this;
    }

    public String getTcsProgramLocation() {
        return tcsProgramLocation;
    }

    public ProgramAssemblyInfo setTcsProgramLocation(String tcsProgramLocation) {
        this.tcsProgramLocation = tcsProgramLocation;
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
