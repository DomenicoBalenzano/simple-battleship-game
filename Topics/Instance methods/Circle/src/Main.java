class Circle {

    double radius;

    public double getLength() {
        return this.radius * 2 * Math.PI;
    }

    public double getArea() {
        return this.radius * this.radius * Math.PI;
    }
}