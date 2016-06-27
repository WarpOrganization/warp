package pl.warp.engine.core.scene;

import com.google.common.collect.Lists;
import pl.warp.engine.core.EngineContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 19
 */
public abstract class Parent extends Component {

    public Parent(Parent parent) {
        super(parent);
    }

    public Parent(EngineContext context) {
        super(context);
    }

    public abstract List<Component> getChildren();

    public <T extends Property> Set<T> getChildrenProperties(Class<T> propertyClass) {
        Stream<T> childrenOfChildrenProperties = getChildren().stream()
                .filter(c -> c instanceof Parent)
                .map(c -> (Parent) c)
                .flatMap(p -> p.getChildrenProperties(propertyClass).stream());
        Stream<T> childrenProperties = getChildren().stream()
                .filter(c -> c.hasProperty(propertyClass))
                .map(c -> c.getProperty(propertyClass));
        Stream<T> allChildrenProperties = Stream.concat(
                childrenOfChildrenProperties,
                childrenProperties);
        return allChildrenProperties.collect(Collectors.toSet());
    }
}
