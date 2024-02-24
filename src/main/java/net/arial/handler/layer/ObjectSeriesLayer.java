package net.arial.handler.layer;

import net.arial.Axiom;
import net.arial.elements.ObjectSeries;
import net.arial.elements.ObjectUnit;
import net.arial.handler.ObjectPattern;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

public final class ObjectSeriesLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return clazz.isArray();
    }

    @Override
    public ObjectUnit write(Object o) {
        var series = new ObjectSeries();
        for (var i = 0; i < Array.getLength(o); i++) {
            var element = Array.get(o, i);
            series.add(Axiom.getObjectHandler().write(element.getClass(), element));
        }
        return series;
    }

    @Override
    public Object read(Type type, Class<?> clazz, ObjectUnit unit) {
        if (!(unit instanceof ObjectSeries series)) throw new UnsupportedOperationException();
        var array = Array.newInstance(clazz.getComponentType(), series.getUnits().size());
        for (var i = 0; i < series.getUnits().size(); i++) {
            Array.set(array, i, Axiom.getObjectHandler().read(null, clazz.getComponentType(), series.getUnits().get(i)));
        }
        return array;
    }
}
