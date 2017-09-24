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
public class ValueAProvider {
    private int a = 20;

    public int getA() {
        return a;
    }

    @ConfigValue("dupa")
    public void onDupaChanged(int dupa) {
        System.out.println(dupa + "s2");
    }

    @ConfigValue("kaczka")
    public void onKaczka(int kaczka){
        System.out.println(kaczka);
    }
}
