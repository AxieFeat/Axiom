package net.arial.handler.layer;

import net.arial.Axiom;
import net.arial.elements.ObjectAssortment;
import net.arial.elements.ObjectUnit;
import net.arial.handler.ObjectPattern;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class ObjectMapLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    @Override
    public ObjectUnit write(Object o) {
        var map = (Map<?, ?>) o;
        var assortment = new ObjectAssortment();
        map.forEach((o1, o2) -> assortment.append(o1.toString(), Axiom.getObjectHandler().write(o2.getClass(), o2)));
        return assortment;
    }

    @Override
    public Object read(Type type, Class<?> clazz, ObjectUnit unit) {
        var map = new HashMap<>();

        var typeCurrent = ((ParameterizedType) type).getActualTypeArguments()[1];
        var classCurrent = (Class<?>) typeCurrent;

        unit.assortment().getUnits().forEach((s, unit1) -> map.put(s, Axiom.getObjectHandler().read(typeCurrent, classCurrent, unit1)));
        return map;
    }
}
