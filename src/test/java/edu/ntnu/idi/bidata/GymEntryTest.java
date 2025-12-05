package edu.ntnu.idi.bidata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idi.bidata.author.Author;
import edu.ntnu.idi.bidata.diary.GymEntry;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for GymEntry.
 */
class GymEntryTest {

  private Author author;
  private LocalDateTime timestamp;

  @BeforeEach
  void setUp() {
    author = new Author(1, "Test Author");
    timestamp = LocalDateTime.of(2024, 12, 4, 14, 30);
  }

  @Test
  void testCreateValidGymEntry() {
    int id = 1;
    String title = "Morning Workout";
    String content = "Great workout today!";
    String category = "Fitness";
    String exercises = "Bench Press, Squats";
    String sets = "3, 4";
    String reps = "8x60kg;7x60kg;6x60kg, 10x100kg;9x100kg;8x100kg;7x100kg";

    GymEntry entry = new GymEntry(id, author, timestamp, title, content, category,
        exercises, sets, reps);

    assertEquals(1, entry.getId());
    assertEquals("Morning Workout", entry.getTitle());
    assertEquals("Great workout today!", entry.getContent());
    assertEquals("Fitness", entry.getCategory());
    assertEquals("Gym", entry.getEntryType());
    assertEquals("Bench Press, Squats", entry.getExercises());
    assertEquals("8x60kg;7x60kg;6x60kg, 10x100kg;9x100kg;8x100kg;7x100kg", entry.getReps());
  }

  @Test
  void testGymEntryHasTemplateFields() {
    GymEntry entry = new GymEntry(1, author, timestamp, "Title", "Content", "Category",
        "Bench Press", "3", "8x60kg;7x60kg;6x60kg");

    Map<String, String> fields = entry.getTemplateFields();
    assertEquals(3, fields.size());
    assertEquals("Bench Press", fields.get("Exercises"));
    assertEquals("3", fields.get("Sets"));
    assertEquals("8x60kg;7x60kg;6x60kg", fields.get("Reps"));
  }

  @Test
  void testSetExercises() {
    GymEntry entry = new GymEntry(1, author, timestamp, "Title", "Content", "Category",
        "Bench Press", "3", "8x60kg");

    entry.setExercises("Deadlift, Squats");

    assertEquals("Deadlift, Squats", entry.getExercises());
    assertEquals("Deadlift, Squats", entry.getTemplateFields().get("Exercises"));
  }

  @Test
  void testSetSets() {
    GymEntry entry = new GymEntry(1, author, timestamp, "Title", "Content", "Category",
        "Bench Press", "3", "8x60kg");

    entry.setSets("4, 5");

    assertEquals("4, 5", entry.getTemplateFields().get("Sets"));
  }

  @Test
  void testSetReps() {
    GymEntry entry = new GymEntry(1, author, timestamp, "Title", "Content", "Category",
        "Bench Press", "3", "8x60kg");

    entry.setReps("10x70kg;9x70kg;8x70kg");

    assertEquals("10x70kg;9x70kg;8x70kg", entry.getReps());
    assertEquals("10x70kg;9x70kg;8x70kg", entry.getTemplateFields().get("Reps"));
  }

  @Test
  void testCreateGymEntryWithWhitespaceInTemplateFields() {
    GymEntry entry = new GymEntry(1, author, timestamp, "Title", "Content", "Category",
        "  Bench Press  ", "  3  ", "  8x60kg  ");

    Map<String, String> fields = entry.getTemplateFields();
    assertEquals("Bench Press", fields.get("Exercises"));
    assertEquals("3", fields.get("Sets"));
    assertEquals("8x60kg", fields.get("Reps"));
  }

  @Test
  void testGetExercises() {
    GymEntry entry = new GymEntry(1, author, timestamp, "Title", "Content", "Category",
        "Bench Press, Squats", "3, 4", "8x60kg, 10x100kg");

    String exercises = entry.getExercises();

    assertEquals("Bench Press, Squats", exercises);
  }

