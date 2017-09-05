package pl.warp.test.services;

import pl.warp.engine.core.context.annotation.Service;

/**
 * @author Jaca777
 * Created 2017-09-05 at 20
 */
@Service
public class ValueBProvider {
    private int b = 30;

    public int getB() {
        return b;
    }
}
