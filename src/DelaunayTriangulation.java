import Model.Model;
import Model.Polygon;
import Model.TriPolyModel;
import math.Vector2f;
import math.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



public class DelaunayTriangulation {

    private static final double EPSILON = 1e-12;
    public static float crossProduct(Vector2f a, Vector2f b){
        return a.getX()*b.getX() - a.getY()*b.getY();
    }

    static class Edge {
        int a, b;

        Edge(int a, int b)
        {
            this.a = a;
            this.b = b;
        }
    }

    private static class Point {
        float x, y;

        Point(float x, float y)
        {
            this.x = x;
            this.y = y;
        }
    }
    public static TriPolyModel triangulate(Model model) {
        TriPolyModel result = new TriPolyModel();

        for(Map.Entry<Polygon,ArrayList<Vector3f>> entry : getPolyVertMap(model).entrySet()) {

            Polygon polygon = entry.getKey();
            ArrayList<Vector3f> vertices = entry.getValue();

            int n = vertices.size();

            List<Edge> edges = new ArrayList<>();


            // sorting the points by x-coordinate
           ArrayList<Vector2f> sorted = new ArrayList<>();
           TreeMap<Vector2f, Integer> vecOrderMap = new TreeMap<>();
            for (int i = 0; i < n; i++) {
                Vector2f vec2f = new Vector2f(vertices.get(i).getX(),(vertices.get(i).getY()));
                vecOrderMap.put(vec2f,i+1);
                sorted.set(i,vec2f);
            }
            sorted = sortByX(sorted, 0, n - 1);

            // creating the lower hull
            for (int i = 0; i < n; i++) {
                while (edges.size() >= 2) {
                    int j = edges.size() - 2;
                    int k = edges.size() - 1;
                    Vector2f A = sorted.get(edges.get(j).a);
                    Vector2f B = sorted.get(edges.get(j).b);
                    Vector2f C = sorted.get(edges.get(k).b);

                    if (crossProduct(
                            new Vector2f(B.getX() - A.getX(), B.getY() - A.getY()), // why 0 ??
                            new Vector2f(C.getX() - B.getX(), C.getY() - B.getY()))
                            > 0) {
                        break;
                    }

                    edges.remove(edges.size() - 1);
                }
                edges.add(new Edge(edges.size(), i));
            }
            int lower = edges.size();
            // creating the upper hull
            for (int i = n - 2, t = lower + 1; i >= 0; i--) {
                while (edges.size() >= t) {
                    int j = edges.size() - 2;
                    int k = edges.size() - 1;
                    Vector2f A = sorted.get(edges.get(j).a);
                    Vector2f B = sorted.get(edges.get(j).b);
                    Vector2f C = sorted.get(edges.get(k).b);

                    if (crossProduct(
                            new Vector2f(B.getX() - A.getX(), B.getY() - A.getY()), // why 0 ??
                            new Vector2f(C.getX() - B.getX(), C.getY() - B.getY()))
                            > 0) {
                        break;
                    }

                    edges.remove(edges.size() - 1);
                }
                edges.add(new Edge(i, edges.size()));
            }

            // removing the duplicate edges from the hull
            edges.remove(edges.size() - 1);

            // creating the triangulation
            ArrayList<Integer> newVerticesIndices = new ArrayList<Integer>();
            for (Edge edge : edges) {
                int a = edge.a;
                int b = edge.b;
                Vector2f A = sorted.get(a);
                Vector2f B = sorted.get(b);
                boolean flag = true;
                model.vertices.indexOf(A);


                for (int j = 0; j < n; j++) {
                    if (j == a || j == b) {
                        continue;
                    }
                    Vector2f P = sorted.get(j);
                    if (insideCircle(A, B, P,
                            sorted.get(a + b >> 1))) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    newVerticesIndices.add(vecOrderMap.get(A));
                    newVerticesIndices.add(vecOrderMap.get(B));
                }
            }


        }
        return result;
    }

    private static boolean insideCircle(Vector2f A, Vector2f B,
                                        Vector2f C, Vector2f P)
    {
        double ax = A.getX() - P.getX();
        double ay = A.getY() - P.getY();
        double bx = B.getX() - P.getX();
        double by = B.getY() - P.getY();
        double cx = C.getX() - P.getX();
        double cy = C.getY() - P.getY();

        double a2 = ax * ax + ay * ay;
        double b2 = bx * bx + by * by;
        double c2 = cx * cx + cy * cy;

        return (ax * (by - cy) + bx * (cy - ay)
                + cx * (ay - by))
                >= EPSILON;
    }

    private static TreeMap<Polygon, ArrayList<Vector3f>> getPolyVertMap(Model model) {
        TreeMap<Polygon, ArrayList<Vector3f>> polyVertMap = new TreeMap<>();
        ArrayList<Vector3f> polyVertices = new ArrayList<>();
        for (Polygon poly : model.polygons
        ) {
            for (Integer ind: poly.getVertexIndices()
                 ) {
                polyVertices.add(model.vertices.get(ind));
            }
            polyVertMap.put(poly, polyVertices);
            polyVertices.clear();
        }
       return polyVertMap;
    }

    // function to sort the points by x-coordinate
    private static ArrayList<Vector2f> sortByX(ArrayList<Vector2f> vert,
                                               int start, int end)
    {
        if (start >= end) {
            return vert;
        }
        int pivot = partition(vert, start, end);
        sortByX(vert, start, pivot - 1);
        sortByX(vert, pivot + 1, end);
        return vert;
    }

    // function to partition the points for quick sort
    private static int partition(ArrayList<Vector2f> vert, int start,
                                 int end)
    {
        Vector2f pivot = vert.get(end);
        int i = start - 1;
        for (int j = start; j <= end - 1; j++) {
            if (vert.get(j).getX() <= pivot.getX()) {
                i++;
                Vector2f temp = vert.get(i);
                vert.set(i,vert.get(j));
                vert.set(j,temp);
            }
        }
        Vector2f temp = vert.get(i+1);
        vert.set(i+1, vert.get(end));
        vert.set(end, temp);
        return i + 1;
    }
}
