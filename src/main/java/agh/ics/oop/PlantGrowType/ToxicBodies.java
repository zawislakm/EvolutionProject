package agh.ics.oop.PlantGrowType;

import agh.ics.oop.PlantGrowType.IPlantGrowType;
import agh.ics.oop.Vector2d;
import agh.ics.oop.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class ToxicBodies implements IPlantGrowType {

    public List<Vector2d> pickPreferredPositions(WorldMap worldMap){
        List<Vector2d> preferredPositions = allPositions(worldMap);


        int iterator = 0; //if keySet have over 80% of allVectors
        int iterTo = (int) (Math.ceil((preferredPositions.size()*80.0)/100.0));
        for(Vector2d key: worldMap.sortedDeadAnimalCountDesc.keySet()){ //all keys from hashmap if below 80%
            preferredPositions.remove(key);
            iterator++;
            if (iterator>iterTo){
                break;
            }
        }
        return preferredPositions;
    }

    private List<Vector2d> allPositions(WorldMap worldMap){
        Vector2d upperRight = worldMap.getUpperRight();
        List<Vector2d> allPositions = new ArrayList<>();
        for (int x = 0; x <= upperRight.x; x++) {
            for (int y = 0; x <= upperRight.y; y++) {
                allPositions.add(new Vector2d(x, y));
            }
        }
        return allPositions;
    }
}
