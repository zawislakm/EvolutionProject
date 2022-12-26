package agh.ics.oop.PlantGrowType;

import agh.ics.oop.PlantGrowType.IPlantGrowType;
import agh.ics.oop.Vector2d;
import agh.ics.oop.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class ToxicBodies implements IPlantGrowType {

    public List<Vector2d> pickPreferredPositions(WorldMap worldMap) {

        List<Vector2d> preferredPositions = new ArrayList<>();

        int iterator = 0;
        int iterTo = (int) (Math.ceil((preferredPositions.size() * 20.0) / 100.0));

        //picks 20% of places as preferred
        for (Vector2d key : worldMap.sortedDeadAnimalCountDesc.keySet()) {
            preferredPositions.add(key);
            iterator++;
            if (iterator > iterTo) {
                break;
            }
        }

        return preferredPositions;
    }


}
