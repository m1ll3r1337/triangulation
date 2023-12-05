package Triangulation;

import Model.Model;
import Model.TriPolyModel;
import Model.Polygon;
import math.Vector3f;
import Model.Triangle3D;

import java.util.*;

public class Triangulation {


    public static TriPolyModel getTriangulatedModel(Model model) {
        TriPolyModel triangulatedPolygonsModel = new TriPolyModel();
        triangulatedPolygonsModel.textureVertices = model.textureVertices;
        triangulatedPolygonsModel.vertices = model.vertices;
        HashMap<Polygon, ArrayList<Vector3f>> polyVertMap = new HashMap<>(getPolyVertMap(model));
        for (Map.Entry<Polygon, ArrayList<Vector3f>> entry : polyVertMap.entrySet()
        ) {
            Polygon polygon = entry.getKey();
            ArrayList<Vector3f> vertices = entry.getValue();
            List<List<Vector3f>> triangulatedPolygons;
            triangulatedPolygons = triangulate(vertices);
            for (List<Vector3f> triangle : triangulatedPolygons
            ) {
                Polygon triangulatedPolygon = new Polygon();
                ArrayList<Integer> polyVert = new ArrayList<>();
                polyVert.add(model.vertices.indexOf(triangle.get(0))+1);
                polyVert.add(model.vertices.indexOf(triangle.get(1))+1);
                polyVert.add(model.vertices.indexOf(triangle.get(2))+1);
                triangulatedPolygon.setVertexIndices(polyVert);
                triangulatedPolygon.setNormalIndices(entry.getKey().getNormalIndices());
                triangulatedPolygon.setTextureVertexIndices(entry.getKey().getTextureVertexIndices());
                triangulatedPolygonsModel.polygons.add(triangulatedPolygon);
            }


        }
        return triangulatedPolygonsModel;
    }

    public static List<List<Vector3f>> triangulate(List<Vector3f> vertList) {

        List<List<Vector3f>> triangulatedPolygons = new ArrayList<>();
        if (vertList.size() < 3){
            triangulatedPolygons.add(vertList);
            return triangulatedPolygons;
        }
        for(int i = 1; i < vertList.size()-1; i++){
            List<Vector3f> triangle = new ArrayList<>();
            triangle.add(vertList.get(0));
            triangle.add(vertList.get(i));
            triangle.add(vertList.get(i+1));
            triangulatedPolygons.add(triangle);
        }
        return triangulatedPolygons;
    }


    private static HashMap<Polygon, ArrayList<Vector3f>> getPolyVertMap(Model model) {
        HashMap<Polygon, ArrayList<Vector3f>> polyVertMap = new HashMap<>();
        ArrayList<Vector3f> polyVertices = new ArrayList<>();
        for (Polygon poly : model.polygons
        ) {
            for (Integer ind : poly.getVertexIndices()
            ) {
                polyVertices.add(model.vertices.get(ind));
            }
            polyVertMap.put(poly, polyVertices);
            polyVertices = new ArrayList<>();
        }
        return polyVertMap;
    }
}
