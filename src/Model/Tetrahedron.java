package Model;

import math.Vector3f;

import java.util.ArrayList;

public class Tetrahedron {
    private ArrayList<Triangle3D> faces;


    private final Vector3f a;
    private final Vector3f b;
    private final Vector3f c;
    private final Vector3f d;

    public ArrayList<Triangle3D> getFaces() {
        return faces;
    }

    public Tetrahedron(ArrayList<Triangle3D> faces, Vector3f a, Vector3f b, Vector3f c, Vector3f d) {
        this.faces = faces;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    private static double determinant(double[][] matrix) {
        double result = 0;
        result += matrix[0][0] * (matrix[1][1] * (matrix[2][2] * matrix[3][3] - matrix[2][3] * matrix[3][2]) -
                matrix[1][2] * (matrix[2][1] * matrix[3][3] - matrix[2][3] * matrix[3][1]) +
                matrix[1][3] * (matrix[2][1] * matrix[3][2] - matrix[2][2] * matrix[3][1]));
        result -= matrix[0][1] * (matrix[1][0] * (matrix[2][2] * matrix[3][3] - matrix[2][3] * matrix[3][2]) -
                matrix[1][2] * (matrix[2][0] * matrix[3][3] - matrix[2][3] * matrix[3][0]) +
                matrix[1][3] * (matrix[2][0] * matrix[3][2] - matrix[2][2] * matrix[3][0]));
        result += matrix[0][2] * (matrix[1][0] * (matrix[2][1] * matrix[3][3] - matrix[2][3] * matrix[3][1]) -
                matrix[1][1] * (matrix[2][0] * matrix[3][3] - matrix[2][3] * matrix[3][0]) +
                matrix[1][3] * (matrix[2][0] * matrix[3][1] - matrix[2][1] * matrix[3][0]));
        result -= matrix[0][3] * (matrix[1][0] * (matrix[2][1] * matrix[3][2] - matrix[2][2] * matrix[3][1]) -
                matrix[1][1] * (matrix[2][0] * matrix[3][2] - matrix[2][2] * matrix[3][0]) +
                matrix[1][2] * (matrix[2][0] * matrix[3][1] - matrix[2][1] * matrix[3][0]));
        return result;
    }

    // Function to check if a point is inside the circumsphere of a tetrahedron
    public boolean isPointInsideCircumsphere(Vector3f point) {
        float p0x = a.getX();
        float p0y = a.getY();
        float p0z = a.getZ();
        float p1x = b.getX();
        float p1y = b.getY();
        float p1z = b.getZ();
        float p2x = c.getX();
        float p2y = c.getY();
        float p2z = c.getZ();
        float p3x = d.getX();
        float p3y = d.getY();
        float p3z = d.getZ();
        float pointX = point.getX();
        float pointY = point.getY();
        float pointZ = point.getZ();


        double[][] matrix = {
                {p0x - pointX, p0y - pointY, p0z - pointZ, (p0x - pointX) * (p0x - pointX) + (p0y - pointY) * (p0y - pointY) + (p0z - pointZ) * (p0z - pointZ)},
                {p1x - pointX, p1y - pointY, p1z - pointZ, (p1x - pointX) * (p1x - pointX) + (p1y - pointY) * (p1y - pointY) + (p1z - pointZ) * (p1z - pointZ)},
                {p2x - pointX, p2y - pointY, p2z - pointZ, (p2x - pointX) * (p2x - pointX) + (p2y - pointY) * (p2y - pointY) + (p2z - pointZ) * (p2z - pointZ)},
                {p3x - pointX, p3y - pointY, p3z - pointZ, (p3x - pointX) * (p3x - pointX) + (p3y - pointY) * (p3y - pointY) + (p3z - pointZ) * (p3z - pointZ)}
        };

        double detMatrix = determinant(matrix);

        return detMatrix > 0; // Inside if determinant is positive
    }
}
