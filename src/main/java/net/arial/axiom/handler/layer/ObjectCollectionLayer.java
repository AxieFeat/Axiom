package net.arial.axiom.handler.layer;

import net.arial.axiom.Axiom;
import net.arial.axiom.elements.ObjectSeries;
import net.arial.axiom.elements.ObjectUnit;
import net.arial.axiom.handler.ObjectPattern;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.stream.Collectors;

public final class ObjectCollectionLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    @Override
    public ObjectUnit write(Object o) {
        var series = new ObjectSeries();
        for (Object elements : (Collection<?>) o) {
            series.add(Axiom.getObjectHandler().write(elements.getClass(), elements));
        }
        return series;
    }

    @Override
    public Object read(Type type, Class<?> clazz, ObjectUnit unit) {
        if (!(unit instanceof ObjectSeries series)) throw new UnsupportedOperationException();
        return series.getUnits().stream().map(it -> Axiom.getObjectHandler().read(null, (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0], it)).collect(Collectors.toList());
    }
}
