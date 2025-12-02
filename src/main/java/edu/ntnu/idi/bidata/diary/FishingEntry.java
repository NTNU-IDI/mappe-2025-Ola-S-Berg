package edu.ntnu.idi.bidata.diary;

import edu.ntnu.idi.bidata.author.Author;
import java.time.LocalDateTime;

/**
 * <h1>Fishing Entry.</h1>
 *
 * <p>Represents a fishing diary entry with specific fields for fishing-related information.
 * Extends DiaryEntry with additional fields for weather, fish caught, location, and bait used.</p>
 */
public class FishingEntry extends DiaryEntry {

  private String weather;
  private String fishCaught;
  private String location;
  private String baitUsed;

  /**
   * Constructs a new fishing diary entry.
   *
   * @param id         The ID of the entry.
   * @param author     The author of the entry.
   * @param timestamp  The date and time when the entry was created.
   * @param title      The title of the entry.
   * @param content    The content of the entry.
   * @param category   The category of the entry.
   * @param weather    The weather conditions during fishing.
   * @param fishCaught The type/species of fish caught.
   * @param location   The fishing location.
   * @param baitUsed   The bait or lure used.
   */
  public FishingEntry(int id, Author author, LocalDateTime timestamp,
      String title, String content, String category,
      String weather, String fishCaught, String location, String baitUsed) {
    super(id, author, timestamp, title, content, category);

    this.weather = validateTemplateField(weather, "Weather");
    this.fishCaught = validateTemplateField(fishCaught, "Fish caught");
    this.location = validateTemplateField(location, "Location");
    this.baitUsed = validateTemplateField(baitUsed, "Bait used");

    templateFields.put("Weather", this.weather);
    templateFields.put("Fish caught", this.fishCaught);
    templateFields.put("Location", this.location);
    templateFields.put("Bait used", this.baitUsed);
  }

  /**
   * Validates a template field.
   *
   * @param value     The value to validate.
   * @param fieldName The name of the field.
   * @return The trimmed value.
   * @throws IllegalArgumentException If value is null or empty.
   */
  private String validateTemplateField(String value, String fieldName) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(fieldName + " cannot be null or empty");
    }
    return value.trim();
  }

  public String getWeather() {
    return weather;
  }

  public void setWeather(String weather) {
    this.weather = validateTemplateField(weather, "Weather");
    templateFields.put("Weather", this.weather);
  }

  public String getFishCaught() {
    return fishCaught;
  }

  public void setFishCaught(String fishCaught) {
    this.fishCaught = validateTemplateField(fishCaught, "Fish caught");
    templateFields.put("Fish caught", this.fishCaught);
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = validateTemplateField(location, "Location");
    templateFields.put("Location", this.location);
  }

  public String getBaitUsed() {
    return baitUsed;
  }

  public void setBaitUsed(String baitUsed) {
    this.baitUsed = validateTemplateField(baitUsed, "Bait used");
    templateFields.put("Bait used", this.baitUsed);
  }

  @Override
  public String getEntryType() {
    return "Fishing";
  }
}