package edu.ntnu.idi.bidata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.bidata.author.Author;
import edu.ntnu.idi.bidata.diary.DiaryEntry;
import edu.ntnu.idi.bidata.diary.DiaryRegistry;
import edu.ntnu.idi.bidata.diary.FishingEntry;
import edu.ntnu.idi.bidata.diary.GymEntry;
import edu.ntnu.idi.bidata.diary.StandardEntry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for DiaryRegistry.
 */
class DiaryRegistryTest {

  private DiaryRegistry registry;
  private Author author;

  @BeforeEach
  void setUp() {
    registry = new DiaryRegistry();
    author = new Author(1, "Test Author");
  }


  @Test
  void testCreateEmptyRegistry() {

    boolean empty = registry.isEmpty();
    int count = registry.getNumberOfEntries();

    assertTrue(empty);
    assertEquals(0, count);
  }

  @Test
  void testCreateStandardEntry() {
    LocalDateTime timestamp = LocalDateTime.now();

    StandardEntry entry = registry.createStandardEntry(author, timestamp,
        "Title", "Content", "Category");

    assertNotNull(entry);
    assertEquals(1, entry.getId());
    assertEquals("Standard", entry.getEntryType());
    assertEquals(1, registry.getNumberOfEntries());
  }

  @Test
  void testCreateFishingEntry() {
    LocalDateTime timestamp = LocalDateTime.now();

    FishingEntry entry = registry.createFishingEntry(author, timestamp,
        "Fishing Trip", "Great day", "Fishing",
        "Sunny", "Salmon", "Lake", "Worm");

    assertNotNull(entry);
    assertEquals(1, entry.getId());
    assertEquals("Fishing", entry.getEntryType());
    assertEquals(1, registry.getNumberOfEntries());
  }

  @Test
  void testCreateGymEntry() {
    LocalDateTime timestamp = LocalDateTime.now();

    GymEntry entry = registry.createGymEntry(author, timestamp,
        "Workout", "Good workout", "Fitness",
        "Bench Press", "3", "8x60kg");

    assertNotNull(entry);
    assertEquals(1, entry.getId());
    assertEquals("Gym", entry.getEntryType());
    assertEquals(1, registry.getNumberOfEntries());
  }

  @Test
  void testCreateMultipleEntriesWithIncrementingIds() {
    LocalDateTime timestamp = LocalDateTime.now();

    StandardEntry entry1 = registry.createStandardEntry(author, timestamp,
        "Title 1", "Content 1", "Category");
    FishingEntry entry2 = registry.createFishingEntry(author, timestamp,
        "Title 2", "Content 2", "Fishing",
        "Sunny", "Salmon", "Lake", "Worm");
    GymEntry entry3 = registry.createGymEntry(author, timestamp,
        "Title 3", "Content 3", "Fitness",
        "Squats", "4", "10x100kg");

    assertEquals(1, entry1.getId());
    assertEquals(2, entry2.getId());
    assertEquals(3, entry3.getId());
    assertEquals(3, registry.getNumberOfEntries());
  }

  @Test
  void testAddEntry() {
    StandardEntry entry = new StandardEntry(1, author, LocalDateTime.now(),
        "Title", "Content", "Category");

    registry.addEntry(entry);

    assertEquals(1, registry.getNumberOfEntries());
    assertFalse(registry.isEmpty());
  }

  @Test
  void testFindEntryById() {
    StandardEntry entry = registry.createStandardEntry(author, LocalDateTime.now(),
        "Title", "Content", "Category");
    int id = entry.getId();

    DiaryEntry found = registry.findEntryById(id);

    assertNotNull(found);
    assertEquals(entry.getId(), found.getId());
    assertEquals(entry.getTitle(), found.getTitle());
  }

