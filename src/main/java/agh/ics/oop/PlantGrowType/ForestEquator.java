package agh.ics.oop.PlantGrowType;

import agh.ics.oop.PlantGrowType.IPlantGrowType;
import agh.ics.oop.Vector2d;
import agh.ics.oop.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ForestEquator implements IPlantGrowType {

    public List<Vector2d> pickPreferredPositions(WorldMap worldMap ){

        Vector2d upperRight = worldMap.getUpperRight();
        int width = upperRight.x +1;
        int height = upperRight.y +1 ;

        List<Vector2d> preferredPositions = new ArrayList<>();
        int xCentre = width/2;
        int yCentre = height/2;

        int quantityPreferredPosition = (int) (Math.floor((width*height*20)/100.0));
        preferredPositions.add(new Vector2d(xCentre,yCentre));
        int used = 1;

        int xMove = 1;
        while (used < quantityPreferredPosition){

            Vector2d newLeft = new Vector2d(xCentre-xMove,yCentre);
            if (worldMap.isInBorder(newLeft)){
                preferredPositions.add(newLeft);
                used+=1;
            }else {
                break;
            }
            Vector2d newRight = new Vector2d(xCentre+xMove,yCentre);

            if (worldMap.isInBorder(newRight)){
                preferredPositions.add(newRight);
                used+=1;
            } else {
                break;
            }
            xMove = xMove +1;
        }


        int yMove = 1;
        while (used < quantityPreferredPosition){

            Vector2d newCenterUp = new Vector2d(xCentre,yCentre+yMove);
            Vector2d newCenterDown = new Vector2d(xCentre,yCentre-yMove);
            preferredPositions.add(newCenterUp);
            preferredPositions.add(newCenterDown);
            used += 2;
            int xMoveV2 = 1;

            while (used < quantityPreferredPosition){
                Vector2d newUpperLeft = new Vector2d(xCentre-xMoveV2,yCentre+yMove);
                Vector2d newUpperRight = new Vector2d(xCentre+xMoveV2,yCentre+yMove);
                Vector2d newLowerLeft = new Vector2d(xCentre-xMoveV2,yCentre-yMove);
                Vector2d newLowerRight = new Vector2d(xCentre+xMoveV2,yCentre-yMove);
                xMoveV2 +=1;

                if (worldMap.isInBorder(newUpperLeft)){
                    preferredPositions.add(newUpperLeft);
                    used+=1;
                }else {
                    break;
                }
                if (worldMap.isInBorder(newUpperRight)){
                    preferredPositions.add(newUpperRight);
                    used+=1;
                }else {
                    break;
                }
                if (worldMap.isInBorder(newLowerRight)){
                    preferredPositions.add(newLowerRight);
                    used+=1;
                }else {
                    break;
                }

                if (worldMap.isInBorder(newLowerLeft)){
                    preferredPositions.add(newLowerLeft);
                    used+=1;
                }else {
                    break;
                }
            }
            yMove ++;

        }


        return preferredPositions;

    }
}
