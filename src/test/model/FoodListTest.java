package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FoodListTest {
    private FoodList emptyList;
    private FoodList oneElement;
    private FoodList heavyDiet;
    private FoodList negativeDiet;
    private FoodList waterDiet;
    private FoodList duplicateFood;

    private Food friedChicken;
    private Food hamburger;
    private Food water;
    private Food celery;
    private Food calorie625;


    @BeforeEach
    public void setup() {
        friedChicken = new Food(720, "fried chicken");
        hamburger = new Food(580, "hamburger");
        water = new Food(0, "water");
        celery = new Food(-50, "celery");
        calorie625 = new Food(625, "Test Food");

        emptyList = new FoodList();

        oneElement = new FoodList();
        oneElement.addFood(hamburger);

        heavyDiet = new FoodList();
        heavyDiet.addFood(hamburger);
        heavyDiet.addFood(friedChicken);
        heavyDiet.addFood(calorie625);
        heavyDiet.addFood(water);

        negativeDiet = new FoodList();
        negativeDiet.addFood(celery);
        negativeDiet.addFood(water);

        waterDiet = new FoodList();
        waterDiet.addFood(water);
        waterDiet.addFood(water);

        duplicateFood = new FoodList();
        duplicateFood.addFood(hamburger);
        duplicateFood.addFood(hamburger);
    }

    @Test
    public void testRemoveFood() {
        assertTrue(emptyList.isEmpty());

        oneElement.removeLastFood();
        assertTrue(oneElement.isEmpty());

        assertFalse(heavyDiet.isEmpty());
        heavyDiet.removeLastFood();
        heavyDiet.removeLastFood();
        heavyDiet.removeLastFood();
        heavyDiet.removeLastFood();
        assertTrue(heavyDiet.isEmpty());
    }

    @Test
    public void testRemoveFoodCheckIfElementIsGone() {
        heavyDiet.removeLastFood();
        heavyDiet.removeLastFood();
        assertEquals(720 + 580, heavyDiet.totalCaloriesConsumed());

        negativeDiet.removeLastFood();
        assertEquals(-50, negativeDiet.totalCaloriesConsumed());
        negativeDiet.removeLastFood();
        assertEquals(0, negativeDiet.totalCaloriesConsumed());
    }

    @Test
    public void testTotalCaloriesConsumed() {
        assertEquals(0, waterDiet.totalCaloriesConsumed());
        assertEquals(0, emptyList.totalCaloriesConsumed());
        assertEquals(-50, negativeDiet.totalCaloriesConsumed());
        assertEquals(720 + 580 + 625, heavyDiet.totalCaloriesConsumed());
        assertEquals(580, oneElement.totalCaloriesConsumed());
        assertEquals(1160, duplicateFood.totalCaloriesConsumed());
    }
}
