package pl.warp.engine.core.scene;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 18
 */
public abstract class Property<T extends Component> {
    private T owner;
    private String name;

    public Property(T owner) {
        this.owner = owner;
        this.name = getClass().getName();
        owner.addProperty(this);
    }

    public Property(T owner, String name) {
        this.owner = owner;
        this.name = name;
        owner.addProperty(this);
    }

    public T getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }
}
