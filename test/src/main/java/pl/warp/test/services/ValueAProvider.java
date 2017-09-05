package pl.warp.test.services;

import pl.warp.engine.core.context.annotation.Service;

/**
 * @author Jaca777
 * Created 2017-09-05 at 20
 */
@Service
public class ValueAProvider {
    private int a = 20;

    public int getA() {
        return a;
    }
}
