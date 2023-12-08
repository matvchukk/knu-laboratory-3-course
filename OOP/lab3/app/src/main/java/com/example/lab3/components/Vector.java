package com.example.lab3.components;
import java.util.Objects;
public class Vector {
    public float x, y;

    public Vector() {}

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public void set(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }

    public Vector plus(Vector v) {
        return new Vector(this.x + v.x, this.y + v.y);
    }
    public Vector plus(float x, float y) {
        return new Vector(this.x + x, this.y + y);
    }
    public Vector plusX(float x) {
        return new Vector(this.x + x, this.y);
    }
    public Vector plusY(float y) {
        return new Vector(this.x, this.y + y);
    }

    public Vector mult(float l) {
        return new Vector(x * l, y * l);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Float.compare(vector.x, x) == 0 && Float.compare(vector.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
