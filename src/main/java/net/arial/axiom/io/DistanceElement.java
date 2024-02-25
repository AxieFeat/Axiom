package net.arial.axiom.io;

public abstract class DistanceElement {

    private int distance = 0;
    public String NEXT_LINE = "\n";

    public String space() {
        return " ".repeat(distance * 3);
    }

    public void expand() {
        distance++;
    }

    public void reduce() {
        distance--;
    }

    public void blockSet(Runnable runnable) {
        this.expand();
        runnable.run();
        this.reduce();
    }
}
