package net.arial.axiom.handler.layer;

import net.arial.axiom.Axiom;
import net.arial.axiom.annotations.Comment;
import net.arial.axiom.reflections.AeonReflections;
import net.arial.axiom.elements.ObjectAssortment;
import net.arial.axiom.elements.ObjectUnit;
import net.arial.axiom.handler.ObjectPattern;

import java.lang.reflect.Type;
import java.util.Arrays;

public final class ObjectAssortmentLayer implements ObjectPattern {

    public static ObjectAssortmentLayer INSTANCE;

    public ObjectAssortmentLayer() {
        INSTANCE = this;
    }

    @Override
    public boolean isElement(Class<?> clazz) {
        return true;
    }

    @Override
    public ObjectUnit write(Object value) {
        var assortment = new ObjectAssortment();
        Arrays.stream(value.getClass().getDeclaredFields()).forEach(it -> {
            var unit = Axiom.getObjectHandler().write(it.getType(), AeonReflections.get(it, value));
            if (it.isAnnotationPresent(Comment.class)) {
                unit.setComments(it.getDeclaredAnnotation(Comment.class).comment());
            }
            assortment.append(it.getName(), unit);
        });
        return assortment;
    }

    @Override
    public Object read(Type type, Class<?> clazz, ObjectUnit unit) {
        var object = AeonReflections.allocate(clazz);
        if (unit instanceof ObjectAssortment assortment) {
            Arrays.stream(clazz.getDeclaredFields()).forEach(it -> {
                if (assortment.has(it.getName())) {
                    AeonReflections.modify(it, object, Axiom.getObjectHandler().read(it.getGenericType(), it.getType(), assortment.get(it.getName())));
                } else {
                    AeonReflections.modify(it, object, null);
                }
            });
        }
        return object;
    }
}
