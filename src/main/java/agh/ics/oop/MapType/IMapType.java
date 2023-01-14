package agh.ics.oop.MapType;

import agh.ics.oop.Animal;
import agh.ics.oop.Vector2d;
import agh.ics.oop.WorldMap;

public interface IMapType {
    Vector2d mapSpecyfication(Animal animal, Vector2d lowerLeft, Vector2d upperRight, Vector2d newPosition, int breedCost); // nieczytelna nazwa

}
