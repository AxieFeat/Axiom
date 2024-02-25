package net.arial.axiom.io;

import net.arial.axiom.elements.ObjectAssortment;
import net.arial.axiom.elements.ObjectPrimitive;
import net.arial.axiom.elements.ObjectSeries;
import net.arial.axiom.elements.ObjectUnit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public final class RecordFileReader extends DistanceElement {


    private final ObjectAssortment objectAssortment = new ObjectAssortment();

    public RecordFileReader(Path path) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path).stream().map(String::trim).filter(it -> !(it.isEmpty() || it.startsWith("#"))).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (var index = 0; index < lines.size(); index++) {
            index += readElement(lines.subList(index, lines.size()), objectAssortment);
        }
    }

    public RecordFileReader(String str) {
        String[] split = str.split("\n");
        var lines = Arrays.asList(split).stream().map(String::trim).filter(it -> !(it.isEmpty() || it.startsWith("#"))).toList();

        for (var index = 0; index < lines.size(); index++) {
            index += readElement(lines.subList(index, lines.size()), objectAssortment);
        }
    }

    private int readElement(List<String> lines, ObjectUnit unit) {
        var line = lines.get(0);
        if (line.contains(": [")) {
            return this.readAssortment(lines.subList(1, lines.size()), unit, line.split(": ")[0]);
        } else if (line.contains(": {")) {
            return this.readSeries(lines.subList(1, lines.size()), unit, line.split(": ")[0]);
        } else if (line.contains(": ")) {
            return this.readPrimitive(unit, line.split(": "), line);
        } else {
            throw new UnsupportedOperationException("Element: " + line);
        }
    }

    private int readPrimitive(ObjectUnit unit, String[] elements, String line) {
        String[] split = line.split(": ");

        if (split[1].startsWith("\"") && split[1].endsWith("\"")) {
            line = line.replace(split[1], removeFirstAndLastChar(split[1]));
        }

        for (int i = 0; i < elements.length; i++) {
            if (elements[i].startsWith("\"") && elements[i].endsWith("\"")) {
                elements[i] = removeFirstAndLastChar(elements[i]);
            }
        }

        if (!(unit instanceof ObjectAssortment assortment)) return 1;
        assortment.append(elements[0], new ObjectPrimitive(line.substring(elements[0].length() + 2)));
        return 0;
    }

    private String removeFirstAndLastChar(String str) {
        if (str != null && str.length() > 1) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

    private String removeLastChar(String str) {
        if (str != null && str.length() > 0) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }

    private int readAssortment(List<String> lines, ObjectUnit unit, String key) {
        var id = 0;
        var instance = new ObjectAssortment();
        for (id = 0; id < lines.size(); id++) {
            if (lines.get(id).equals("]") || lines.get(id).equals("],")) break;
            id += readElement(lines.subList(id, lines.size()), instance);
        }
        this.add(unit, key, instance);
        return ++id;
    }

    private int readSeries(List<String> lines, ObjectUnit unit, String key) {
        var id = 0;
        var series = new ObjectSeries();
        for (id = 0; id < lines.size(); id++) {
            var line = lines.get(id);
            if (line.contains("}")) break;
            if (line.contains("[")) {
                id += readAssortment(lines.subList(id + 1, lines.size()), series, null);
            } else {
                if (line.endsWith(",")) {
                    if (line.startsWith("\"") && line.endsWith("\",")) {
                        line = removeFirstAndLastChar(removeLastChar(line)) + ",";
                    }
                } else {
                    if (line.startsWith("\"") && line.endsWith("\"")) {
                        line = removeFirstAndLastChar(line);
                    }
                }

                series.add(new ObjectPrimitive(line.substring(0, line.endsWith(",") ? line.length() - 1 : line.length())));
            }
        }
        this.add(unit, key, series);
        return ++id;
    }

    private void add(ObjectUnit unit, String key, ObjectUnit instance) {
        if (unit instanceof ObjectAssortment assortment) {
            assortment.append(key, instance);
        } else if (unit instanceof ObjectSeries series) {
            series.add(instance);
        }
    }

    public ObjectAssortment getObjectAssortment() {
        return objectAssortment;
    }
}