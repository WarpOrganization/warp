package pl.warp.test.services;

import pl.warp.engine.core.context.annotation.Service;

/**
 * @author Jaca777
 * Created 2017-09-05 at 20
 */
@Service
public class SummingService {
    private ValueAProvider valueAProvider;
    private ValueBProvider valueBProvider;

    public SummingService(ValueAProvider valueAProvider, ValueBProvider valueBProvider) {
        this.valueAProvider = valueAProvider;
        this.valueBProvider = valueBProvider;
    }

    public int sum() {
        return valueAProvider.getA() + valueBProvider.getB();
    }
}
