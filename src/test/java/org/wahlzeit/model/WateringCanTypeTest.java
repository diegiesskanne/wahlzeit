package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class WateringCanTypeTest {

    @Test
    public void testWateringCanType() {

        Watering_Can_Type big_cans = new Watering_Can_Type("big cans");
        Watering_Can_Type small_cans = new Watering_Can_Type("small cans");

        Watering_Can_Type red_cans = new Watering_Can_Type("red cans");
        Watering_Can_Type blue_cans = new Watering_Can_Type("blue cans");

        blue_cans.setSuperType(big_cans);

        big_cans.setCapacity(63);
        Watering_Can gustav_the_can = big_cans.createInstance();

        small_cans.addSubType(red_cans);

        Watering_Can anne_the_can = red_cans.createInstance();

        assertTrue(red_cans.isSubType());
        assertFalse(big_cans.isSubType());
        assertFalse(small_cans.isSubType());

        assertFalse(small_cans.hasInstance(gustav_the_can));
        assertTrue(red_cans.hasInstance(anne_the_can));

        assertEquals(big_cans.getCapacity(), 63);
        assertEquals(0, gustav_the_can.getId());
        assertEquals(1, anne_the_can.getId());

        assertEquals(blue_cans.getSuperType(), big_cans);

    }
    @Test
    public void testManager() {

        Watering_Can_Manager manager = Watering_Can_Manager.getInstance();

        Watering_Can klaus_the_can = manager.createWaterinteringCan("middle cans");
        Watering_Can elsa_the_can = manager.createWaterinteringCan("middle cans");

        Watering_Can_Type type1 = elsa_the_can.getWatering_Can_Type();
        Watering_Can_Type type2 = manager.getWateringCanType("middle cans");

        assertEquals(type1, type2);
        assertSame(klaus_the_can.getWatering_Can_Type(), elsa_the_can.getWatering_Can_Type());
        assertNotEquals(klaus_the_can.getId(), elsa_the_can.getId());
    }

}
