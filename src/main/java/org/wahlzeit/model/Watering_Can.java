package org.wahlzeit.model;

public class Watering_Can {
    protected Watering_Can_Type watering_can_type = null;
    private final int id;
    private static int _id = 0;

    public Watering_Can(Watering_Can_Type wtype) {
        if (wtype == null) {
            throw new IllegalArgumentException("wtype cannot be null");
        }
        this.id = _id++;
        this.watering_can_type = wtype;
    }

    public int getId() {
        return id;
    }

    public Watering_Can_Type getWatering_Can_Type() {
        return this.watering_can_type;
    }

    public int getCapacity() {
        return watering_can_type.capacity;
    }

    public void setCapacity(int newcapacity) {
        watering_can_type.capacity = newcapacity;
    }
}
