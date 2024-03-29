package net.arial.axiom.reflections;

import net.arial.axiom.annotations.Options;

import java.nio.file.Files;
import java.nio.file.Path;

public final class AeonPathFinder {

    public static Path find(Object value) {
        var path = value.getClass().getSimpleName();
        if(value.getClass().isAnnotationPresent(Options.class)) {
            var opt = value.getClass().getAnnotation(Options.class);
            path = (opt.path().isEmpty() ? AeonReflections.EMTPY_STRING : opt.path()) + (opt.name().isEmpty() ? value.getClass().getSimpleName() : opt.name());
        }
        return Path.of(path);
    }

    public static boolean isPresent(Object value) {
        return Files.exists(find(value));
    }
}