  @Test
  void testFindEntriesByDate() {
    LocalDate date = LocalDate.of(2024, 12, 4);
    LocalDateTime timestamp1 = date.atTime(10, 0);
    LocalDateTime timestamp2 = date.atTime(15, 0);
    LocalDateTime timestamp3 = LocalDate.of(2024, 12, 5).atTime(10, 0);

    registry.createStandardEntry(author, timestamp1, "Title 1", "Content 1", "Category");
    registry.createStandardEntry(author, timestamp2, "Title 2", "Content 2", "Category");
    registry.createStandardEntry(author, timestamp3, "Title 3", "Content 3", "Category");

    List<DiaryEntry> entries = registry.findEntriesByDate(date);

    assertEquals(2, entries.size());
  }

  @Test
  void testFindEntriesByCategory() {
    registry.createStandardEntry(author, LocalDateTime.now(), "Title 1", "Content 1", "Personal");
    registry.createStandardEntry(author, LocalDateTime.now(), "Title 2", "Content 2", "Work");
    registry.createStandardEntry(author, LocalDateTime.now(), "Title 3", "Content 3", "Personal");

    List<DiaryEntry> entries = registry.findEntriesByCategory("Personal");

    assertEquals(2, entries.size());
    assertTrue(entries.stream().allMatch(e -> e.getCategory().equals("Personal")));
  }

  @Test
  void testFindEntriesByCategoryCaseInsensitive() {
    registry.createStandardEntry(author, LocalDateTime.now(), "Title", "Content", "Personal");

    List<DiaryEntry> entries = registry.findEntriesByCategory("personal");

    assertEquals(1, entries.size());
  }

  @Test
  void testFindEntriesByType() {
    registry.createStandardEntry(author, LocalDateTime.now(), "Title 1", "Content 1", "Category");
    registry.createFishingEntry(author, LocalDateTime.now(), "Title 2", "Content 2", "Fishing",
        "Sunny", "Salmon", "Lake", "Worm");
    registry.createGymEntry(author, LocalDateTime.now(), "Title 3", "Content 3", "Fitness",
        "Squats", "4", "10x100kg");

    List<DiaryEntry> fishingEntries = registry.findEntriesByType("Fishing");

    assertEquals(1, fishingEntries.size());
    assertEquals("Fishing", fishingEntries.getFirst().getEntryType());
  }

  @Test
  void testFindEntriesByTypeCaseInsensitive() {
    registry.createGymEntry(author, LocalDateTime.now(), "Title", "Content",
        "Fitness", "Squats", "4", "10x100kg");

    List<DiaryEntry> entries = registry.findEntriesByType("gym");

    assertEquals(1, entries.size());
  }

  @Test
  void testDeleteEntryById() {
    StandardEntry entry = registry.createStandardEntry(author, LocalDateTime.now(),
        "Title", "Content", "Category");
    int id = entry.getId();

    boolean deleted = registry.deleteEntryById(id);

    assertTrue(deleted);
    assertNull(registry.findEntryById(id));
    assertEquals(0, registry.getNumberOfEntries());
  }

  @Test
  void testGetAllEntriesSortedDescending() {
    LocalDateTime time1 = LocalDateTime.of(2024, 12, 1, 10, 0);
    LocalDateTime time2 = LocalDateTime.of(2024, 12, 3, 10, 0);
    LocalDateTime time3 = LocalDateTime.of(2024, 12, 2, 10, 0);

    registry.createStandardEntry(author, time1, "First", "Content 1", "Category");
    registry.createStandardEntry(author, time2, "Third", "Content 2", "Category");
    registry.createStandardEntry(author, time3, "Second", "Content 3", "Category");

    List<DiaryEntry> entries = registry.getAllEntriesSortedDescending();

    assertEquals(3, entries.size());
    assertEquals("Third", entries.get(0).getTitle());
    assertEquals("Second", entries.get(1).getTitle());
    assertEquals("First", entries.get(2).getTitle());
  }

