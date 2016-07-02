package pl.warp.engine.core.scene;

import com.google.common.collect.ImmutableList;
import pl.warp.engine.core.EngineContext;

import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 23
 */
public class SimpleComponent extends Component {

    public SimpleComponent(EngineContext context) {
        super(context);
    }

    public SimpleComponent(Component parent) {
        super(parent);
    }
}
