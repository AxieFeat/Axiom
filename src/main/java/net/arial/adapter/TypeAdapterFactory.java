package net.arial.adapter;

import net.arial.adapter.extras.UUIDTypeAdapter;

public final class TypeAdapterFactory {


    private final TypeAdapterPool typeAdapterPool = new TypeAdapterPool();

    public TypeAdapterPool getTypeAdapterPool() {
        return typeAdapterPool;
    }

    public TypeAdapterFactory() {
        this.typeAdapterPool.registerTypeAdapter(new UUIDTypeAdapter());
    }
}
