package org.wahlzeit.model;

public class Coordinate{

    private double x;

    private double y;

    private double z;

    // may be discarded
    private Location location;

    public Coordinate(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public double getDistance(Coordinate another_coordinate){

        double squared_x = Math.pow((another_coordinate.x - this.x), 2);
        double squared_y = Math.pow((another_coordinate.y - this.y), 2);
        double squared_z = Math.pow((another_coordinate.z - this.z), 2);

        return Math.sqrt(squared_x + squared_y + squared_z);
    }

    public boolean isEqual(Coordinate another_coordinate){
        return this.x == another_coordinate.x && this.y == another_coordinate.y && this.z == another_coordinate.z;
    }
    @Override
    public boolean equals(Object o){
        if ((o instanceof Coordinate)){
            return isEqual((Coordinate) o);
        }else{
            return false;
        }
    }
}

