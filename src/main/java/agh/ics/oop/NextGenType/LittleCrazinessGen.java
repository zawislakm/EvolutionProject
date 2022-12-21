package agh.ics.oop.NextGenType;

import agh.ics.oop.NextGenType.INextGenType;
import agh.ics.oop.WorldMap;

public class LittleCrazinessGen implements INextGenType {


    public int nextGen(WorldMap worldMap, int whichGenNow){

        int random = (int )(Math.random() * 5 + 1);
        if (random == 1){
            int newGen = (int)(Math.random()*worldMap.genomLength+1) - 1;
            while (newGen == whichGenNow){
                newGen = (int)(Math.random()*worldMap.genomLength+1) - 1;
            }
            return newGen;
        }


        return (whichGenNow+1) % worldMap.genomLength;
    }
}
