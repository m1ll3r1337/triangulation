import Model.Model;
import Model.TriPolyModel;
import Model.Tetrahedron;
import Model.Triangle3D;
import Model.Polygon;

import math.Vector3f;
import objreader.ObjReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        Path fileName = Path.of("C:\\Users\\miller\\IdeaProjects\\AaDS\\triangulation\\3DModels\\SimpleModelsForReaderTests\\NonManifold.obj");
        String fileContent = Files.readString(fileName);

        System.out.println("Loading model ...");
        Model model = ObjReader.read(fileContent);

        System.out.println("Vertices: " + model.vertices.size());
        System.out.println("Texture vertices: " + model.textureVertices.size());
        System.out.println("Normals: " + model.normals.size());
        System.out.println("Polygons: " + model.polygons.size());
        TriPolyModel triPolyModel = Triangulation.getTriangulatedModel(model);
        System.out.println("balls");
        for (Polygon polygon: triPolyModel.polygons
             ) {
            for (Integer vertices: polygon.getVertexIndices()
                 ) {
                System.out.println(vertices);
            }
            System.out.println("cock");

        }


    }
}
