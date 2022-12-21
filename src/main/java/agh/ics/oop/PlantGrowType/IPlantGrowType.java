package agh.ics.oop.PlantGrowType;

import agh.ics.oop.Vector2d;
import agh.ics.oop.WorldMap;

import java.util.List;
import java.util.TreeMap;

public interface IPlantGrowType {

    List<Vector2d> pickPreferredPositions(WorldMap worldMap);

}
