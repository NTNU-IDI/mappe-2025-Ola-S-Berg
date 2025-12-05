package edu.ntnu.idi.bidata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idi.bidata.author.Author;
import edu.ntnu.idi.bidata.diary.FishingEntry;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for FishingEntry.
 */
class FishingEntryTest {

  private Author author;
  private LocalDateTime timestamp;

  @BeforeEach
  void setUp() {
    author = new Author(1, "Test Author");
    timestamp = LocalDateTime.of(2025, 12, 4, 14, 30);
  }


  @Test
  void testCreateValidFishingEntry() {
    int id = 1;
    String title = "Great Fishing Day";
    String content = "Caught many fish today.";
    String category = "Fishing";
    String weather = "Sunny";
    String fishCaught = "Salmon";
    String location = "Lake Superior";
    String baitUsed = "Worm";

    FishingEntry entry = new FishingEntry(id, author, timestamp, title, content, category,
        weather, fishCaught, location, baitUsed);

    assertEquals(1, entry.getId());
    assertEquals("Great Fishing Day", entry.getTitle());
    assertEquals("Caught many fish today.", entry.getContent());
    assertEquals("Fishing", entry.getCategory());
    assertEquals("Fishing", entry.getEntryType());
  }

  @Test
  void testFishingEntryHasTemplateFields() {
    FishingEntry entry = new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
        "Sunny", "Salmon", "Lake Superior", "Worm");

    Map<String, String> fields = entry.getTemplateFields();
    assertEquals(4, fields.size());
    assertEquals("Sunny", fields.get("Weather"));
    assertEquals("Salmon", fields.get("Fish caught"));
    assertEquals("Lake Superior", fields.get("Location"));
    assertEquals("Worm", fields.get("Bait used"));
  }

  @Test
  void testSetWeather() {
    FishingEntry entry = new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
        "Sunny", "Salmon", "Lake Superior", "Worm");

    entry.setWeather("Rainy");

    assertEquals("Rainy", entry.getTemplateFields().get("Weather"));
  }

  @Test
  void testSetFishCaught() {
    FishingEntry entry = new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
        "Sunny", "Salmon", "Lake Superior", "Worm");

    entry.setFishCaught("Trout");

    assertEquals("Trout", entry.getTemplateFields().get("Fish caught"));
  }

  @Test
  void testSetLocation() {
    FishingEntry entry = new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
        "Sunny", "Salmon", "Lake Superior", "Worm");

    entry.setLocation("Pacific Ocean");

    assertEquals("Pacific Ocean", entry.getTemplateFields().get("Location"));
  }

  @Test
  void testSetBaitUsed() {
    FishingEntry entry = new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
        "Sunny", "Salmon", "Lake Superior", "Worm");

    entry.setBaitUsed("Fly");

    assertEquals("Fly", entry.getTemplateFields().get("Bait used"));
  }

  @Test
  void testCreateFishingEntryWithWhitespaceInTemplateFields() {
    FishingEntry entry = new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
        "  Sunny  ", "  Salmon  ", "  Lake Superior  ", "  Worm  ");

    Map<String, String> fields = entry.getTemplateFields();
    assertEquals("Sunny", fields.get("Weather"));
    assertEquals("Salmon", fields.get("Fish caught"));
    assertEquals("Lake Superior", fields.get("Location"));
    assertEquals("Worm", fields.get("Bait used"));
  }

  @Test
  void testCreateFishingEntryWithNullWeather() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
            null, "Salmon", "Lake Superior", "Worm")
    );
    assertEquals("Weather cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateFishingEntryWithEmptyWeather() {
    String weather = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
            weather, "Salmon", "Lake Superior", "Worm")
    );
    assertEquals("Weather cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateFishingEntryWithWhitespaceOnlyWeather() {
    String weather = "   ";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
            weather, "Salmon", "Lake Superior", "Worm")
    );
    assertEquals("Weather cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateFishingEntryWithNullFishCaught() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
            "Sunny", null, "Lake Superior", "Worm")
    );
    assertEquals("Fish caught cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateFishingEntryWithEmptyFishCaught() {
    String fishCaught = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
            "Sunny", fishCaught, "Lake Superior", "Worm")
    );
    assertEquals("Fish caught cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateFishingEntryWithNullLocation() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
            "Sunny", "Salmon", null, "Worm")
    );
    assertEquals("Location cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateFishingEntryWithEmptyLocation() {
    String location = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
            "Sunny", "Salmon", location, "Worm")
    );
    assertEquals("Location cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateFishingEntryWithNullBaitUsed() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
            "Sunny", "Salmon", "Lake Superior", null)
    );
    assertEquals("Bait used cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateFishingEntryWithEmptyBaitUsed() {
    String baitUsed = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
            "Sunny", "Salmon", "Lake Superior", baitUsed)
    );
    assertEquals("Bait used cannot be null or empty", exception.getMessage());
  }

  @Test
  void testSetWeatherWithNull() {
    FishingEntry entry = new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
        "Sunny", "Salmon", "Lake Superior", "Worm");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> entry.setWeather(null)
    );
    assertEquals("Weather cannot be null or empty", exception.getMessage());
  }

  @Test
  void testSetFishCaughtWithEmpty() {
    FishingEntry entry = new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
        "Sunny", "Salmon", "Lake Superior", "Worm");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> entry.setFishCaught("")
    );
    assertEquals("Fish caught cannot be null or empty", exception.getMessage());
  }

  @Test
  void testSetLocationWithWhitespace() {
    FishingEntry entry = new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
        "Sunny", "Salmon", "Lake Superior", "Worm");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> entry.setLocation("   ")
    );
    assertEquals("Location cannot be null or empty", exception.getMessage());
  }

  @Test
  void testSetBaitUsedWithNull() {
    FishingEntry entry = new FishingEntry(1, author, timestamp, "Title", "Content", "Category",
        "Sunny", "Salmon", "Lake Superior", "Worm");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> entry.setBaitUsed(null)
    );
    assertEquals("Bait used cannot be null or empty", exception.getMessage());
  }
}