package agh.ics.oop;

import agh.ics.oop.Enums.MapDirection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class MapDirectionTest {


    @Test
    void orientationRotationFromIntTest(){
        assertEquals(MapDirection.NORTH.orientationRotationFromInt(7),MapDirection.NORTHWEST);
        assertEquals(MapDirection.EAST.orientationRotationFromInt(2),MapDirection.SOUTH);
        assertEquals(MapDirection.SOUTHWEST.orientationRotationFromInt(1),MapDirection.WEST);
        assertEquals(MapDirection.NORTHWEST.orientationRotationFromInt(0),MapDirection.NORTHWEST);
    }
}
