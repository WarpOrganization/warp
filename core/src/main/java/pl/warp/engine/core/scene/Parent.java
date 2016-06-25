package pl.warp.engine.core.scene;

import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 19
 */
public interface Parent extends Component{
    List<Component> getChildren();
}
