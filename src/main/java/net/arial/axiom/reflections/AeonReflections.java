package net.arial.axiom.reflections;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;

public final class AeonReflections {

    @SuppressWarnings("sunapi")
    public static final Unsafe unsafe;
    public static final String EMTPY_STRING = "";
    public static final Class<?>[] elements = new Class<?>[]{String.class, Integer.class, Boolean.class, Short.class, Float.class, Byte.class, Double.class, Long.class};

    static {
        try {
            var field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static<T> T allocate(Class<T> tClass) {
        try {
            return (T) unsafe.allocateInstance(tClass);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void modify(Field field, Object object, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDefaultElement(Class<?> type) {
        return Arrays.asList(elements).contains(type);
    }

    public static Object get(Field field, Object object) {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
