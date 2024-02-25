package net.arial.axiom.elements;

import net.arial.axiom.Axiom;
import net.arial.axiom.reflections.AeonReflections;

public final class ObjectPrimitive extends ObjectUnit {


    private Object value;

    public ObjectPrimitive(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public String asString() {
        return value.toString();
    }

    public int asInt() {
        return Integer.parseInt(value.toString());
    }

    public boolean asBoolean() {
        return Boolean.parseBoolean(value.toString());
    }

    public double asDouble() {
        return Double.parseDouble(value.toString());
    }

    public short asShort() {
        return Short.parseShort(value.toString());
    }

    public float asFloat() {
        return Float.parseFloat(value.toString());
    }

    public <T> T as(Class<T> clazz) {
        if (clazz.isPrimitive() || AeonReflections.isDefaultElement(clazz)) {
            return (T) value;
        } else {
            return (T) Axiom.getObjectHandler().read(null, clazz, this);
        }
    }
}
