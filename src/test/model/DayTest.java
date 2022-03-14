package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DayTest {
    Exercise running;
    Exercise swimming;

    Food pasta;
    Food hamburger;

    ExerciseList exerciseList;
    ExerciseList emptyExerList;

    FoodList foodList;
    FoodList emptyFoodList;

    Day emptyListDay;
    Day regularDay;

    @BeforeEach
    void setup() {
        running = new Exercise("running", 1, 0);
        swimming = new Exercise("swimming", 2, 12);

        pasta = new Food(350, "Pasta");
        hamburger = new Food(550, "Hamburger");

        emptyExerList = new ExerciseList();
        exerciseList = new ExerciseList();
        exerciseList.addExercise(running);
        exerciseList.addExercise(swimming);

        emptyFoodList = new FoodList();
        foodList = new FoodList();
        foodList.addFood(pasta);
        foodList.addFood(hamburger);

        emptyListDay = new Day("1", emptyExerList, emptyFoodList);
        regularDay = new Day("1", exerciseList, foodList);
    }

    @Test
    void testGetNumFromList() {
        assertEquals(2, regularDay.getNumExerList());
        assertEquals(2, regularDay.getNumFoodList());
        assertEquals(0, emptyListDay.getNumFoodList());
        assertEquals(0, emptyListDay.getNumFoodList());
    }
}
