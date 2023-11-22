import Model.Model;
import Model.Polygon;
import Model.TriPolyModel;
import Model.Tetrahedron;
import math.Vector3f;
import Model.Triangle3D;

import java.util.*;


public class DelaunayTriangulation {


    public static TriPolyModel getTriangulatedModel(Model model) {
        TriPolyModel triangulatedPolygonsModel = new TriPolyModel();
        triangulatedPolygonsModel.textureVertices = model.textureVertices;
        triangulatedPolygonsModel.vertices = model.vertices;
        HashMap<Polygon, ArrayList<Vector3f>> polyVertMap = new HashMap<>(getPolyVertMap(model));
        for (Map.Entry<Polygon, ArrayList<Vector3f>> entry : polyVertMap.entrySet()
        ) {
            Polygon polygon = entry.getKey();
            ArrayList<Vector3f> vertices = entry.getValue();
            List<Triangle3D> triangulatedPolygons = new ArrayList<>();
            triangulatedPolygons = triangulate(vertices);
            for (Triangle3D triangle : triangulatedPolygons
                 ) {
                Polygon triangulatedPolygon = new Polygon();
                ArrayList<Integer> polyVert = new ArrayList<>();
                polyVert.add(model.vertices.indexOf(triangle.getA()));
                polyVert.add(model.vertices.indexOf(triangle.getB()));
                polyVert.add(model.vertices.indexOf(triangle.getC()));
                triangulatedPolygon.setVertexIndices(polyVert);
                triangulatedPolygon.setNormalIndices(entry.getKey().getNormalIndices());
                triangulatedPolygon.setTextureVertexIndices(entry.getKey().getTextureVertexIndices());
                triangulatedPolygonsModel.polygons.add(triangulatedPolygon);
            }





        }
        return triangulatedPolygonsModel;
    }

    // Function to perform 3D Delaunay triangulation
    public static List<Triangle3D> triangulate(List<Vector3f> points) {
        // Add an initial tetrahedron to cover all points or first 4 points to start
        List<Triangle3D> polygons = new ArrayList<>();
        // Create a super tetrahedron that bounds all the points
        Tetrahedron superTetrahedron = createSuperTetrahedron(points);

        // Initialize triangulation with the super tetrahedron
        List<Tetrahedron> tetrahedrons = new ArrayList<>();
        tetrahedrons.add(superTetrahedron);
        ArrayList<Triangle3D> triangles = new ArrayList<>();

        // Iteratively add points to the triangulation
        for (Vector3f point : points) {
            Set<Tetrahedron> badTetrahedrons = new HashSet<>();

            // Find all triangles that are affected by the new point
            for (Tetrahedron tetrahedron : tetrahedrons) {
                if (tetrahedron.isPointInsideCircumsphere(point)) {
                    badTetrahedrons.add(tetrahedron);
                    triangles.addAll(tetrahedron.getFaces());
                }
            }

//            // Get the boundary edges of the affected triangles
//            Set<Edge3D> boundaryEdges = getBoundaryEdges(badTetrahedrons);

            // Remove the bad triangles from the triangulation
            tetrahedrons.removeAll(badTetrahedrons);

            // Add new triangles formed by connecting the point with boundary edges
//            for (Edge3D edge : boundaryEdges) {
//                Triangle3D newTriangle = new Triangle3D(edge.getStart(), edge.getEnd(), point);
//                tetrahedron.add(newTriangle);
//            }
//        }
            ArrayList<Triangle3D> badTriangles = new ArrayList<>();
            for (Triangle3D e1 : triangles) {
                for (Triangle3D e2 : triangles) {
                    if (e1 == e2) {
                        continue;
                    }
                    if (e1.equals(e2)) {
                        badTriangles.add(e1);
                        badTriangles.add(e2);
                    }
                }
            }
            triangles.removeAll(badTriangles);
            // Remove triangles that belong to the super tetrahedron
            tetrahedrons.remove(superTetrahedron);

            for (Tetrahedron tetrahedron : tetrahedrons) {
                polygons.addAll(tetrahedron.getFaces());
            }

        }
        return polygons;
    }

    // Function to create a super tetrahedron containing all points
    private static Tetrahedron createSuperTetrahedron(List<Vector3f> points) {

        // Logic to create a tetrahedron containing all points
        // This could be a bounding box or another method to enclose all the points
        // Return the initial tetrahedron
        float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE, minZ = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE, maxZ = Float.MIN_VALUE;

        for (Vector3f point : points) {
            minX = Math.min(minX, point.getX());
            minY = Math.min(minY, point.getY());
            minZ = Math.min(minZ, point.getZ());

            maxX = Math.max(maxX, point.getX());
            maxY = Math.max(maxY, point.getY());
            maxZ = Math.max(maxZ, point.getZ());
        }

        // Create vertices of the tetrahedron using extreme coordinates
        Vector3f v1 = new Vector3f(minX, minY, minZ);
        Vector3f v2 = new Vector3f(minX, maxY, maxZ);
        Vector3f v3 = new Vector3f(maxX, minY, maxZ);
        Vector3f v4 = new Vector3f(maxX, maxY, minZ);
        Triangle3D face1 = new Triangle3D(v1,v2,v3);
        Triangle3D face2 = new Triangle3D(v1, v2, v3);
        Triangle3D face3 = new Triangle3D(v1, v2, v3);
        Triangle3D face4 = new Triangle3D(v1, v2, v3);
        ArrayList<Triangle3D> faces = new ArrayList<>();
        faces.add(face1);
        faces.add(face2);
        faces.add(face3);
        faces.add(face4);
        // Create and return a tetrahedron with the found vertices
        return new Tetrahedron(faces, v1,v2,v3,v4);
    }

    // Function to get the boundary edges from a set of triangles
//    private Set<Edge3D> getBoundaryEdges(Set<Triangle3D> triangles) {
//        // Logic to extract boundary edges from the set of triangles
//        // Return the boundary edges
//    }
//
//    // Function to remove triangles that belong to the super tetrahedron
//    private void removeSuperTetrahedronTriangles(List<Triangle3D> triangulation) {
//        // Logic to remove triangles that belong to the super tetrahedron
//    }


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



    // function to sort the points by x-coordinate

}
