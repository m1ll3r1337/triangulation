package Model;

import math.Vector2f;
import math.Vector3f;

import java.util.Vector;

public class Triangle3D {
    private final Vector3f a;
    private final Vector3f b;
    private final Vector3f c;

    public Triangle3D(Vector3f a, Vector3f b, Vector3f c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Vector3f getA() {
        return a;
    }

    public Vector3f getB() {
        return b;
    }

    public Vector3f getC() {
        return c;
    }
}
