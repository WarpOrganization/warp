package pl.warp.engine.graphics.program.extendedglsl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jaca777
 * Created 2017-09-22 at 19
 */
public class ProgramOutputResolver {

    private static final Pattern OUTPUT_VAR_PATTERN = Pattern.compile("layout\\(location = (\\d*)\\) out \\w* (\\w*);");

    public static String[] getOutput(String fragmentShaderCode) {
        Matcher matcher = OUTPUT_VAR_PATTERN.matcher(fragmentShaderCode);
        int matchesNumber = countMatches(matcher);
        String[] outputNames = new String[matchesNumber];
        while (matcher.find()) {
            int index = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2);
            checkOutputVariable(index, outputNames, matchesNumber);
            outputNames[index] = name;
        }
        return outputNames;
    }

    private static void checkOutputVariable(int index, String[] outputNames, int matchesNumber) {
        if (index >= matchesNumber)
            throw new ProgramCompilationException("Fragment shader contains a output variable with an unexpected location.");
        if (outputNames[index] != null)
            throw new ProgramCompilationException("Locations of output variables are not unique in the fragment shader.");
    }

    private static int countMatches(Matcher matcher) {
        int count = 0;
        while (matcher.find())
            count++;
        matcher.reset();
        return count;
    }
}
