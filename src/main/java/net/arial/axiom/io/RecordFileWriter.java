package net.arial.axiom.io;

import net.arial.axiom.elements.ObjectAssortment;
import net.arial.axiom.elements.ObjectPrimitive;
import net.arial.axiom.elements.ObjectSeries;
import net.arial.axiom.elements.ObjectUnit;
import net.arial.axiom.reflections.AeonReflections;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class RecordFileWriter extends DistanceElement {

    private StringBuilder builder = new StringBuilder();

    public RecordFileWriter(ObjectUnit unit, Path path) {
        writeElement(null, unit, false);

        if (path == null) {
            return;
        }

        if (!Files.exists(path) && path.toFile().getParentFile() != null) {
            path.toFile().getParentFile().mkdirs();
        }

        try (var reader = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            reader.write(this.builder.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String getBuilderString() {
        return builder.toString();
    }

    private void writeElement(String key, ObjectUnit unit, boolean seriesElement) {
        if (unit.getComments() != null) {
            for (String comment : unit.getComments()) {
                builder.append(space()).append("# ").append(comment).append(NEXT_LINE);
            }
        }

        if (key == null && unit instanceof ObjectAssortment assortment && !seriesElement) {
            assortment.getUnits().forEach((s, unit1) -> writeElement(s, unit1, false));
        } else if (unit instanceof ObjectUnit.Null) {
            this.writePrimitive(new ObjectPrimitive(null), key, seriesElement);
        } else if (unit instanceof ObjectAssortment assortment) {
            this.writeAssortment(key, assortment, seriesElement);
        } else if (unit instanceof ObjectSeries series) {
            this.writeSeries(key, series);
        } else if (unit instanceof ObjectPrimitive primitive) {
            if (primitive.getValue() instanceof String && !primitive.getValue().toString().startsWith("\"") && !primitive.getValue().toString().endsWith("\"")) {
                primitive = new ObjectPrimitive("\"" + primitive.getValue() + "\"");
            }
            this.writePrimitive(primitive, key, seriesElement);
        } else throw new UnsupportedOperationException();
    }

    private void writeAssortment(String key, ObjectAssortment assortment, boolean seriesElement) {
        this.writeBlockElement(key, () -> assortment.getUnits().forEach((s, unit) -> writeElement(s, unit, false)), '[', ']', seriesElement);
    }

    private void writeSeries(String key, ObjectSeries series) {
        this.writeBlockElement(key, () -> {
            for (int i = 0; i < series.getUnits().size(); i++) {
                writeElement(null, series.getUnits().get(i), true);
                if (i < series.getUnits().size() - 1) {
                    this.builder.delete(this.builder.length() - 1, this.builder.length()).append(",\n");
                }
            }
        }, '{', '}', false);
    }

    private void writePrimitive(ObjectPrimitive primitive, String key, boolean seriesElement) {
        //if (primitive.getValue() instanceof String) {
         //   this.builder.append(space()).append(seriesElement ? AeonReflections.EMTPY_STRING : key + ": ").append("\"").append(primitive.getValue() == null ? null : primitive.getValue().toString().replaceAll("\n", "\\\\n")).append("\"").append(NEXT_LINE);
       // } else {
            this.builder.append(space()).append(seriesElement ? AeonReflections.EMTPY_STRING : key + ": ").append(primitive.getValue() == null ? null : primitive.getValue().toString().replaceAll("\n", "\\\\n")).append(NEXT_LINE);
        //}
    }

    private void writeBlockElement(String key, Runnable handle, char openSymbol, char closeSymbol, boolean seriesElement) {
        this.builder.append(space()).append(seriesElement ? AeonReflections.EMTPY_STRING : key + ": ").append(openSymbol).append(NEXT_LINE);
        this.blockSet(handle);
        this.builder.append(space()).append(closeSymbol).append(NEXT_LINE);
    }
}