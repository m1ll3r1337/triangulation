package Triangulation;

import math.Vector3f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TriangulationTest {
    void assertVectorListsEqual(List<Vector3f> expected, List<Vector3f> actual) {
        Assertions.assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            Vector3f expectedVector = expected.get(i);
            Vector3f actualVector = actual.get(i);

            Assertions.assertEquals(expectedVector.getX(), actualVector.getX());
            Assertions.assertEquals(expectedVector.getY(), actualVector.getY());
            Assertions.assertEquals(expectedVector.getZ(), actualVector.getZ());
        }
    }


    @Test
    void getTriangulatedModel() {
    }

    @Test
    @DisplayName("Simple polygon triangulation")
    void simplePolyTriangulation() {

        List<Vector3f> vertList = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 1, 0),
                new Vector3f(0, 1, 0)
        ));

        List<Vector3f> resultingSublist1 = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 1, 0)

        ));
        List<Vector3f> resultingSublist2 = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 1, 0),
                new Vector3f(0, 1, 0)
        ));

        List<List<Vector3f>> resultingList = new ArrayList<>(Arrays.asList(
               resultingSublist1, resultingSublist2
        ));
        List<List<Vector3f>> actualList = Triangulation.triangulate(vertList);
        Assertions.assertEquals(resultingList.size(), actualList.size());

        for (int i = 0; i < resultingList.size(); i++) {
            assertVectorListsEqual(resultingList.get(i), actualList.get(i));
        }

    }
    @Test
    @DisplayName("Empty list passed as an argument")
    void emptyListTriangulation() {
        List<Vector3f> vertList = new ArrayList<>();

        List<Vector3f> resultingSublist1 = new ArrayList<>();


        List<List<Vector3f>> resultingList = new ArrayList<>(List.of(
                resultingSublist1
        ));
        List<List<Vector3f>> actualList = Triangulation.triangulate(vertList);
        Assertions.assertEquals(resultingList.size(), actualList.size());
        Assertions.assertEquals(1, actualList.size());
        Assertions.assertEquals(0, actualList.get(0).size());

    }
    @Test
    @DisplayName("Incorrect polygon vertices amount triangulation")
    void incVertTriangulation() {

        List<Vector3f> vertList = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0)
        ));

        List<Vector3f> resultingSublist1 = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0)

        ));

        List<List<Vector3f>> resultingList = new ArrayList<>(List.of(
                resultingSublist1
        ));
        List<List<Vector3f>> actualList = Triangulation.triangulate(vertList);
        Assertions.assertEquals(resultingList.size(), actualList.size());

        for (int i = 0; i < resultingList.size(); i++) {
            assertVectorListsEqual(resultingList.get(i), actualList.get(i));
        }

    }

    @Test
    void getPolyVertMap() {
    }
}