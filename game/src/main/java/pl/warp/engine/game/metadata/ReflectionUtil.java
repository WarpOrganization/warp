package pl.warp.engine.game.metadata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by user on 2017-01-19.
 */
public class ReflectionUtil {
    public static List<Field> getAllFieldsToSuperclass(Class<?> startClass, Class<?> limitSuperclass) {
        List<Field> fields = new ArrayList<>();
        try {
            for (Class<?> currentClass = startClass; currentClass != limitSuperclass; currentClass = currentClass.getSuperclass())
                addFields(fields, currentClass);
        } catch (NullPointerException e) {
            throw new RuntimeException("limitSuperclass is not a superclass of startClass.", e);
        }
        return fields;
    }

    private static void addFields(List<Field> fields, Class<?> currentClass) {
        Collections.addAll(fields, currentClass.getDeclaredFields());
    }
}
