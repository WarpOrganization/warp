package pl.warp.engine.core.scene.position;

import org.apache.commons.lang3.tuple.Pair;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;

import java.util.Objects;

/**
 * @author Jaca777
 *         Created 2016-12-27 at 15
 */
public class Positions {
    public static int getMutualUnit(Component a, Component b) {
        Pair<Component, Component> parents = getComponentsWithPosWithMutualParent(a, b);
        Component parentA = parents.getLeft();
        Component parentB = parents.getRight();
        PositionProperty posA = getPositionProperty(parentA);
        PositionProperty posB = getPositionProperty(parentB);
        if (posA.getUnit() == posB.getUnit())
            return posA.getUnit();
        else throw new ScenePositionException("Components don't have a mutual unit.");
    }

    public static PositionProperty getPositionProperty(Component a) {
        return a.getProperty(PositionProperty.POSITION_PROPERTY_NAME);
    }

    public static boolean hasPosition(Component a) {
        return a.hasProperty(PositionProperty.POSITION_PROPERTY_NAME);
    }

    public static Vector3f getRelativePosition(Component a, Component b, int unit, Vector3f dest) {
        Component parentA = findParentWithUnit(a, unit);
        Component parentB = findParentWithUnit(b, unit);
        int aDepth = getDepth(a);
        int bDepth = getDepth(b);
        Pair<Component, Component> parentsWithMutualParent = getComponentsWithPosWithMutualParent(parentA, parentB);
        if (parentsWithMutualParent.getRight() == parentsWithMutualParent.getLeft()) {
            Component child = (aDepth > bDepth) ? parentA : parentB;
            Component parent = (aDepth > bDepth) ? parentB : parentA;
            return getPositionRelativeToParent(child, parent, unit, dest);
        } else {
            return getPositionRelativeToNeighbour(parentA, parentsWithMutualParent.getRight(), parentB, parentsWithMutualParent.getLeft(), unit, dest);
        }
    }

    public static Vector3f getPositionRelativeToParent(Component child, Component parent, int unit, Vector3f dest) {
        PositionProperty property = getPositionProperty(child);
        return property.getStrategy().getPosition(parent, unit, dest);
    }

    public static Vector3f getPositionRelativeToNeighbour(Component a, Component aRel, Component b, Component bRel, int unit, Vector3f dest) {
        getPositionRelativeToParent(a, aRel, unit, dest);
        float x = dest.x;
        float y = dest.y;
        float z = dest.z;
        dest.zero();
        getPositionRelativeToParent(b, bRel, unit, dest);
        dest.sub(x, y, z);
        return dest;
    }

    private static Component findParentWithUnit(Component component, int unit) {
        Component currentParent = component;
        while (currentParent.hasParent() && hasPositionUnit(currentParent, unit))
            currentParent = currentParent.getParent();
        if (!hasPositionUnit(currentParent, unit))
            throw new ScenePositionException("Components don't have a mutual parent with a given position unit.");
        return currentParent;
    }

    public static boolean hasPositionUnit(Component component, int unit) {
        return hasPosition(component) && getPositionProperty(component).getUnit() == unit;
    }

    private static Pair<Component, Component> getComponentsWithPosWithMutualParent(Component a, Component b) {
        Component currentAComp = a;
        Component currentBComp = b;
        Component currentACompWithPos = null;
        Component currentBCompWithPos = null;
        int aDepth = getDepth(a);
        int bDepth = getDepth(b);

        if (aDepth > bDepth)
            currentAComp = getParent(a, aDepth - bDepth);
        else if (bDepth > aDepth)
            currentBComp = getParent(b, bDepth - aDepth);

        for (int i = 0; i < Math.min(aDepth, bDepth); i++) {
            if (hasPosition(currentAComp))
                currentACompWithPos = currentAComp;
            if (hasPosition(currentBComp))
                currentBCompWithPos = currentBComp;
            if (currentAComp.getParent() == currentBComp.getParent()) {
                if (Objects.isNull(currentACompWithPos) || Objects.isNull(currentBCompWithPos))
                    throw new ScenePositionException("Components don't have a position property.");
                else return Pair.of(currentACompWithPos, currentBCompWithPos);
            } else {
                currentAComp = currentAComp.getParent();
                currentBComp = currentBComp.getParent();
            }
        }
        throw new ScenePositionException("Components don't belong to the same scene");
    }

    private static Component getParent(Component comp, int i) {
        Component currentComp = comp;
        for (int j = i; j > 0; j++)
            currentComp = currentComp.getParent();
        return currentComp;
    }

    private static int getDepth(Component comp) {
        Component currentComp = comp;
        int i = 0;
        while (currentComp.hasParent()) {
            currentComp = currentComp.getParent();
            i++;
        }
        return i;
    }
}
