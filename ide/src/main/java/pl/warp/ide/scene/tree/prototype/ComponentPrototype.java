package pl.warp.ide.scene.tree.prototype;

import pl.warp.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-01-29 at 11
 */
public abstract class ComponentPrototype {
    
    private String name;

    public ComponentPrototype(String name) {
        this.name = name;
    }

    public abstract GameComponent newInstance(GameComponent parent);

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
