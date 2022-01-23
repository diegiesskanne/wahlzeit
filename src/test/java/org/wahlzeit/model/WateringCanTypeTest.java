package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class WateringCanTypeTest {

    @Test
    public void testWateringCanTypeMethods() {

        WateringCanType bigCans = new WateringCanType("big cans");
        WateringCanType smallCans = new WateringCanType("small cans");

        WateringCanType redCans = new WateringCanType("red cans");
        WateringCanType blueCans = new WateringCanType("blue cans");

        blueCans.setSuperType(bigCans);

        bigCans.setCapacity(63);
        WateringCan gustavTheCan = bigCans.createInstance();

        smallCans.addSubType(redCans);

        WateringCan anneTheCan = redCans.createInstance();

        assertFalse(redCans.isSubType(smallCans));
        assertFalse(bigCans.isSubType(smallCans));
        assertTrue(smallCans.isSubType(redCans));

        assertFalse(smallCans.hasInstance(gustavTheCan));
        assertTrue(redCans.hasInstance(anneTheCan));

        assertEquals(bigCans.getCapacity(), 63);
        assertEquals(0, gustavTheCan.getId());
        assertEquals(1, anneTheCan.getId());

        assertEquals(blueCans.getSuperType(), bigCans);

    }
    @Test
    public void testWateringCanManagerMethods() {

        WateringCanManager manager = WateringCanManager.getInstance();

        WateringCan klausTheCan = manager.createWateringCan("middle cans");
        WateringCan elsaTheCan = manager.createWateringCan("middle cans");

        WateringCanType type1 = elsaTheCan.getWatering_Can_Type();
        WateringCanType type2 = manager.getWateringCanType("middle cans");

        assertEquals(type1, type2);
        assertSame(klausTheCan.getWatering_Can_Type(), elsaTheCan.getWatering_Can_Type());
        assertNotEquals(klausTheCan.getId(), elsaTheCan.getId());
    }

}
