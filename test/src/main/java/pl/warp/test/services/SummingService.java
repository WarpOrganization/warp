package pl.warp.test.services;

import pl.warp.engine.core.context.config.ConfigValue;
import pl.warp.engine.core.context.config.EnableConfig;
import pl.warp.engine.core.context.service.Service;

/**
 * @author Jaca777
 * Created 2017-09-05 at 20
 */

@EnableConfig
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

    @ConfigValue("dupa")
    public void onDupaChanged(int dupa) {
        System.out.println(dupa + "ss");
    }
}