  @Test
  void testGetReps() {
    GymEntry entry = new GymEntry(1, author, timestamp, "Title", "Content", "Category",
        "Bench Press", "3", "8x60kg;7x60kg;6x60kg");

    String reps = entry.getReps();

    assertEquals("8x60kg;7x60kg;6x60kg", reps);
  }

  @Test
  void testCreateGymEntryWithNullExercises() {

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new GymEntry(1, author, timestamp, "Title", "Content", "Category",
            null, "3", "8x60kg")
    );
    assertEquals("Exercises cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateGymEntryWithEmptyExercises() {
    String exercises = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new GymEntry(1, author, timestamp, "Title", "Content", "Category",
            exercises, "3", "8x60kg")
    );
    assertEquals("Exercises cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateGymEntryWithWhitespaceOnlyExercises() {
    String exercises = "   ";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new GymEntry(1, author, timestamp, "Title", "Content", "Category",
            exercises, "3", "8x60kg")
    );
    assertEquals("Exercises cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateGymEntryWithNullSets() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new GymEntry(1, author, timestamp, "Title", "Content", "Category",
            "Bench Press", null, "8x60kg")
    );
    assertEquals("Sets cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateGymEntryWithEmptySets() {
    String sets = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new GymEntry(1, author, timestamp, "Title", "Content", "Category",
            "Bench Press", sets, "8x60kg")
    );
    assertEquals("Sets cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateGymEntryWithWhitespaceOnlySets() {
    String sets = "   ";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new GymEntry(1, author, timestamp, "Title", "Content", "Category",
            "Bench Press", sets, "8x60kg")
    );
    assertEquals("Sets cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateGymEntryWithNullReps() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new GymEntry(1, author, timestamp, "Title", "Content", "Category",
            "Bench Press", "3", null)
    );
    assertEquals("Reps cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateGymEntryWithEmptyReps() {
    String reps = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new GymEntry(1, author, timestamp, "Title", "Content", "Category",
            "Bench Press", "3", reps)
    );
    assertEquals("Reps cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateGymEntryWithWhitespaceOnlyReps() {
    String reps = "   ";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new GymEntry(1, author, timestamp, "Title", "Content", "Category",
            "Bench Press", "3", reps)
    );
    assertEquals("Reps cannot be null or empty", exception.getMessage());
  }

  @Test
  void testSetExercisesWithNull() {
    GymEntry entry = new GymEntry(1, author, timestamp, "Title", "Content", "Category",
        "Bench Press", "3", "8x60kg");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> entry.setExercises(null)
    );
    assertEquals("Exercises cannot be null or empty", exception.getMessage());
  }

  @Test
  void testSetExercisesWithEmpty() {
    GymEntry entry = new GymEntry(1, author, timestamp, "Title", "Content", "Category",
        "Bench Press", "3", "8x60kg");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> entry.setExercises("")
    );
    assertEquals("Exercises cannot be null or empty", exception.getMessage());
  }

  @Test
  void testSetSetsWithNull() {
    GymEntry entry = new GymEntry(1, author, timestamp, "Title", "Content", "Category",
        "Bench Press", "3", "8x60kg");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> entry.setSets(null)
    );
    assertEquals("Sets cannot be null or empty", exception.getMessage());
  }

  @Test
  void testSetSetsWithWhitespace() {
    GymEntry entry = new GymEntry(1, author, timestamp, "Title", "Content", "Category",
        "Bench Press", "3", "8x60kg");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> entry.setSets("   ")
    );
    assertEquals("Sets cannot be null or empty", exception.getMessage());
  }

  @Test
  void testSetRepsWithNull() {
    GymEntry entry = new GymEntry(1, author, timestamp, "Title", "Content", "Category",
        "Bench Press", "3", "8x60kg");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> entry.setReps(null)
    );
    assertEquals("Reps cannot be null or empty", exception.getMessage());
  }

  @Test
  void testSetRepsWithEmpty() {
    GymEntry entry = new GymEntry(1, author, timestamp, "Title", "Content", "Category",
        "Bench Press", "3", "8x60kg");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> entry.setReps("")
    );
    assertEquals("Reps cannot be null or empty", exception.getMessage());
  }
}