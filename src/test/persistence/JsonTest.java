package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import model.*;

public class JsonTest {

    protected void checkUser(User user, String name, int age, String gender, double weight, double height,
                             double dailyMetabolism, double bodyMassIndex, double weightGoal,
                             boolean isAchievingWeightGoal) {
        assertEquals(name, user.getName());
        assertEquals(age, user.getAge());
        assertEquals(gender, user.getGender());
        assertEquals(weight, user.getWeight());
        assertEquals(height, user.getHeightInCm());
        assertEquals(dailyMetabolism, user.getDailyMetabolism());
        assertEquals(bodyMassIndex, user.getBodyMassIndex());
        assertEquals(weightGoal, user.getWeightGoal());
        assertEquals(isAchievingWeightGoal, user.isAchievingWeightGoal());
    }

    protected void checkDay(Day day, int date, ExerciseList exerciseList, FoodList foodList) {
        assertEquals(date, day.getDate());
        assertEquals(exerciseList.getList().size(), day.getNumExerList());
        assertEquals(foodList.getList().size(), day.getNumFoodList());
    }

    protected void checkFood(Food food, String name, double calories) {
        assertEquals(name, food.getName());
        assertEquals(calories, food.getCalories());
    }

    protected void checkExercise(Exercise exercise, String name, int hours, int minutes) {
        assertEquals(name, exercise.getExerciseName());
        assertEquals(hours, exercise.getHours());
        assertEquals(minutes, exercise.getMinutes());
    }
}
