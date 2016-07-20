package pl.warp.engine.graphics.shader.extendedglsl;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 18
 */
public class ExtendedGLSLProgramCompilerTest {
    @Test
    public void shouldProcessConstants() throws Exception {
        ConstantField constantField = new ConstantField()
                .set("foo", 2)
                .set("bar", 3);
        ExtendedGLSLProgramCompiler compiler = new ExtendedGLSLProgramCompiler(null, null, constantField);
        String sample = "foo bar \"$foo$,\n ab$bar$c\n\n $foo$";
        String result = compiler.processConstants(sample);
        String expectedResult = "foo bar \"2,\n ab3c\n\n 2";
        assertEquals(expectedResult, result);
    }

    @Test(expected = ProgramCompilationException.class)
    public void shouldFailIfConstantUnknown() throws Exception {
        ConstantField constantField = new ConstantField()
                .set("foo", 2)
                .set("bar", 3);
        ExtendedGLSLProgramCompiler compiler = new ExtendedGLSLProgramCompiler(null, null, constantField);
        String sample = "foo bar \"$abc$,\n ab$bar$c\n\n $foo$";
        compiler.processConstants(sample);
    }

    private static final String SOME_GLSL_CODE =
            "#version 330\n" +
                    "precision mediump float;\n" +
                    "\n" +
                    "uniform samplerCube cube;\n" +
                    "in vec3 vTexCoord;\n" +
                    "\n" +
                    "layout(location = 1) out vec4 someColor;\n" +
                    "layout(location = 0) out vec4 fragColor;\n" +
                    "\n" +
                    "void main(void) {\n" +
                    "    fragColor = texture(cube, vTexCoord);\n" +
                    "}";

    @Test
    public void shouldRecognizeOutput() throws Exception {
        ExtendedGLSLProgramCompiler compiler = new ExtendedGLSLProgramCompiler(null, null, null);
        String[] output = compiler.getOutput(SOME_GLSL_CODE);
        String[] expectedOutput = {"fragColor", "someColor"};
        assertArrayEquals(expectedOutput, output);
    }

    private static final String SOME_STRANGE_GLSL_CODE =
            "#version 330\n" +
                    "precision mediump float;\n" +
                    "\n" +
                    "uniform samplerCube cube;\n" +
                    "in vec3 vTexCoord;\n" +
                    "\n" +
                    "layout(location = 2) out vec4 someColor;\n" +
                    "layout(location = 0) out vec4 fragColor;\n" +
                    "\n" +
                    "void main(void) {\n" +
                    "    fragColor = texture(cube, vTexCoord);\n" +
                    "}";

    @Test(expected = ProgramCompilationException.class)
    public void shouldFailIfLocationUnexpected() throws Exception {
        ExtendedGLSLProgramCompiler compiler = new ExtendedGLSLProgramCompiler(null, null, null);
        compiler.getOutput(SOME_STRANGE_GLSL_CODE);
    }

    private static final String YET_ANOTHER_STRANGE_GLSL_CODE =
            "#version 330\n" +
                    "precision mediump float;\n" +
                    "\n" +
                    "uniform samplerCube cube;\n" +
                    "in vec3 vTexCoord;\n" +
                    "\n" +
                    "layout(location = 0) out vec4 someColor;\n" +
                    "layout(location = 0) out vec4 fragColor;\n" +
                    "\n" +
                    "void main(void) {\n" +
                    "    fragColor = texture(cube, vTexCoord);\n" +
                    "}";

    @Test(expected = ProgramCompilationException.class)
    public void shouldFailIfLocationDuplicated() throws Exception {
        ExtendedGLSLProgramCompiler compiler = new ExtendedGLSLProgramCompiler(null, null, null);
        compiler.getOutput(YET_ANOTHER_STRANGE_GLSL_CODE);
    }

}