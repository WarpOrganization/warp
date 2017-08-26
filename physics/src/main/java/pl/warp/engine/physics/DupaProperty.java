package pl.warp.engine.physics;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

/**
 * @author Hubertus
 *         Created 05.03.17
 */
public class DupaProperty extends Property {
    public static final String DUPA_PROPERTY_NAME = "dupaProperty";
    private final Component c1;
    private final Component c2;

    public DupaProperty(Component c1, Component c2){
        super(DUPA_PROPERTY_NAME);
        this.c1 = c1;
        this.c2 = c2;
    }

    public Component getC1() {
        return c1;
    }

    public Component getC2() {
        return c2;
    }
}
