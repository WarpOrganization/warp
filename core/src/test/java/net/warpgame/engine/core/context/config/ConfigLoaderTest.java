package net.warpgame.engine.core.context.config;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author KocproZ
 * Created 2018-07-31 at 18:36
 */
public class ConfigLoaderTest {

    @Test
    public void parseTest() {
        ConfigLoader loader = new ConfigLoader();

        Assert.assertSame(100, loader.parse("100"));
        Assert.assertTrue(loader.parse("1.77") instanceof Float);
        Assert.assertSame(true, loader.parse("true"));
        Assert.assertSame(false, loader.parse("false"));
        Assert.assertSame("test", loader.parse("test"));
    }

}
