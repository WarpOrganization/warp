package pl.warp.engine.graphics.program;

/**
 * @author Jaca777
 * Created 2017-09-25 at 16
 */
public class ProgramAssemblyInfo {

    private String programName;
    private String fragmentShaderLocation;
    private String vertexShaderLocation;
    private String geometryShaderLocation;
    private String tcsShaderLocation;
    private String tesShaderLocation;

    public ProgramAssemblyInfo(String programName) {
        this.programName = programName;
        this.vertexShaderLocation = programName + "/vert";
        this.fragmentShaderLocation = programName + "/frag";
    }

    public ProgramAssemblyInfo() {
    }

    public String getProgramName() {
        return programName;
    }

    public String getFragmentShaderLocation() {
        return fragmentShaderLocation;
    }

    public ProgramAssemblyInfo setFragmentShaderLocation(String fragmentShaderLocation) {
        this.fragmentShaderLocation = fragmentShaderLocation;
        return this;
    }

    public String getVertexShaderLocation() {
        return vertexShaderLocation;
    }

    public ProgramAssemblyInfo setVertexShaderLocation(String vertexShaderLocation) {
        this.vertexShaderLocation = vertexShaderLocation;
        return this;
    }

    public String getGeometryShaderLocation() {
        return geometryShaderLocation;
    }

    public ProgramAssemblyInfo setGeometryShaderLocation(String geometryShaderLocation) {
        this.geometryShaderLocation = geometryShaderLocation;
        return this;
    }

    public ProgramAssemblyInfo setTcsShaderLocation(String tcsShaderLocation) {
        this.tcsShaderLocation = tcsShaderLocation;
        return this;
    }

    public ProgramAssemblyInfo setTesShaderLocation(String tesShaderLocation) {
        this.tesShaderLocation = tesShaderLocation;
        return this;
    }

    public String getTcsShaderLocation() {
        return tcsShaderLocation;
    }

    public String getTesShaderLocation() {
        return tesShaderLocation;
    }

    public ProgramAssemblyInfo setTesselator(String tesselatorLocation) {
        this.tcsShaderLocation = tesselatorLocation + "/tcs";
        this.tesShaderLocation = tesselatorLocation + "/tes";
        return this;
    }
}
