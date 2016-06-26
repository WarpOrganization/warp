package pl.warp.engine.core.scene;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 18
 */
public abstract class Property {
    private Component owner;

    public Component getOwner() {
        return owner;
    }

    void setOwner(Component owner) {
        this.owner = owner;
    }
}
