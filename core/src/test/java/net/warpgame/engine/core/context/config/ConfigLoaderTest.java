package net.warpgame.engine.core.context.config;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author KocproZ
 * Created 2018-07-31 at 18:36
 */
public class ConfigLoaderTest {

    @Test
    public void parseShouldReturnInteger() {
        Assert.assertSame(100, new ConfigLoader().parse("100"));
    }

    @Test
    public void parseShouldReturnFloat() {
        Assert.assertSame(Float.class, new ConfigLoader().parse("1.1").getClass());
    }

    @Test
    public void parseShouldReturnTrue() {
        Assert.assertSame(true, new ConfigLoader().parse("true"));
    }

    @Test
    public void parseShouldReturnFalse() {
        Assert.assertSame(false, new ConfigLoader().parse("false"));
    }

    @Test
    public void parseShouldReturnString() {
        Assert.assertSame("127.0.0.1", new ConfigLoader().parse("127.0.0.1"));
    }

}
