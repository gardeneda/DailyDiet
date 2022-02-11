package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FoodTest {
    private Food friedChicken;
    private Food hamburger;
    private Food water;
    private Food celery;

    @BeforeEach
    public void setup() {
        friedChicken = new Food(720, "fried chicken");
        hamburger = new Food(580, "hamburger");
        water = new Food(0, "water");
        celery = new Food(-50, "celery");
    }

    @Test
    public void testConstructor() {
        assertEquals(720, friedChicken.getCalories());
        assertEquals("fried chicken", friedChicken.getName());

        assertEquals(580, hamburger.getCalories());
        assertEquals("hamburger", hamburger.getName());

        assertEquals(0, water.getCalories());
        assertEquals("water", water.getName());

        assertEquals(-50, celery.getCalories());
        assertEquals("celery", celery.getName());
    }
}
