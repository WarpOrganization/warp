package pl.warp.engine.graphics;

/**
 * @author Jaca777
 *         Created 2017-03-20 at 22
 *         TODO remove - demonstrational feature
 */
public class PipelineEffects {
    private boolean monochromatic;
    private boolean distorted;
    private boolean mosaic;
    private boolean screen;
    private boolean barrelchroma;

    public boolean isMonochromatic() {
        return monochromatic;
    }

    public void setMonochromatic(boolean monochromatic) {
        this.monochromatic = monochromatic;
    }

    public boolean isDistorted() {
        return distorted;
    }

    public void setDistorted(boolean distorted) {
        this.distorted = distorted;
    }

    public boolean isMosaic() {
        return mosaic;
    }

    public void setMosaic(boolean mosaic) {
        this.mosaic = mosaic;
    }

    public boolean isScreen() {
        return screen;
    }

    public void setScreen(boolean screen) {
        this.screen = screen;
    }

    public boolean isBarrelchroma() {
        return barrelchroma;
    }

    public void setBarrelchroma(boolean barrelchroma) {
        this.barrelchroma = barrelchroma;
    }
}
