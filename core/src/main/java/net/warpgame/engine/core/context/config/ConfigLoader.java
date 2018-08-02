package net.warpgame.engine.core.context.config;

import net.warpgame.engine.core.context.service.Service;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Jaca777
 * Created 2017-09-23 at 17
 */
@Service
public class ConfigLoader {

    private static final Logger logger = Logger.getLogger(ConfigLoader.class);

    public static final String YML_CONFIG_LOCATION = "warp.yml";

    public void loadTo(Config config){
        logger.info("Loading config...");
        Map<String, Object> values = loadFromYaml();
        setValues(config, values, "");
        setValuesFromProperty(config);
        logger.info("Config loaded");
    }

    private void setValuesFromProperty(Config config) {
        String prop;
        for (String s : config.getValues().keySet()) {
            if ((prop = System.getProperty("conf." + s)) != null) {
                logger.info("Detected property: " + s + "=" + prop);
                config.setValue(s, parse(prop));
            }
        }
    }

    Object parse(String o) { //lol
        Scanner s = new Scanner(o);
        if (s.hasNextInt()) return s.nextInt();
        if (s.hasNextFloat()) return s.nextFloat();
        if (s.hasNextBoolean()) return s.nextBoolean();
        return o;
    }

    private void setValues(Config config, Map<String, Object> values, String acc) {
        for(Map.Entry<String, Object> entry : values.entrySet()) {

            Object value = entry.getValue();
            if(value instanceof Map<?, ?>){
                setValues(config, (Map<String, Object>) value, acc + entry.getKey() + ".");
            } else {
                config.setValue(acc + entry.getKey(), value);
            }
        }
    }

    public Map<String, Object> loadFromYaml(){
        InputStream stream = getClass().getClassLoader().getResourceAsStream(YML_CONFIG_LOCATION);
        if(stream == null) {
            logger.warn("Configuration file not found. Empty config loaded.");
            return new HashMap<>();
        } else {
            try {
                String yamlContent = IOUtils.toString(stream, Charset.defaultCharset());
                CustomClassLoaderConstructor constr = new CustomClassLoaderConstructor(
                        Thread.currentThread().getContextClassLoader());
                Yaml yaml = new Yaml(constr);
                Map<String, Object> map = (Map<String, Object>) yaml.load(yamlContent);
                logger.info("Loaded config file");
                return map;
            } catch (IOException e) {
                throw new ServiceConfigurationException(e);
            }

        }
    }
}
