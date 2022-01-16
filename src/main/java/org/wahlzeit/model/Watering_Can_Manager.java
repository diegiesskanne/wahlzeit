package org.wahlzeit.model;

import java.util.HashMap;

public class Watering_Can_Manager {

    private static final Watering_Can_Manager managerInstance = new Watering_Can_Manager();
    private final HashMap<Integer, Watering_Can> cans = new HashMap<Integer, Watering_Can>();
    private final HashMap<String, Watering_Can_Type> canTypes = new HashMap<String, Watering_Can_Type>();

    public static Watering_Can_Manager getInstance() {
        return managerInstance;
    }

    public Watering_Can createWaterinteringCan(String typeName) {
        assert typeName != null : "my type is null";
        Watering_Can_Type wateringType = getWateringCanType(typeName);
        Watering_Can result = wateringType.createInstance();
        this.cans.put(result.getId(), result);

        return result;
    }

    public Watering_Can_Type getWateringCanType(String typeName) {
        if (canTypes.containsKey(typeName)) {
            return canTypes.get(typeName);
        } else {
            Watering_Can_Type watering_can_type = new Watering_Can_Type(typeName);
            canTypes.put(typeName, watering_can_type);
            return watering_can_type;
        }
    }
}
