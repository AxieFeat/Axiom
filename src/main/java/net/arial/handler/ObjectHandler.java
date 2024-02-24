package net.arial.handler;

import net.arial.Axiom;
import net.arial.adapter.TypeAdapter;
import net.arial.elements.ObjectUnit;
import net.arial.handler.layer.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

public final class ObjectHandler {

    private final ObjectPattern[] patterns = new ObjectPattern[]{
            new ObjectSeriesLayer(), new ObjectCollectionLayer(), new ObjectEnumerationLayer(), new ObjectPrimitiveLayer(),
            new ObjectMapLayer(), new ObjectRecordLayer(), new ObjectAssortmentLayer(),
    };

    public ObjectUnit write(Class<?> clazz, Object object) {
        if (object == null) {
            return new ObjectUnit.Null();
        }
        Optional<? extends TypeAdapter<?>> adapter = Axiom.getTypeAdapterFactory().getTypeAdapterPool().findIf(clazz);
        if (adapter.isPresent()) {
            return adapter.get().writeInstance(object);
        } else {
            var optional = Axiom.getObjectHandler().findPattern(clazz);
            if (optional.isEmpty()) {
                return new ObjectUnit.Null();
            }
            return optional.get().write(object);
        }
    }


    public Object read(Type type, Class<?> clazz, ObjectUnit unit) {
        if (unit instanceof ObjectUnit.Null) {
            return null;
        }
        Optional<? extends TypeAdapter<?>> adapter = Axiom.getTypeAdapterFactory().getTypeAdapterPool().findIf(clazz);
        if (adapter.isPresent()) {
            try {
                return adapter.get().readInstance(clazz, unit);
            } catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
        } else {
            Optional<ObjectPattern> optional = Axiom.getObjectHandler().findPattern(clazz);
            if (optional.isEmpty()) {
                return null;
            }
            return optional.get().read(type, clazz, unit);
        }
    }

    public Optional<ObjectPattern> findPattern(Class<?> clazz) {
        return Arrays.stream(this.patterns).filter(it -> it.isElement(clazz)).findFirst();
    }

    public ObjectPattern[] getPatterns() {
        return patterns;
    }
}