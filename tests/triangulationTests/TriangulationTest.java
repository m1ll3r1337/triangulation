package triangulationTests;

import Model.Polygon;
import math.Vector3f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangulationTest {

    @Test
    void getTriangulatedModel() {


        Assertions.assertEquals();
    }

    @Test
    @DisplayName("triangulating some basic polygon")
    void basicPolyTriangulation() {
        List<List<Vector3f>> expectedList = new ArrayList<>();
        List<Vector3f> vertList = new ArrayList<>();
        vertList.add(new Vector3f(0,0,0));
        vertList.add(new Vector3f(1,0,0));
        vertList.add(new Vector3f(0,1,0));
        vertList.add(new Vector3f(1,1,0));
        triangulate(vertList);
        expectedList
    }

    @Test
    void getPolyVertMap() {
    }
}