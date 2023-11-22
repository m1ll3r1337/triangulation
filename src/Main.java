import Model.Model;
import Model.TriPolyModel;
import Model.Polygon;

import objreader.ObjReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {

        Path fileName = Path.of("C:\\Users\\miller\\IdeaProjects\\AaDS\\triangulation\\3DModels\\SimpleModelsForReaderTests\\LoadingTest.obj");
        String fileContent = Files.readString(fileName);

        System.out.println("Loading model ...");
        Model model = ObjReader.read(fileContent);
        TriPolyModel triModel = DelaunayTriangulation.getTriangulatedModel(model);
//        System.out.println("Vertices: " + model.vertices.size());
//        System.out.println("Texture vertices: " + model.textureVertices.size());
//        System.out.println("Normals: " + model.normals.size());
//        System.out.println("Polygons: " + model.polygons.size());

        for (Polygon poly: model.polygons
             ) {
            System.out.println(poly.getVertexIndices());
            System.out.println("cock");
        }
        System.out.println("balls");
        for (Polygon poly: triModel.polygons
        ) {
            System.out.println(poly.getVertexIndices());
            System.out.println("cock");
        }


    }
}
