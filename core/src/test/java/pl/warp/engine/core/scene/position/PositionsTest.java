package pl.warp.engine.core.scene.position;

import javafx.geometry.Pos;
import org.joml.Vector3f;
import org.junit.Test;
import pl.warp.engine.core.EngineContext;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.SimpleComponent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by user on 2017-01-10.
 */
public class PositionsTest {

    public static final double EPS = 0.000001;

    @Test
    public void getPositionRelativeToParent() throws Exception {
        EngineContext context = mock(EngineContext.class);
        Component c1 = new SimpleComponent(context);
        Component c2 = new SimpleComponent(c1);
        Vector3f position = new Vector3f(10.0f);
        new PositionProperty(c2, position, new LazyPositionCalculationStrategy(), 4);
        Vector3f dest = new Vector3f();
        Positions.getPositionRelativeToParent(c2, c1, 4, dest);
        assertEquals(dest.x, position.x, EPS);
    }

    @Test
    public void getPositionRelativeToNeighbour() throws Exception {
        EngineContext context = mock(EngineContext.class);
        Component c1 = new SimpleComponent(context);
        Component c2 = new SimpleComponent(c1);
        Vector3f position2 = new Vector3f(10.0f);
        new PositionProperty(c2, position2, new LazyPositionCalculationStrategy(), 3);
        Component c3 = new SimpleComponent(c1);
        Vector3f position3 = new Vector3f(14.0f);
        new PositionProperty(c3, position3, new LazyPositionCalculationStrategy(), 3);
        Vector3f dest = new Vector3f();
        Positions.getPositionRelativeToNeighbour(c2, c1, c3, c1, 3, dest);
        assertEquals(position3.x - position2.x, dest.x, EPS);
    }

    @Test
    public void getPositionRelativeToNeighbour2() throws Exception {
        EngineContext context = mock(EngineContext.class);
        Component c1 = new SimpleComponent(context);
        Component c2 = new SimpleComponent(c1);
        Vector3f position2 = new Vector3f(10.0f);
        new PositionProperty(c2, position2, new LazyPositionCalculationStrategy(), 3);
        Component c3 = new SimpleComponent(c1);
        Vector3f position3 = new Vector3f(14.0f);
        new PositionProperty(c3, position3, new LazyPositionCalculationStrategy(), 3);
        Component c4 = new SimpleComponent(c2);
        Vector3f position4 = new Vector3f(12.0f);
        new PositionProperty(c4, position4, new LazyPositionCalculationStrategy(), 3);
        Vector3f dest = new Vector3f();
        Positions.getPositionRelativeToNeighbour(c4, c1, c3, c1, 3, dest);
        assertEquals(position3.x - (position2.x + position4.x), dest.x, EPS);
    }

    @Test
    public void getMutualUnit() throws Exception {
        EngineContext context = mock(EngineContext.class);
        Component c1 = new SimpleComponent(context);
        Component c2 = new SimpleComponent(c1);
        Component c3 = new SimpleComponent(c1);
        Component c4 = new SimpleComponent(c2);
        Component c5 = new SimpleComponent(c3);
        new PositionProperty(c2, new Vector3f(), new ImmediatePositionCalculationStrategy(), 2);
        new PositionProperty(c3, new Vector3f(), new ImmediatePositionCalculationStrategy(), 2);
        int mutualUnit = Positions.getMutualUnit(c4, c5);
        assertEquals(mutualUnit, 2);
    }

    @Test
    public void getPositionProperty() throws Exception {
        EngineContext context = mock(EngineContext.class);
        Component c1 = new SimpleComponent(context);
        PositionProperty prop = new PositionProperty(c1, new Vector3f(), new ImmediatePositionCalculationStrategy(), 2);
        assertEquals(prop, Positions.getPositionProperty(c1));
    }

    @Test
    public void hasPosition() throws Exception {
        EngineContext context = mock(EngineContext.class);

        Component c1 = new SimpleComponent(context);
        new PositionProperty(c1, new Vector3f(), new ImmediatePositionCalculationStrategy(), 2);
        assertTrue(Positions.hasPosition(c1));

        Component c2 = new SimpleComponent(context);
        assertFalse(Positions.hasPosition(c2));
    }

    @Test
    public void getRelativePosition() throws Exception {
        //TODO Some complex test.
    }

    @Test
    public void hasPositionUnit() throws Exception {
        EngineContext context = mock(EngineContext.class);
        Component c1 = new SimpleComponent(context);
        new PositionProperty(c1, new Vector3f(), new ImmediatePositionCalculationStrategy(), 2);
        assertTrue(Positions.hasPositionUnit(c1, 2));
        assertFalse(Positions.hasPositionUnit(c1, 3));
    }
}