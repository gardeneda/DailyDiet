package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from JSON Serialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            User user = new User("Jack", 24,
                    "M", 70, 170);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected.");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyWorkRoom() {
        try {
            ExerciseList emptyExer = new ExerciseList();
            FoodList emptyFood = new FoodList();
            Day day = new Day(1, emptyExer, emptyFood);
            JsonWriter writer = new JsonWriter("./data/testWriterDayEmptyLists");
            writer.open();
            writer.write(day);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDayEmptyLists");
            day = reader.dayRead();
            assertEquals(1, day.getDate());
            assertEquals(0, day.getNumFoodList());
            assertEquals(0, day.getNumFoodList());
        } catch (IOException e) {
            fail("IOException was caught when it wasn't supposed to.");
        }
    }

    @Test
    void testWriterGeneralDay() {
        try {
            Exercise running = new Exercise("running", 1, 0);
            Exercise swimming = new Exercise("swimming", 2, 12);
            Food pasta = new Food(350, "Pasta");
            Food hamburger = new Food(550, "Hamburger");
            ExerciseList exerciseList = new ExerciseList();
            exerciseList.addExercise(running);
            exerciseList.addExercise(swimming);
            FoodList foodList = new FoodList();
            foodList.addFood(pasta);
            foodList.addFood(hamburger);
            Day day = new Day(1, exerciseList, foodList);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDay.json");
            writer.open();
            writer.write(day);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDay.json");
            day = reader.dayRead();
            List<Exercise> exerListReader = day.getExerciseList().getList();
            List<Food> foodListReader = day.getFoodList().getList();
            assertEquals(1, day.getDate());
            assertEquals(2, exerListReader.size());
            assertEquals(2, foodListReader.size());
            checkExercise(exerListReader.get(0), "running", 1, 0);
            checkExercise(exerListReader.get(1), "swimming", 2, 12);
            checkFood(foodListReader.get(0), "Pasta", 350);
            checkFood(foodListReader.get(1), "Hamburger", 550);

        } catch (IOException e) {
            fail("IOException was caught when it wasn't supposed to.");
        }
    }

    @Test
    void testWriterUser() {
        try {
            User user = new User("Jack Test", 25, "M", 70, 173);
            user.calculateMetabolism();
            user.calculateBMI(user.getWeight());
            user.updateWeightGoal(67.0);
            user.setAchievingWeightGoal(true);

            JsonWriter writer = new JsonWriter("./data/testWriterUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterUser.json");
            user = reader.userRead();
            String name = user.getName();
            int age = user.getAge();
            String gender = user.getGender();
            double weight = user.getWeight();
            double heightInCm = user.getHeightInCm();
            double heightInMeters = user.getHeightInMeters();
            double bodyMassIndex = user.getBodyMassIndex();
            int dailyMetabolism = user.getDailyMetabolism();
            double weightGoal = user.getWeightGoal();
            boolean isAchievingWeightGoal = user.isAchievingWeightGoal();
            checkUser(user, name, age, gender, weight, heightInCm,
                    bodyMassIndex, dailyMetabolism, weightGoal, isAchievingWeightGoal);

        } catch (IOException e) {
            fail("IOException was caught when it wasn't supposed to.");
        }
    }
}