package net.arial.axiom.handler;

import net.arial.axiom.elements.ObjectUnit;

import java.lang.reflect.Type;

public interface ObjectPattern {

    boolean isElement(Class<?> clazz);

    ObjectUnit write(Object o);

    Object read(Type type, Class<?> clazz, ObjectUnit unit);

    default Object readCaughtException(Exception exception) {
        exception.printStackTrace();
        return null;
    }
}
