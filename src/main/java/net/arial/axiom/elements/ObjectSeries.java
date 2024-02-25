package net.arial.axiom.elements;

import java.util.ArrayList;
import java.util.List;

public final class ObjectSeries extends ObjectUnit {

    private final List<ObjectUnit> units = new ArrayList<>();

    public void add(ObjectUnit unit) {
        this.units.add(unit);
    }

    public List<ObjectUnit> getUnits() {
        return units;
    }
}
