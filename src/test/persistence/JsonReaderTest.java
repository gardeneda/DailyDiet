package persistence;

import model.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

// Referenced from JSON Serialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentUserFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            User user = reader.userRead();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNonExistentDayFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Day day = reader.dayRead();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderDayEmptyLists() {
        JsonReader reader = new JsonReader("./data/testReaderDayEmptyLists.json");
        try {
            Day day = reader.dayRead();
            int date = day.getDate();
            ExerciseList exerList = day.getExerciseList();
            FoodList foodList = day.getFoodList();
            checkDay(day, date, exerList, foodList);
        } catch (IOException e) {
            fail("Couldn't read from file.");
        }
    }

    @Test
    void testReaderGeneralDay() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDay.json");
        try {
            Day day = reader.dayRead();

            List<Exercise> exerList = day.getExerciseList().getList();
            List<Food> foodList = day.getFoodList().getList();

            assertEquals(1, day.getDate());
            assertEquals(2, exerList.size());
            assertEquals(2, foodList.size());

            checkExercise(exerList.get(0), "running", 1, 0);
            checkExercise(exerList.get(1), "swimming", 2, 12);
            checkFood(foodList.get(0), "Pasta", 350);
            checkFood(foodList.get(1), "Hamburger", 550);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderUser() {
        JsonReader reader = new JsonReader("./data/testReaderUser.json");
        try {
            User user = reader.userRead();
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
            fail("Couldn't find the file.");
        }
    }
}