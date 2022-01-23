package org.wahlzeit.model;

import java.util.HashMap;

public class WateringCanManager {

    private static final WateringCanManager managerInstance = new WateringCanManager();
    private final HashMap<Integer, WateringCan> cans = new HashMap<Integer, WateringCan>();
    private final HashMap<String, WateringCanType> canTypes = new HashMap<String, WateringCanType>();

    public static synchronized WateringCanManager getInstance() {
        return managerInstance;
    }

    public WateringCan createWateringCan(String typeName) {
        if (typeName == null) {
            throw new IllegalArgumentException("my type is null");
        }

        WateringCanType wateringType = getWateringCanType(typeName);
        WateringCan result = wateringType.createInstance();
        this.cans.put(result.getId(), result);

        return result;
    }

    public WateringCanType getWateringCanType(String typeName) {
        if (canTypes.containsKey(typeName)) {
            return canTypes.get(typeName);
        } else {
            WateringCanType watering_can_type = new WateringCanType(typeName);
            canTypes.put(typeName, watering_can_type);
            return watering_can_type;
        }
    }
}
