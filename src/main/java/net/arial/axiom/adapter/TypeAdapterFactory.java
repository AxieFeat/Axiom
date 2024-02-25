package net.arial.axiom.adapter;

import net.arial.axiom.adapter.extras.UUIDTypeAdapter;

public final class TypeAdapterFactory {


    private final TypeAdapterPool typeAdapterPool = new TypeAdapterPool();

    public TypeAdapterPool getTypeAdapterPool() {
        return typeAdapterPool;
    }

    public TypeAdapterFactory() {
        this.typeAdapterPool.registerTypeAdapter(new UUIDTypeAdapter());
    }
}
