package net.arial;

import net.arial.adapter.TypeAdapterFactory;
import net.arial.annotations.Options;
import net.arial.handler.ObjectHandler;
import net.arial.io.RecordFileReader;
import net.arial.io.RecordFileWriter;
import net.arial.reflections.AeonPathFinder;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("ALL")
public final class Axiom {


    private static final ObjectHandler objectHandler = new ObjectHandler();

    public static ObjectHandler getObjectHandler() {
        return objectHandler;
    }

    private static final TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactory();

    public static TypeAdapterFactory getTypeAdapterFactory() {
        return typeAdapterFactory;
    }

    public static <T> T insert(@NotNull T value, Path path) {
        if (value.getClass().isAnnotationPresent(Options.class)) {
            Options options = value.getClass().getDeclaredAnnotation(Options.class);
            if (options.name().length() > 0) {
                path = path.resolve(Path.of(options.name()));
            }
        }
        path = Path.of(path + ".ax");

        if (Files.exists(path)) {
            var element = (T) objectHandler.read(null, value.getClass(), new RecordFileReader(path).getObjectAssortment());
            //overwrite existing property
            new RecordFileWriter(objectHandler.write(value.getClass(), element), path);
            return element;
        }
        new RecordFileWriter(objectHandler.write(value.getClass(), value), path);
        return value;
    }

    public static <T> T fromString(@NotNull String str, @NotNull Class clazz) {
        return (T) objectHandler.read(null, clazz, new RecordFileReader(str).getObjectAssortment());
    }

    public static String toString(@NotNull Object value) {
        return new RecordFileWriter(objectHandler.write(value.getClass(), value), null).getBuilderString();
    }


    public static <T> T insert(@NotNull T value) {
        return insert(value, AeonPathFinder.find(value));
    }

}
