package agh.ics.oop.Enums;

import agh.ics.oop.Vector2d;

public enum MapDirection {

    NORTH("Północ", new Vector2d(0, 1)),
    NORTHEAST("Północny-Wschód", new Vector2d(1, 1)),
    EAST("Wschód", new Vector2d(1, 0)),
    SOUTHEAST("Południowy-Wschód", new Vector2d(1, -1)),
    SOUTH("Południe", new Vector2d(0, -1)),
    SOUTHWEST("Południwy-Zachód", new Vector2d(-1, -1)),
    WEST("Zachód", new Vector2d(-1, 0)),
    NORTHWEST("Północny-Zachód", new Vector2d(-1, 1));


    private final String directionName;
    private final Vector2d defaultVec;

    MapDirection(String kierunek, Vector2d vec) { // polglish
        this.directionName = kierunek;
        this.defaultVec = vec;
    }

    public String toString() {
        return this.directionName;
    }

    public Vector2d toUnitVector() {
        return this.defaultVec;
    }

    public MapDirection orientationRotationFromInt(int rotateAngle) {

        MapDirection returnValue = this;
        for (int i = 0; i < rotateAngle; i++) {
            returnValue = returnValue.next();
        }

        return returnValue;
    }

    public MapDirection random(int random) {

        return switch (random) { // .values()
            case 0 -> NORTHEAST;
            case 1 -> EAST;
            case 2 -> SOUTHEAST;
            case 3 -> SOUTH;
            case 4 -> SOUTHWEST;
            case 5 -> WEST;
            case 6 -> NORTHWEST;
            default -> NORTH;
        };
    }

    private MapDirection next() {

        return switch (this) {
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST -> NORTHWEST;
            case NORTHWEST -> NORTH;
        };
    }
}
