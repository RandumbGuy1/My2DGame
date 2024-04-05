package main;

public class Vector2 {
    public double X;
    public double Y;

    public Vector2(double x, double y) {
        X = x;
        Y = y;
    }

    public double getLength() {
        return Math.sqrt(this.X * this.X + this.Y * this.Y);
    }

    public double getSqrLength() {
        return this.X * this.X + this.Y * this.Y;
    }

    public Vector2 normalize() {
        double length = getLength();

        if (length <= 0f) return new Vector2(0f, 0f);

        return new Vector2(this.X / length, this.Y / length);
    }

    public Vector2 add(Vector2 b) {
        return new Vector2(this.X + b.X, this.Y + b.Y);
    }
    public Vector2 subtract(Vector2 b) {
        return new Vector2(this.X - b.X, this.Y - b.Y);
    }

    public Vector2 multiply(double b) {
        return new Vector2(this.X * b, this.Y * b);
    }

    public Vector2 divide(double b) {
        return new Vector2(this.X / b, this.Y / b);
    }

    public Vector2 scale(Vector2 b) { return new Vector2(this.X * b.X, this.Y * b.Y); }
}
