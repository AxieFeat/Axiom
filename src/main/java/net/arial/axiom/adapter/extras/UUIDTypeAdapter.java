package net.arial.axiom.adapter.extras;

import net.arial.axiom.adapter.TypeAdapter;
import net.arial.axiom.elements.ObjectPrimitive;
import net.arial.axiom.elements.ObjectUnit;

import java.util.UUID;

public final class UUIDTypeAdapter extends TypeAdapter<UUID> {

    @Override
    public ObjectUnit write(UUID value) {
        return new ObjectPrimitive(value.toString());
    }

    @Override
    public UUID read(Class<UUID> value, ObjectUnit unit) {
        return UUID.fromString(unit.primitives().getValue().toString());
    }
}
