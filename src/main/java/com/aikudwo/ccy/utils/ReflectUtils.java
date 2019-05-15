package com.aikudwo.ccy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class ReflectUtils {
    public static Map<String, Object> objectToMap(Object object) {
        return objectToMap(object, false);
    }

    public static Map<String, Object> objectToMap(Object object, boolean hasNull) {

        Class objectClass = object.getClass();
        List<Field> fields = new ArrayList<>();
        Class tempClass = objectClass;
        while (tempClass != null) {
            fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }
        HashMap<String, Object> map = new HashMap<>();
        try {
            for (Field field : fields) {
                if (Modifier.isFinal(field.getModifiers()))
                    continue;
                String name = field.getName();
                Object value = invokeGetMethod(object, name);
                if (hasNull || value != null)
                    map.put(name, value);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return map;

    }


    private static Object invokeGetMethod(Object obj, String fieldName) throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Class<?> objClass = obj.getClass();
        Method method = objClass.getMethod(fieldToGetter(fieldName));
        return method.invoke(obj);
    }

    private static String fieldToGetter(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private static String fieldToSetter(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
