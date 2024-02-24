package net.arial.handler.layer;

import net.arial.Axiom;
import net.arial.elements.ObjectAssortment;
import net.arial.elements.ObjectUnit;
import net.arial.handler.ObjectPattern;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public final class ObjectRecordLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return clazz.isRecord();
    }

    @Override
    public ObjectUnit write(Object value) {
        return ObjectAssortmentLayer.INSTANCE.write(value);
    }

    @Override
    public Object read(Type type, Class<?> clazz, ObjectUnit unit) {
        var types = Arrays.stream(clazz.getDeclaredFields()).map(Field::getType).toArray(value -> new Class<?>[value]);
        var typeObjects = new ArrayList<>();
        if (unit instanceof ObjectAssortment assortment) {
            Arrays.stream(clazz.getDeclaredFields()).forEach(it -> {
                if (assortment.get(it.getName()) != null) {
                    typeObjects.add(Axiom.getObjectHandler().read(it.getGenericType(), it.getType(), assortment.get(it.getName())));
                }
            });
        }
        try {
            return clazz.getDeclaredConstructor(types).newInstance(typeObjects.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
