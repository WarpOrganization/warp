package pl.warp.engine.core.scene;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 18
 */
public abstract class Property<T extends Component> {
    private T owner;

    public Property(T owner) {
        this.owner = owner;
        owner.addProperty(this);
    }

    public T getOwner() {
        return owner;
    }
}
