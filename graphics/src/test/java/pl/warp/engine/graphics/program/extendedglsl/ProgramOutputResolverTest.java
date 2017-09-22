package pl.warp.engine.graphics.program.extendedglsl;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Jaca777
 * Created 2017-09-22 at 19
 */
public class ProgramOutputResolverTest {
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
        String[] output = ProgramOutputResolver.getOutput(SOME_GLSL_CODE);
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
        ProgramOutputResolver.getOutput(SOME_STRANGE_GLSL_CODE);
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
        ProgramOutputResolver.getOutput(YET_ANOTHER_STRANGE_GLSL_CODE);
    }


}