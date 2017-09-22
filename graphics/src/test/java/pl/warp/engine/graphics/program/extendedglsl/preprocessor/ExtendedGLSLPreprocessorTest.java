package pl.warp.engine.graphics.program.extendedglsl.preprocessor;

import org.junit.Test;
import pl.warp.engine.graphics.program.extendedglsl.ProgramCompilationException;

import static org.junit.Assert.assertEquals;

/**
 * @author Jaca777
 * Created 2017-09-22 at 19
 */
public class ExtendedGLSLPreprocessorTest {
    @Test
    public void shouldProcessConstants() throws Exception {
        ConstantField constantField = new ConstantField()
                .set("foo", 2)
                .set("bar", 3);
        ExtendedGLSLPreprocessor preprocessor = new ExtendedGLSLPreprocessor(constantField, null);
        String sample = "foo bar \"$foo$,\n ab$bar$c\n\n $foo$";
        String result = preprocessor.preprocess(sample);
        String expectedResult = "foo bar \"2,\n ab3c\n\n 2";
        assertEquals(expectedResult, result);
    }

    @Test(expected = ProgramCompilationException.class)
    public void shouldFailIfConstantUnknown() throws Exception {
        ConstantField constantField = new ConstantField()
                .set("foo", 2)
                .set("bar", 3);
        ExtendedGLSLPreprocessor preprocessor = new ExtendedGLSLPreprocessor(constantField, null);
        String sample = "foo bar \"$abc$,\n ab$bar$c\n\n $foo$";
        preprocessor.preprocess(sample);
    }
}