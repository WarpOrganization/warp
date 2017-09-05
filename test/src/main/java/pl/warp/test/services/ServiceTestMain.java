package pl.warp.test.services;

import pl.warp.engine.core.context.Context;

/**
 * @author Jaca777
 * Created 2017-09-05 at 20
 */
public class ServiceTestMain {
    public static void main(String... args) {
        Context context = Context.create();
        SummingService summingService = context.findOne(SummingService.class).get();
        System.out.println(summingService.sum());
    }
}
