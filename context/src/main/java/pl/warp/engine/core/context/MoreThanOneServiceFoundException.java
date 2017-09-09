package pl.warp.engine.core.context;

import java.util.Optional;

/**
 * @author Jaca777
 * Created 2017-09-05 at 20
 */
public class MoreThanOneServiceFoundException extends RuntimeException {

    public MoreThanOneServiceFoundException(String typeName, Optional<String> qualifier) {
        super(qualifier
                .map(q -> "More than one service matching type " + typeName + "and qualifier \"" + q + "\" found")
                .orElseGet(() -> "More than one service matching type " + typeName + " found")
        );
    }
}
