package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
    // napisac equalsa oraz napisac test do equalans i po co bylo move direction
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean precedes(Vector2d other) {
        return x <= other.x && y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return x >= other.x && y >= other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(x, other.x), Math.max(y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(x, other.x), Math.min(y, other.y));
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }

    public boolean equals(Object other) //???
    {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vector2d)) {
            return false;
        }
        Vector2d v2 = (Vector2d) other;
        return this.x == v2.x && this.y == v2.y;

    }

    public int hashCode() { //zmiana hash codu, nie rozumien za bardzo po co
        return Objects.hash(this.x, this.y);
    }

    public Vector2d opposite() {
        return new Vector2d(x * -1, y * -1);
    }



}
