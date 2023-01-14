package agh.ics.oop.NextGenType;

import agh.ics.oop.NextGenType.INextGenType;
import agh.ics.oop.WorldMap;

public class ExpectedNextGen implements INextGenType {

    public int nextGen(WorldMap worldMap, int whichGenNow) {
        return ((whichGenNow + 1) % worldMap.genomLength);
    }
}
