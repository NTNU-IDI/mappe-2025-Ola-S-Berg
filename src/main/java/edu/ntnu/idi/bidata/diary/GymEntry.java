package edu.ntnu.idi.bidata.diary;

import edu.ntnu.idi.bidata.author.Author;
import java.time.LocalDateTime;

/**
 * <h1>Gym Entry.</h1>
 *
 * <p>Represents a gym/workout diary entry with specific fields for exercise information.
 * Extends DiaryEntry with additional fields for exercises, sets, reps, and weight.</p>
 */
public class GymEntry extends DiaryEntry {

  private String exercises;
  private String sets;
  private String reps;

  /**
   * Constructs a new gym diary entry.
   *
   * @param id        The ID of the entry.
   * @param author    The author of the entry.
   * @param timestamp The date and time when the entry was created.
   * @param title     The title of the entry.
   * @param content   The content of the entry.
   * @param category  The category of the entry.
   * @param exercises The exercises performed (e.g., "Bench press, Squats").
   * @param sets      Number of sets performed.
   * @param reps      Number of repetitions per set.
   */
  public GymEntry(int id, Author author, LocalDateTime timestamp,
      String title, String content, String category,
      String exercises, String sets, String reps) {
    super(id, author, timestamp, title, content, category);

    this.exercises = validateTemplateField(exercises, "Exercises");
    this.sets = validateTemplateField(sets, "Sets");
    this.reps = validateTemplateField(reps, "Reps");

    templateFields.put("Exercises", this.exercises);
    templateFields.put("Sets", this.sets);
    templateFields.put("Reps", this.reps);
  }

  /**
   * Validates a template field.
   *
   * @param value     The value to validate.
   * @param fieldName The name of the field (for error messages).
   * @return The trimmed value.
   * @throws IllegalArgumentException If value is null or empty.
   */
  private String validateTemplateField(String value, String fieldName) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(fieldName + " cannot be null or empty");
    }
    return value.trim();
  }

  /**
   * Gets the exercises for this gym entry.
   *
   * @return The exercise to get.
   */
  public String getExercises() {
    return exercises;
  }

  /**
   * Sets the exercises for this gym entry.
   *
   * @param exercises The exercise to set.
   */
  public void setExercises(String exercises) {
    this.exercises = validateTemplateField(exercises, "Exercises");
    templateFields.put("Exercises", this.exercises);
  }

  /**
   * Sets the sets for this gym entry.
   *
   * @param sets The sets to set.
   */
  public void setSets(String sets) {
    this.sets = validateTemplateField(sets, "Sets");
    templateFields.put("Sets", this.sets);
  }

  /**
   * Gets the repetitions for this gym entry.
   *
   * @return The repetitions to get.
   */
  public String getReps() {
    return reps;
  }

  /**
   * Sets the repetitions for this gym entry.
   *
   * @param reps The repetitions to set.
   */
  public void setReps(String reps) {
    this.reps = validateTemplateField(reps, "Reps");
    templateFields.put("Reps", this.reps);
  }

  /**
   * Gets the entry type of this gym entry.
   *
   * @return The entry type.
   */
  @Override
  public String getEntryType() {
    return "Gym";
  }
}