  @Test
  void testGetNumberOfEntries() {
    registry.createStandardEntry(author, LocalDateTime.now(), "Title 1", "Content 1", "Category");
    registry.createStandardEntry(author, LocalDateTime.now(), "Title 2", "Content 2", "Category");
    registry.createStandardEntry(author, LocalDateTime.now(), "Title 3", "Content 3", "Category");

    int count = registry.getNumberOfEntries();

    assertEquals(3, count);
  }

  @Test
  void testIsEmpty() {
    assertTrue(registry.isEmpty());

    registry.createStandardEntry(author, LocalDateTime.now(), "Title", "Content", "Category");

    assertFalse(registry.isEmpty());
  }

  @Test
  void testAddNullEntry() {

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> registry.addEntry(null)
    );
    assertEquals("Diary entry cannot be null", exception.getMessage());
  }

  @Test
  void testFindEntryByIdNonExistent() {
    registry.createStandardEntry(author, LocalDateTime.now(), "Title", "Content", "Category");

    DiaryEntry found = registry.findEntryById(999);

    assertNull(found);
  }

  @Test
  void testFindEntriesByNullDate() {

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> registry.findEntriesByDate(null)
    );
    assertEquals("Date cannot be null", exception.getMessage());
  }

  @Test
  void testFindEntriesByDateNoMatches() {
    LocalDate date = LocalDate.of(2024, 12, 4);
    registry.createStandardEntry(author, LocalDate.of(2024, 12, 5).atTime(10, 0),
        "Title", "Content", "Category");

    List<DiaryEntry> entries = registry.findEntriesByDate(date);

    assertTrue(entries.isEmpty());
  }

  @Test
  void testFindEntriesByNullCategory() {

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> registry.findEntriesByCategory(null)
    );
    assertEquals("Category cannot be null or empty", exception.getMessage());
  }

  @Test
  void testFindEntriesByEmptyCategory() {
    String category = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> registry.findEntriesByCategory(category)
    );
    assertEquals("Category cannot be null or empty", exception.getMessage());
  }

  @Test
  void testFindEntriesByWhitespaceCategory() {
    String category = "   ";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> registry.findEntriesByCategory(category)
    );
    assertEquals("Category cannot be null or empty", exception.getMessage());
  }

  @Test
  void testFindEntriesByCategoryNoMatches() {
    registry.createStandardEntry(author, LocalDateTime.now(), "Title", "Content", "Personal");

    List<DiaryEntry> entries = registry.findEntriesByCategory("Work");

    assertTrue(entries.isEmpty());
  }

  @Test
  void testFindEntriesByNullType() {

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> registry.findEntriesByType(null)
    );
    assertEquals("Entry type cannot be null or empty", exception.getMessage());
  }

  @Test
  void testFindEntriesByEmptyType() {
    String type = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> registry.findEntriesByType(type)
    );
    assertEquals("Entry type cannot be null or empty", exception.getMessage());
  }

  @Test
  void testFindEntriesByWhitespaceType() {
    String type = "   ";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> registry.findEntriesByType(type)
    );
    assertEquals("Entry type cannot be null or empty", exception.getMessage());
  }

  @Test
  void testFindEntriesByTypeNoMatches() {
    registry.createStandardEntry(author, LocalDateTime.now(), "Title", "Content", "Category");

    List<DiaryEntry> entries = registry.findEntriesByType("Fishing");

    assertTrue(entries.isEmpty());
  }

  @Test
  void testDeleteEntryByIdNonExistent() {
    registry.createStandardEntry(author, LocalDateTime.now(), "Title", "Content", "Category");

    boolean deleted = registry.deleteEntryById(999);

    assertFalse(deleted);
    assertEquals(1, registry.getNumberOfEntries());
  }

  @Test
  void testGetAllEntriesSortedDescendingEmptyRegistry() {

    List<DiaryEntry> entries = registry.getAllEntriesSortedDescending();

    assertNotNull(entries);
    assertTrue(entries.isEmpty());
  }
}