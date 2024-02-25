package net.arial.axiom.handler.layer;

import net.arial.axiom.elements.ObjectPrimitive;
import net.arial.axiom.elements.ObjectUnit;
import net.arial.axiom.handler.ObjectPattern;
import net.arial.axiom.reflections.AeonReflections;

import java.beans.PropertyEditorManager;
import java.lang.reflect.Type;

public final class ObjectPrimitiveLayer implements ObjectPattern {

    @Override
    public boolean isElement(Class<?> clazz) {
        return clazz.isPrimitive() || AeonReflections.isDefaultElement(clazz);
    }

    @Override
    public ObjectUnit write(Object value) {
        return new ObjectPrimitive(value);
    }

    @Override
    public Object read(Type type, Class<?> clazz, ObjectUnit unit) {
        if (!(unit instanceof ObjectPrimitive primitive)) {
            throw new UnsupportedOperationException("This is not a correct primitive type.");
        }
        var editor = PropertyEditorManager.findEditor(clazz);
        editor.setAsText(primitive.getValue().toString());
        return editor.getValue();
    }
}
